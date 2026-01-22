package com.marcazia.ordeService.order;

import com.marcazia.ordeService.exception.BusinessException;
import com.marcazia.ordeService.customer.CustomerClient;
import com.marcazia.ordeService.product.ProductClient;
import com.marcazia.ordeService.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

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
    }
}
