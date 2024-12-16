package com.healthy.dto;

import com.healthy.model.enums.PaymentStatus;
import com.healthy.model.enums.SubscriptionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProfileSubscriptionDTO {
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private PaymentStatus paymentStatus;
    private SubscriptionStatus subscriptionStatus;

    // PARA SUB PLAN
    private String subPlanName;
    private String subPlanDescription;
    private BigDecimal subPlanPrice;
    private Integer durationDays;
}
