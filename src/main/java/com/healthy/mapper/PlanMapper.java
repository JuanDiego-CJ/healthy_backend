package com.healthy.mapper;

import com.healthy.dto.PlanCreateDTO;
import com.healthy.dto.PlanDTO;
import com.healthy.model.entity.Plan;
import com.healthy.model.entity.Profile;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component

public class PlanMapper {
    private final ModelMapper modelMapper;
    private final GoalMapper goalMapper;

    public PlanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.goalMapper = new GoalMapper(modelMapper);
    }

    public Plan toPlanCreateDTO(PlanCreateDTO planCreateDTO) {
        Plan plan = modelMapper.map(planCreateDTO, Plan.class);
        Profile profile = new Profile();
        plan.setProfile(profile);

        plan.setName(planCreateDTO.getPlanName());
        plan.setDescription(planCreateDTO.getDescription());
        plan.setStartDate(planCreateDTO.getStartDate());
        plan.setEndDate(planCreateDTO.getEndDate());
        plan.setPlanStatus(planCreateDTO.getStatus());

        plan.setGoals(null);

        return plan;
    }
    public PlanDTO toPlanDTO(Plan plan) {
        PlanDTO planDTO = modelMapper.map(plan, PlanDTO.class);
        planDTO.setPlanId(plan.getId());
        planDTO.setPlanName(plan.getName());
        planDTO.setProfileId(plan.getProfile().getId());
        planDTO.setDescription(plan.getDescription());
        planDTO.setStartDate(plan.getStartDate());
        planDTO.setEndDate(plan.getEndDate());
        planDTO.setPlanStatus(plan.getPlanStatus());

        if(plan.getGoals() != null) {
            planDTO.setGoals(plan.getGoals().stream()
                    .map(goalMapper::toGoalDTO)
                    .toList());
        }

        return planDTO;
    }
}
