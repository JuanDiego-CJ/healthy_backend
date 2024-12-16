package com.healthy.service.impl;


import com.healthy.dto.ProfileResourceCreateUpdateDTO;
import com.healthy.dto.ProfileResourceDetailsDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.ProfileResourceMapper;
import com.healthy.model.entity.Profile;
import com.healthy.model.entity.ProfileResource;
import com.healthy.model.entity.Resource;
import com.healthy.repository.ProfileRepository;
import com.healthy.repository.ProfileResourceRepository;
import com.healthy.repository.ResourceRepository;
import com.healthy.service.ProfileResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminProfileResourceServiceImpl implements ProfileResourceService {
    private final ProfileResourceMapper profileResourceMapper;
    private final ProfileResourceRepository profileResourceRepository;
    private final ProfileRepository profileRepository;
    private final ResourceRepository resourceRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ProfileResourceDetailsDTO> getAll() {
        List<ProfileResource> profileResources = profileResourceRepository.findAll();
        return profileResources.stream()
                .map(profileResourceMapper::toDetailsDTO)
                .toList();
    }

    @Override
    public Page<ProfileResourceDetailsDTO> paginate(Pageable pageable) {
        return profileResourceRepository.findAll(pageable)
                .map(profileResourceMapper::toDetailsDTO);
    }

    @Override
    public ProfileResourceDetailsDTO findById(Integer id) {
        ProfileResource profileResource = profileResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        return profileResourceMapper.toDetailsDTO(profileResource);
    }

    @Transactional
    @Override
    public ProfileResourceDetailsDTO create(ProfileResourceCreateUpdateDTO profileResourceCreateUpdateDTO) {
        Profile profile = profileRepository.findById(profileResourceCreateUpdateDTO.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        Resource resource = resourceRepository.findById(profileResourceCreateUpdateDTO.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        ProfileResource profileResource = profileResourceMapper.toProfileResource(profileResourceCreateUpdateDTO);
        profileResource.setProfile(profile);
        profileResource.setResource(resource);
        return profileResourceMapper.toDetailsDTO(profileResourceRepository.save(profileResource));
    }

    @Transactional
    @Override
    public ProfileResourceDetailsDTO update(Integer id, ProfileResourceCreateUpdateDTO profileResourceCreateUpdateDTO) {
        ProfileResource profileResource = profileResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        Profile profileFromDb = profileRepository.findById(profileResourceCreateUpdateDTO.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        Resource resourceFromDb = resourceRepository.findById(profileResourceCreateUpdateDTO.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));

        profileResource.setProfile(profileFromDb);
        profileResource.setResource(resourceFromDb);
        profileResource.setId(profileResourceCreateUpdateDTO.getId());
        profileResource.setAccess_expiration(profileResourceCreateUpdateDTO.getAccess_expiration());
        profileResource.set_active(profileResourceCreateUpdateDTO.is_active());
        return profileResourceMapper.toDetailsDTO(profileResourceRepository.save(profileResource));
    }

    public void delete(Integer id) {
        ProfileResource profileResource = profileResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        profileResourceRepository.delete(profileResource);
    }
}
