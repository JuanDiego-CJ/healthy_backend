package com.healthy.repository;
import com.healthy.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthy.model.enums.SubscriptionStatus;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    boolean existsByProfileIdAndSubscriptionStatus(Integer profileId, SubscriptionStatus subscriptionStatus);
}