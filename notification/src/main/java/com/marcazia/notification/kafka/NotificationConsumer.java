package com.marcazia.notification.kafka;

import com.marcazia.notification.kafka.order.OrderConfirmation;
import com.marcazia.notification.kafka.payment.PaymentConfirmation;
import com.marcazia.notification.notification.Notification;
import com.marcazia.notification.notification.NotificationRepository;
import com.marcazia.notification.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
//    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(
            PaymentConfirmation paymentConfirmation
    ) {

        log.info(
                String.format(
                        "Consuming the message from payment-topic %s",
                        paymentConfirmation
                )
        );

        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        // TODO: send email
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(
            OrderConfirmation orderConfirmation
    ) {

        log.info(
                String.format(
                        "Consuming the message from order-topic %s",
                        orderConfirmation
                )
        );

        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // TODO: send email
    }


}
