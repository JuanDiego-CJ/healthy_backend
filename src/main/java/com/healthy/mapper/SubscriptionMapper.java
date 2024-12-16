package com.healthy.mapper;

import com.healthy.dto.SubscriptionCreateDTO;
import com.healthy.dto.SubscriptionDTO;
import com.healthy.model.entity.Subscription;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    private final ModelMapper modelMapper;

    public SubscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public SubscriptionDTO toDetailsDTO(Subscription subscription) {
        SubscriptionDTO subscriptionDetailsDTO = modelMapper.map(subscription, SubscriptionDTO.class);

        subscriptionDetailsDTO.setStartAt(subscription.getStartAt());
        subscriptionDetailsDTO.setEndAt(subscription.getEndAt());
        subscriptionDetailsDTO.setPaymentStatus(subscription.getPaymentStatus());
        subscriptionDetailsDTO.setSubscriptionStatus(subscription.getSubscriptionStatus());

        subscriptionDetailsDTO.setUserName(subscription.getProfile().getUserName());

        subscriptionDetailsDTO.setSubPlanName(subscription.getSubPlan().getName());

        return subscriptionDetailsDTO;
    }

    public Subscription toSubscription(SubscriptionCreateDTO subscriptionCreateUpdateDTO) {
        return modelMapper.map(subscriptionCreateUpdateDTO, Subscription.class);
    }

    public SubscriptionCreateDTO toSubscriptionCreateUpdateDTO(Subscription subscription) {
        return modelMapper.map(subscription, SubscriptionCreateDTO.class);
    }
}