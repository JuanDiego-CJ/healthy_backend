package com.healthy.mapper;

import com.healthy.dto.GoalCreateDTO;
import com.healthy.dto.GoalDTO;
import com.healthy.dto.GoalTrackingRecordCreateDTO;
import com.healthy.dto.GoalTrackingRecordDTO;
import com.healthy.model.entity.*;
import com.healthy.model.enums.GoalStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    private final ModelMapper modelMapper;

    public GoalMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public Goal toGoalCreateDTO(GoalCreateDTO goalDTO) {
        Goal goal = modelMapper.map(goalDTO, Goal.class);
        Profile profile = new Profile();
        Habit habit = new Habit();
        Plan plan = new Plan();
        HabitType habitType = new HabitType();
        habit.setId(goalDTO.getHabitId());
        plan.setId(goalDTO.getPlanId());
        goal.setProfile(profile);
        goal.setHabit(habit);
        goal.setPlan(plan);
        goal.getHabit().setHabitType(habitType);
        goal.setGoalStatus(GoalStatus.IN_PROGRESS);

        // MAPEAR MANUALMENTE LAS LISTAS
        goal.setTrackingRecords(goalDTO.getTrackings().stream()
                .map(this::toTrackingRecordGoalEntity)
                .toList());
        return goal;
    }
    public GoalDTO toGoalDTO(Goal goal) {
        GoalDTO goalDTO = modelMapper.map(goal, GoalDTO.class);
        goalDTO.setGoalId(goal.getId());
        goalDTO.setTargetValue(goal.getTargetValue());
        goalDTO.setCurrentValue(goal.getCurrentValue());
        goalDTO.setStartDate(goal.getStartDate());
        goalDTO.setEndDate(goal.getEndDate());
        goalDTO.setGoalStatus(goal.getGoalStatus());

        // PROFILE
        goalDTO.setName(goal.getProfile().getUserName());
        //HABITO
        goalDTO.setHabitId(goal.getId());
        goalDTO.setHabitName(goal.getHabit().getName());
        goalDTO.setHabitTypeName(goal.getHabit().getHabitType().getName());
        goalDTO.setFrequency(goal.getHabit().getFrequency());


        goalDTO.setTrackings(goal.getTrackingRecords().stream()
                .map(this::toGoalTrackingRecordDTO)
                .toList());
        return goalDTO;
    }

    private TrackingRecord toTrackingRecordGoalEntity(GoalTrackingRecordCreateDTO gtrDTO) {
        TrackingRecord trackingRecord = modelMapper.map(gtrDTO, TrackingRecord.class);
        Profile profile = new Profile();
        trackingRecord.setDate(gtrDTO.getDate());
        trackingRecord.setNote(gtrDTO.getNote());
        trackingRecord.setValue(gtrDTO.getValue());
        return trackingRecord;
    }
    private GoalTrackingRecordDTO toGoalTrackingRecordDTO(TrackingRecord trackingRecord) {
        GoalTrackingRecordDTO gtrDTO = modelMapper.map(trackingRecord, GoalTrackingRecordDTO.class);
        gtrDTO.setDate(trackingRecord.getDate());
        gtrDTO.setNote(trackingRecord.getNote());
        gtrDTO.setValue(trackingRecord.getValue());
        return gtrDTO;
    }

    public Goal toEntity(GoalCreateDTO goalCreateUpdateDTO) {
        return modelMapper.map(goalCreateUpdateDTO, Goal.class);
    }

    public GoalCreateDTO toGoalCreateUpdateDTO(Goal goal) {
        return modelMapper.map(goal, GoalCreateDTO.class);
    }
}
