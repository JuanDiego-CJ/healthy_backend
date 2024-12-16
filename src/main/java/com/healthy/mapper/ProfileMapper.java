package com.healthy.mapper;

import com.healthy.dto.ProfileCreateDTO;
import com.healthy.dto.ProfileDTO;
import com.healthy.dto.ProfileResourceDetailsDTO;
import com.healthy.dto.ProfileSubscriptionDTO;
import com.healthy.model.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component

public class ProfileMapper {
    private final ModelMapper modelMapper;
    private final PlanMapper planMapper;

    public ProfileMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.planMapper = new PlanMapper(modelMapper);
    }

    public ProfileDTO toProfileDTO(Profile profile) {
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);
        profileDTO.setName(profile.getUserName());
        profileDTO.setHeight(profile.getHeight());
        profileDTO.setWeight(profile.getWeight());
        profileDTO.setAge(profile.getAge());
        profileDTO.setGender(profile.getGender());
        profileDTO.setHealthConditions(profile.getHealthConditions());

        profileDTO.setPlans(profile.getPlans().stream()
                .map(planMapper::toPlanDTO)
                .toList());
        profileDTO.setResources(profile.getProfileResources().stream()
                .map(this::toProfileResourceDTO)
                .toList());
        profileDTO.setSubPlans(profile.getSubPlans().stream()
                .map(this::toProfileSubscriptionDTO)
                .toList());

        return profileDTO;
    }
    private ProfileResourceDetailsDTO toProfileResourceDTO(ProfileResource profileResource) {
        ProfileResourceDetailsDTO profileResourceDTO = modelMapper.map(profileResource, ProfileResourceDetailsDTO.class);

        //DE PROFILE RESOURCE
        profileResourceDTO.set_active(profileResource.is_active());
        profileResourceDTO.setAccess_expiration(profileResource.getAccess_expiration());

        //DE RESOURCE EXPERT
        profileResourceDTO.setExpertFirstName(profileResource.getResource().getExpert().getFirstName());
        profileResourceDTO.setExpertLastName(profileResource.getResource().getExpert().getLastName());
        profileResourceDTO.setExpertise(profileResource.getResource().getExpert().getExpertise());

        //DE RESOURCE SUBPLAN
        profileResourceDTO.setSubPlanName(profileResource.getResource().getSubPlan().getName());

        // DE RESOURCE
        profileResourceDTO.setResourceTitle(profileResource.getResource().getTitle());
        profileResourceDTO.setDescription(profileResource.getResource().getDescription());
        profileResourceDTO.setResourceType(profileResource.getResource().getResourceType());
        profileResourceDTO.setContent(profileResource.getResource().getContent());

        profileResourceDTO.setUserName(profileResource.getProfile().getUserName());

        return profileResourceDTO;
    }
    private ProfileSubscriptionDTO toProfileSubscriptionDTO(Subscription subscription) {
        ProfileSubscriptionDTO profileSubscriptionDTO = modelMapper.map(subscription, ProfileSubscriptionDTO.class);

        profileSubscriptionDTO.setStartAt(subscription.getStartAt());
        profileSubscriptionDTO.setEndAt(subscription.getEndAt());
        profileSubscriptionDTO.setPaymentStatus(subscription.getPaymentStatus());
        profileSubscriptionDTO.setSubscriptionStatus(subscription.getSubscriptionStatus());


        // PARA SUBPLAN
        profileSubscriptionDTO.setSubPlanName(subscription.getSubPlan().getName());
        profileSubscriptionDTO.setSubPlanDescription(subscription.getSubPlan().getDescription());
        profileSubscriptionDTO.setSubPlanPrice(subscription.getSubPlan().getPrice());
        profileSubscriptionDTO.setDurationDays(subscription.getSubPlan().getDurationDays());

        return profileSubscriptionDTO;
    }
    public Profile toProfile(ProfileCreateDTO profileCreateDTO) {
        return modelMapper.map(profileCreateDTO, Profile.class);
    }
}
