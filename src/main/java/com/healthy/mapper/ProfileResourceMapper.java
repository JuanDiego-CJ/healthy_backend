package com.healthy.mapper;
import com.healthy.dto.ProfileResourceCreateUpdateDTO;
import com.healthy.dto.ProfileResourceDetailsDTO;
import com.healthy.model.entity.ProfileResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class ProfileResourceMapper {
    private final ModelMapper modelMapper;

    public ProfileResourceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ProfileResourceDetailsDTO toDetailsDTO(ProfileResource profileResource) {
        ProfileResourceDetailsDTO profileResourceDTO = modelMapper.map(profileResource, ProfileResourceDetailsDTO.class);

        profileResourceDTO.setResourceTitle(profileResource.getResource().getTitle());
        profileResourceDTO.setDescription(profileResource.getResource().getDescription());
        profileResourceDTO.setResourceType(profileResource.getResource().getResourceType());
        profileResourceDTO.setContent(profileResource.getResource().getContent());
        profileResourceDTO.setExpertFirstName(profileResource.getResource().getExpert().getFirstName());
        profileResourceDTO.setExpertLastName(profileResource.getResource().getExpert().getLastName());
        profileResourceDTO.setExpertise(profileResource.getResource().getExpert().getExpertise());

        profileResourceDTO.setSubPlanName(profileResource.getResource().getSubPlan().getName());

        profileResourceDTO.setUserName(profileResource.getProfile().getUserName());

        return profileResourceDTO;
    }

    public ProfileResource toProfileResource(ProfileResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        return modelMapper.map(resourceCreateUpdateDTO, ProfileResource.class);
    }

    public ProfileResourceCreateUpdateDTO toProfileResourceCreateUpdateDTO(ProfileResource profileResource) {
        return modelMapper.map(profileResource, ProfileResourceCreateUpdateDTO.class);
    }
}
