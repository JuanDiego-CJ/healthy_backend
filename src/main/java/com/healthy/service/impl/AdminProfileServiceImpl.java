package com.healthy.service.impl;


import com.healthy.dto.ProfileCreateDTO;
import com.healthy.dto.ProfileDTO;
import com.healthy.dto.ProfileUpdateDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.ProfileMapper;
import com.healthy.model.entity.Profile;
import com.healthy.model.entity.User;
import com.healthy.repository.ProfileRepository;
import com.healthy.repository.SubPlanRepository;
import com.healthy.repository.UserRepository;
import com.healthy.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final SubPlanRepository subPlanRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public List<ProfileDTO> getAll(){
        List<Profile> profiles = profileRepository.findAll();

        return profiles.stream()
                .map(profileMapper::toProfileDTO)
                .toList();
    }
    @Transactional(readOnly = true)
    @Override
    public ProfileDTO findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("El perfil de "+ username +" no se ha encontrado"));
        return profileMapper.toProfileDTO(profile);
    }
    @Override
    public ProfileCreateDTO create(ProfileCreateDTO profileCreateDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User "+username+" not found"));

        Profile profile = profileMapper.toProfile(profileCreateDTO);
        profile.setUser(user);
        profileRepository.save(profile);
        return profileCreateDTO;
    }

    @Override
    public boolean profileExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && user.get().getProfile() != null;
    }

    @Transactional
    @Override
    public ProfileDTO update(ProfileCreateDTO profileUpdateDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("El perfil de "+ username +" no se ha encontrado"));

        profile.setUserName(profileUpdateDTO.getUserName());
        profile.setAge(profileUpdateDTO.getAge());
        profile.setHeight(profileUpdateDTO.getHeight());
        profile.setWeight(profileUpdateDTO.getWeight());
        profile.setGender(profileUpdateDTO.getGender());
        profile.setHealthConditions(profileUpdateDTO.getHealthConditions());

        return profileMapper.toProfileDTO(profileRepository.save(profile));
    }
}
