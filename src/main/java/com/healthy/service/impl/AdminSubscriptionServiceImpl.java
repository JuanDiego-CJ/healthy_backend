package com.healthy.service.impl;

import com.healthy.dto.SubscriptionCreateDTO;
import com.healthy.dto.SubscriptionDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.SubscriptionMapper;
import com.healthy.model.entity.Profile;
import com.healthy.model.entity.SubPlan;
import com.healthy.model.entity.Subscription;
import com.healthy.model.enums.PaymentStatus;
import com.healthy.model.enums.SubscriptionStatus;
import com.healthy.repository.ProfileRepository;
import com.healthy.repository.SubPlanRepository;
import com.healthy.repository.SubscriptionRepository;
import com.healthy.service.AdminSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AdminSubscriptionServiceImpl implements AdminSubscriptionService {

    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final SubPlanRepository subPlanRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    @Override
    public List<SubscriptionDTO> getAll() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream()
                .map(subscriptionMapper::toDetailsDTO)
                .toList();
    }

    @Override
    public Page<SubscriptionDTO> paginate(Pageable pageable) {
        return subscriptionRepository.findAll(pageable)
                .map(subscriptionMapper::toDetailsDTO);
    }

    @Override
    public SubscriptionDTO findById(Integer id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        return subscriptionMapper.toDetailsDTO(subscription);
    }

    @Transactional
    @Override
    public SubscriptionDTO create(SubscriptionCreateDTO subscriptionCreateUpdateDTO) {
        Profile profile = profileRepository.findById(subscriptionCreateUpdateDTO.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        SubPlan subPlan = subPlanRepository.findById(subscriptionCreateUpdateDTO.getSubPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        Subscription subscription = subscriptionMapper.toSubscription(subscriptionCreateUpdateDTO);
        subscription.setProfile(profile);
        subscription.setSubPlan(subPlan);
        subscription.setPaymentStatus(PaymentStatus.PENDING);
        subscription.setSubscriptionStatus(SubscriptionStatus.PENDING);
        subscription.setStartAt(LocalDateTime.now());
        subscription.setEndAt(LocalDateTime.now().plusDays(subPlan.getDurationDays()));
        return subscriptionMapper.toDetailsDTO(subscriptionRepository.save(subscription));
    }

    @Transactional
    @Override
    public SubscriptionDTO update(Integer id, SubscriptionCreateDTO subscriptionCreateUpdateDTO) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        Profile profileFromDb = profileRepository.findById(subscriptionCreateUpdateDTO.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        SubPlan subPlanFromDb = subPlanRepository.findById(subscriptionCreateUpdateDTO.getSubPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        subscription.setProfile(profileFromDb);
        subscription.setSubPlan(subPlanFromDb);
        subscription.setId(subscriptionCreateUpdateDTO.getId());
        subscription.setStartAt(subscriptionCreateUpdateDTO.getStartAt());
        subscription.setEndAt(subscriptionCreateUpdateDTO.getEndAt());
        subscription.setPaymentStatus(subscriptionCreateUpdateDTO.getPaymentStatus());
        subscription.setSubscriptionStatus(subscriptionCreateUpdateDTO.getSubscriptionStatus());
        return subscriptionMapper.toDetailsDTO(subscriptionRepository.save(subscription));
    }

    public void delete(Integer id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        subscriptionRepository.delete(subscription);
    }

    @Override
    public SubscriptionDTO confirmPayment(Integer id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        subscription.setPaymentStatus(PaymentStatus.PAID);
        subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartAt(LocalDateTime.now());
        subscription.setEndAt(subscription.getStartAt().plusDays(subscription.getSubPlan().getDurationDays()));
        Subscription updateSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDetailsDTO(updateSubscription);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasActiveSubscription(Integer profileId) {
        return subscriptionRepository.existsByProfileIdAndSubscriptionStatus(profileId, SubscriptionStatus.ACTIVE);
    }


}