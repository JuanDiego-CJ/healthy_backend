package com.healthy.dto;

import com.healthy.model.enums.PaymentStatus;
import com.healthy.model.enums.SubscriptionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubscriptionCreateDTO {
    private Integer id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private PaymentStatus paymentStatus;
    private SubscriptionStatus subscriptionStatus;

    private Integer subPlanId;

    private Integer profileId;
}
