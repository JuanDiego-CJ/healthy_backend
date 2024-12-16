package com.healthy.service;

import com.healthy.dto.PaymentOrderResponse;
import com.healthy.dto.PaymentCaptureResponse;
import jakarta.mail.MessagingException;

public interface CheckoutService {

    PaymentOrderResponse createPayment(Integer purchaseId, String returnUrl, String cancelUrl)throws MessagingException;

    PaymentCaptureResponse capturePayment(String orderId) throws MessagingException;
}