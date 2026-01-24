package com.marcazia.notification.notification;

import com.marcazia.notification.kafka.order.OrderConfirmation;
import com.marcazia.notification.kafka.payment.PaymentConfirmation;
import lombok.*;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;

    private NotificationType type;

    private LocalDateTime notificationDate;

    @Transient
    private OrderConfirmation orderConfirmation;

    @Transient
    private PaymentConfirmation paymentConfirmation;
}
