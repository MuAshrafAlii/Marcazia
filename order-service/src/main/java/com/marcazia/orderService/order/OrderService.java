package com.marcazia.orderService.order;

import com.marcazia.orderService.exception.BusinessException;
import com.marcazia.orderService.customer.CustomerClient;
import com.marcazia.orderService.kafka.OrderConfirmation;
import com.marcazia.orderService.kafka.OrderProducer;
import com.marcazia.orderService.orderLine.OrderLineRequest;
import com.marcazia.orderService.orderLine.OrderLineService;
import com.marcazia.orderService.payment.PaymentClient;
import com.marcazia.orderService.payment.PaymentRequest;
import com.marcazia.orderService.product.ProductClient;
import com.marcazia.orderService.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;



    public Integer createOrder(@Valid OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() ->
                        new BusinessException(
                                "Cannot create order. No customer exists with the provided ID: "
                                        + request.customerId()
                        )
                );

        var purchasedProducts =
                productClient.purchaseProducts(request.products());

        var order = repository.save(
                mapper.toOrder(request)
        );
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );


        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format(
                                        "No order found with the provided ID: %d",
                                        orderId
                                )
                        )
                );
    }
}
