package com.healthy.mapper;

import com.healthy.dto.HabitTypeDTO;
import com.healthy.model.entity.HabitType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HabitTypeMapper {

    private final ModelMapper modelMapper;

    public HabitTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public HabitTypeDTO toDTO(HabitType habitType) {
        return modelMapper.map(habitType, HabitTypeDTO.class);
    }

    public HabitType toEntity(HabitTypeDTO habitTypeDTO) {
        return modelMapper.map(habitTypeDTO, HabitType.class);
    }
}