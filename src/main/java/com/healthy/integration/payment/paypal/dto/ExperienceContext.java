package com.healthy.integration.payment.paypal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExperienceContext {
    @JsonProperty("payment_method_preference")
    private String paymentMethodPreference;

    @JsonProperty("brand_name")
    private String brandName;

    private String locale;

    @JsonProperty("landing_page")
    private String landingPage;

    @JsonProperty("user_action")
    private String userAction;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("cancel_url")
    private String cancelUrl;
}