package com.marcazia.notification.email;

import com.marcazia.notification.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async
    // To Not Block the main thread
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );
        messageHelper.setFrom("muashrafalii@marcazia.com");
        final String templateName =
                EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);

        messageHelper.setSubject(
                EmailTemplates.PAYMENT_CONFIRMATION.getSubject()
        );

        try {
            String htmlTemplate = templateEngine.process(
                    templateName,
                    context
            );

            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);

            mailSender.send(messageHelper.getMimeMessage());

            log.info(
                    String.format(
                            "Email successfully sent to %s using template %s",
                            destinationEmail,
                            templateName
                    )
            );
        }
        catch (MessagingException e) {
            log.warn(
                    "Cannot send email to {}",
                    destinationEmail
            );
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal totalAmount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );
        messageHelper.setFrom("muashrafalii@marcazia.com");
        final String templateName =
                EmailTemplates.ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", totalAmount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);

        messageHelper.setSubject(
                EmailTemplates.ORDER_CONFIRMATION.getSubject()
        );

        try {
            String htmlTemplate = templateEngine.process(
                    templateName,
                    context
            );

            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);

            mailSender.send(messageHelper.getMimeMessage());

            log.info(
                    String.format(
                            "Email successfully sent to %s using template %s",
                            destinationEmail,
                            templateName
                    )
            );
        }
        catch (MessagingException e) {
            log.warn(
                    "Cannot send email to {}",
                    destinationEmail
            );
        }
    }

}
