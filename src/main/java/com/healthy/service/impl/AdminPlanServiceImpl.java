package com.healthy.service.impl;

import com.healthy.dto.PlanCreateDTO;
import com.healthy.dto.PlanDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.PlanMapper;
import com.healthy.model.entity.Plan;
import com.healthy.model.entity.Profile;
import com.healthy.model.entity.User;
import com.healthy.model.enums.PlanStatus;
import com.healthy.repository.GoalRepository;
import com.healthy.repository.PlanRepository;
import com.healthy.repository.ProfileRepository;
import com.healthy.repository.UserRepository;
import com.healthy.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminPlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;
    private final ProfileRepository profileRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public List<PlanDTO> getAll(){
        List<Plan> plans = planRepository.findAll();
        return plans.stream()
                .map(planMapper::toPlanDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PlanDTO> paginate(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        return planRepository.findPlanByProfileId(profile.getId(), pageable)
                .map(planMapper::toPlanDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public PlanDTO findById(Integer id){
        Plan plan = planRepository.findById(id).orElseThrow(() -> new RuntimeException("Plan not found"));
        return planMapper.toPlanDTO(plan);
    }

    @Transactional
    @Override
    public PlanDTO create(PlanCreateDTO planCreateDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Plan plan = planMapper.toPlanCreateDTO(planCreateDTO);

        Optional<User> user = userRepository.findByUsername(username);

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        plan.setStartDate(LocalDateTime.now());
        plan.setPlanStatus(PlanStatus.ACTIVE);
        plan.setProfile(profile);
        return planMapper.toPlanDTO(planRepository.save(plan));
    }

    @Transactional
    @Override
    public PlanDTO update(Integer id, PlanCreateDTO updatePlan) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        Plan planFromDb = planRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Plan "+id+" not found"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        planFromDb.setProfile(profile);
        planFromDb.setName(updatePlan.getPlanName());
        planFromDb.setDescription(updatePlan.getDescription());

        planFromDb = planRepository.save(planFromDb);

        return planMapper.toPlanDTO(planFromDb);

    }

    public void delete(Integer id) {
        Plan plan = planRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Plan "+id+" not found"));
        planRepository.delete(plan);
    }

}
