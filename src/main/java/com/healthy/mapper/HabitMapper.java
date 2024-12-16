package com.healthy.mapper;

import com.healthy.dto.HabitCreateUpdateDTO;
import com.healthy.dto.HabitDetailsDTO;
import com.healthy.model.entity.Habit;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class HabitMapper {
    private final
    ModelMapper modelMapper;

    public HabitMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public HabitDetailsDTO toDetailsDTO(Habit habit) {
        HabitDetailsDTO habitDetailsDTO = modelMapper.map(habit, HabitDetailsDTO.class);
        habitDetailsDTO.setHabitTypeName(habit.getHabitType().getName());
        habitDetailsDTO.setHabitTypeDescription(habit.getHabitType().getDescription());
        return habitDetailsDTO;

    }
    public Habit toEntity(HabitCreateUpdateDTO habitCreateUpdateDTO) {
        return modelMapper.map(habitCreateUpdateDTO, Habit.class);
    }

    public HabitCreateUpdateDTO toCreateUpdateDTO(Habit habit) {
        return modelMapper.map(habit, HabitCreateUpdateDTO.class);
    }
}
