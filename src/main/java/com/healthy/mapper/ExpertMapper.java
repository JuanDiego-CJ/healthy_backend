package com.healthy.mapper;
import com.healthy.dto.ExpertDTO;
import com.healthy.model.entity.Expert;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ExpertMapper {
    private final ModelMapper modelMapper;

    public ExpertMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ExpertDTO toDTO(Expert expert) {
        return modelMapper.map(expert, ExpertDTO.class);
    }

    public Expert toEntity(ExpertDTO expertDTO) {
        return modelMapper.map(expertDTO, Expert.class);
    }
}
