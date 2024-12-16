package com.healthy.service;

import com.healthy.dto.SubscriptionCreateDTO;
import com.healthy.dto.SubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminSubscriptionService {
    List<SubscriptionDTO> getAll();
    Page<SubscriptionDTO> paginate(Pageable pageable);
    SubscriptionDTO findById(Integer id);
    SubscriptionDTO create(SubscriptionCreateDTO subscriptionCreateUpdateDTO);
    SubscriptionDTO update(Integer id,SubscriptionCreateDTO subscriptionCreateUpdateDTO);
    void delete(Integer id);
    boolean hasActiveSubscription(Integer profileId);
    SubscriptionDTO confirmPayment(Integer id);
}