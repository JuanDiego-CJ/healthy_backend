package com.healthy.mapper;

import org.modelmapper.ModelMapper;
import com.healthy.dto.TrackingRecordCreateUpdateDTO;
import com.healthy.dto.TrackingRecordDetailsDTO;
import com.healthy.model.entity.TrackingRecord;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class TrackingRecordMapper {
    private final ModelMapper modelMapper;

    public TrackingRecordMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        //Configurar ModelMapper para que sea estricto
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public TrackingRecordDetailsDTO toDetailsDTO(TrackingRecord trackingRecord) {
        TrackingRecordDetailsDTO trackingRecordDetails = modelMapper.map(trackingRecord, TrackingRecordDetailsDTO.class);

        //PARA GOALS
        trackingRecordDetails.setTargetValue(trackingRecord.getGoal().getTargetValue());
        trackingRecordDetails.setCurrentValue(trackingRecord.getGoal().getCurrentValue());
        trackingRecordDetails.setGoalStatus(trackingRecord.getGoal().getGoalStatus());

        //PARA HABIT
        trackingRecordDetails.setHabitName(trackingRecord.getGoal().getHabit().getName());
        trackingRecordDetails.setHabitDescription(trackingRecord.getGoal().getHabit().getDescription());
        trackingRecordDetails.setHabitFrequency(trackingRecord.getGoal().getHabit().getFrequency());

        //PARA HABIT TYPE
        trackingRecordDetails.setHabitTypeName(trackingRecord.getGoal().getHabit().getHabitType().getName());
        trackingRecordDetails.setHabitTypeDescription(trackingRecord.getGoal().getHabit().getHabitType().getDescription());

        return trackingRecordDetails;

    }

    public TrackingRecord toEntity(TrackingRecordCreateUpdateDTO trackingRecordCreateUpdateDTO) {
        return modelMapper.map(trackingRecordCreateUpdateDTO, TrackingRecord.class);
    }

    public TrackingRecordCreateUpdateDTO toCreateDTO(TrackingRecord trackingRecord) {
        return modelMapper.map(trackingRecord, TrackingRecordCreateUpdateDTO.class);
    }

}