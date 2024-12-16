package com.healthy.mapper;

import com.healthy.dto.ResourceCreateUpdateDTO;
import com.healthy.dto.ResourceDetailsDTO;
import com.healthy.model.entity.Resource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {
    private final ModelMapper modelMapper;

    public ResourceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ResourceDetailsDTO toDetailsDTO(Resource resource) {
        ResourceDetailsDTO resourceDetailsDTO = modelMapper.map(resource, ResourceDetailsDTO.class);

        resourceDetailsDTO.setExpertFirstName(resource.getExpert().getFirstName());
        resourceDetailsDTO.setExpertLastName(resource.getExpert().getLastName());
        resourceDetailsDTO.setExpertExpertise(resource.getExpert().getExpertise());

        resourceDetailsDTO.setSubPlanName(resource.getSubPlan().getName());
        resourceDetailsDTO.setSubPlanPrice(resource.getSubPlan().getPrice().toString());

        return resourceDetailsDTO;
    }

    public Resource toResource(ResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        return modelMapper.map(resourceCreateUpdateDTO, Resource.class);
    }

    public ResourceCreateUpdateDTO toResourceCreateUpdateDTO(Resource resource) {
        return modelMapper.map(resource, ResourceCreateUpdateDTO.class);
    }
}
