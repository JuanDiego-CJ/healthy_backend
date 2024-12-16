package com.healthy.service.impl;
import com.healthy.dto.PaymentCaptureResponse;
import com.healthy.dto.PaymentOrderResponse;
import com.healthy.dto.SubscriptionDTO;
import com.healthy.integration.notification.email.dto.Mail;
import com.healthy.integration.notification.email.service.EmailService;
import com.healthy.integration.payment.paypal.dto.OrderCaptureResponse;
import com.healthy.integration.payment.paypal.dto.OrderResponse;
import com.healthy.integration.payment.paypal.service.PayPalService;
import com.healthy.model.entity.Subscription;
import com.healthy.repository.SubscriptionRepository;
import com.healthy.service.AdminSubscriptionService;
import com.healthy.service.CheckoutService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final PayPalService payPalService;
    private final AdminSubscriptionService subscriptionService;
    private final EmailService emailService;
    private final SubscriptionRepository subscriptionRepository;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public PaymentOrderResponse createPayment(Integer subscriptionId, String returnUrl, String cancelUrl) {
        OrderResponse orderResponse =payPalService.createOrder(subscriptionId, returnUrl, cancelUrl);

        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaymentOrderResponse(paypalUrl);
    }

    @Override
    public PaymentCaptureResponse capturePayment(String orderId) throws MessagingException{
        OrderCaptureResponse orderCaptureResponse = payPalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if (completed) {
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId();
            SubscriptionDTO subscriptionDTO = subscriptionService.confirmPayment(Integer.parseInt(purchaseIdStr));
            paypalCaptureResponse.setSubscriptionId(subscriptionDTO.getId());

            sendSubscriptionConfirmationEmail(subscriptionDTO.getId());
        }
        return paypalCaptureResponse;
    }

    private void sendSubscriptionConfirmationEmail(Integer subId) throws MessagingException, MessagingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Subscription sub = subscriptionRepository.getById(subId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedEndDate = sub.getEndAt().format(formatter);


        Map<String, Object> model = new HashMap<>();
        model.put("user", sub.getProfile().getUser().getUsername());
        model.put("total", sub.getSubPlan().getPrice());
        model.put("endDate", formattedEndDate);

        Mail mail = emailService.createMail(
                userEmail,
                "Confirmación de Pago de Suscripción",
                model,
                mailFrom
        );
        emailService.sendMail(mail,"email/subscription-confirmation-template");
    }
}