package com.healthy.service.impl;

import com.healthy.dto.GoalCreateDTO;
import com.healthy.dto.GoalDTO;
import com.healthy.dto.GoalTrackingRecordDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.GoalMapper;
import com.healthy.model.entity.*;
import com.healthy.model.enums.GoalStatus;
import com.healthy.repository.*;
import com.healthy.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminGoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    private final ProfileRepository profileRepository;
    private final HabitRepository habitRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public GoalDTO create(GoalCreateDTO goalCreateDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        Habit habit = habitRepository.findById(goalCreateDTO.getHabitId())
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + goalCreateDTO.getHabitId()));
        Plan plan = planRepository.findById(goalCreateDTO.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + goalCreateDTO.getPlanId()));

        // Crear el Goal y configurar sus propiedades
        Goal goal = new Goal();
        goal.setProfile(profile);
        goal.setHabit(habit);
        goal.setPlan(plan);
        goal.setGoalStatus(GoalStatus.IN_PROGRESS);
        goal.setTargetValue(goalCreateDTO.getTargetValue());
        goal.setCurrentValue(goalCreateDTO.getCurrentValue());
        goal.setStartDate(LocalDateTime.now());
        goal.setEndDate(goalCreateDTO.getEndDate());

        // Crear y asociar cada TrackingRecord al Goal
        List<TrackingRecord> trackingRecords = goalCreateDTO.getTrackings().stream().map(trackingDTO -> {
            TrackingRecord trackingRecord = new TrackingRecord();
            trackingRecord.setDate(trackingDTO.getDate());
            trackingRecord.setValue(trackingDTO.getValue());
            trackingRecord.setNote(trackingDTO.getNote());
            trackingRecord.setGoal(goal); // Asociar el Goal al TrackingRecord
            return trackingRecord;
        }).collect(Collectors.toList());

        // Asignar los tracking records al Goal
        goal.setTrackingRecords(trackingRecords);

        // Guardar el Goal junto con los TrackingRecords asociados
        Goal savedGoal = goalRepository.save(goal);

        // Convertir el Goal guardado a DTO y devolverlo
        return goalMapper.toGoalDTO(savedGoal);
    }


    @Transactional
    @Override
    public GoalDTO update(Integer id, GoalCreateDTO updateGoal){
        Goal fromGoalDb = goalRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Goal "+id+" not found"));
        Habit habit = habitRepository.findById(updateGoal.getHabitId())
                .orElseThrow(() -> new ResourceNotFoundException("Habit "+updateGoal.getHabitId()+" not found"));
        Plan plan = planRepository.findById(updateGoal.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan "+updateGoal.getPlanId()+" not found"));

        fromGoalDb.setHabit(habit);
        fromGoalDb.setPlan(plan);
        fromGoalDb.setStartDate(LocalDateTime.now());

        fromGoalDb = goalRepository.save(fromGoalDb);

        return goalMapper.toGoalDTO(fromGoalDb);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoalDTO> getAll(){
        List<Goal> goals = goalRepository.findAll();
        return goals.stream()
                .map(goalMapper::toGoalDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    @Override
    public List<GoalDTO> getById(Integer id){
        return goalRepository.findById(id).stream()
                .map(goalMapper::toGoalDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    @Override
    public Page<GoalDTO> paginate(Pageable pageable) {
        return goalRepository.findAll(pageable)
                .map(goalMapper::toGoalDTO);
    }
    public void deleteGoal(Integer id){
        goalRepository.deleteById(id);
    }


    // listando los records en base al perfil
    @Override
    public List<GoalDTO> getDashboard(Integer profileId) {
        List<Goal> goals = goalRepository.findByProfileId(profileId);

        return goals.stream().map(goal -> {
            GoalDTO goalDTO = goalMapper.toGoalDTO(goal);
            goalDTO.setTrackings(goal.getTrackingRecords().stream().map(record -> {
                GoalTrackingRecordDTO trackingDTO = new GoalTrackingRecordDTO();
                trackingDTO.setDate(record.getDate());
                trackingDTO.setValue(record.getValue());
                trackingDTO.setNote(record.getNote());
                return trackingDTO;
            }).collect(Collectors.toList()));
            return goalDTO;
        }).collect(Collectors.toList());
    }
}
