package com.healthy.mapper;

import com.healthy.dto.SubPlanDTO;

import com.healthy.dto.SubPlanDTO;
import com.healthy.model.entity.SubPlan;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubPlanMapper {

    private final ModelMapper modelMapper;

    public SubPlanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SubPlanDTO toDTO(SubPlan subPlan) {
        return modelMapper.map(subPlan, SubPlanDTO.class);
    }

    public SubPlan toEntity(SubPlanDTO subPlanDTO) {
        return modelMapper.map(subPlanDTO, SubPlan.class);
    }
}
