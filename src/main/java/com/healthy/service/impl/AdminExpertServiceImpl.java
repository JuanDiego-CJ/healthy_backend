package com.healthy.service.impl;

import com.healthy.dto.ExpertDTO;
import com.healthy.exception.BadRequestException;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.ExpertMapper;
import com.healthy.model.entity.Expert;
import com.healthy.repository.ExpertRepository;
import com.healthy.service.AdminExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminExpertServiceImpl implements AdminExpertService {
    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ExpertDTO> getAll() {
        List<Expert> experts = expertRepository.findAll();
        return experts.stream()
                .map(expertMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ExpertDTO> paginate(Pageable pageable) {
        Page<Expert> experts = expertRepository.findAll(pageable);
        return experts.map(expertMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public ExpertDTO findById(Integer id) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("El experto con ID " + id + " no existe."));
        return expertMapper.toDTO(expert);
    }

    @Transactional
    @Override
    public ExpertDTO create(ExpertDTO expertDTO) {
        expertRepository.findByFirstNameAndLastName(expertDTO.getFirstName(), expertDTO.getLastName())
                .ifPresent(existingExpert -> {
                    throw new BadRequestException("El experto ya existe con el mismo nombre y apellido.");
                });

        Expert expert = expertMapper.toEntity(expertDTO);
        expert.setCreatedAt(LocalDateTime.now());
        expert = expertRepository.save(expert);
        return expertMapper.toDTO(expert);
    }

    @Transactional
    @Override
    public ExpertDTO update(Integer id, ExpertDTO updateExpertDTO) {
        Expert expertFromDb = expertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El experto con ID " + id + " no existe."));

        expertRepository.findByFirstNameAndLastName(updateExpertDTO.getFirstName(), updateExpertDTO.getLastName())
                .filter(existingExpert -> !existingExpert.getId().equals(id))
                .ifPresent(existingExpert -> {
                    throw new BadRequestException("Ya existe un experto con el mismo nombre y apellido");
                });

        expertFromDb.setFirstName(updateExpertDTO.getFirstName());
        expertFromDb.setLastName(updateExpertDTO.getLastName());
        expertFromDb.setBio(updateExpertDTO.getBio());
        expertFromDb.setExpertise(updateExpertDTO.getExpertise());
        expertFromDb.setUpdatedAt(LocalDateTime.now());

        expertFromDb = expertRepository.save(expertFromDb);
        return expertMapper.toDTO(expertFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El experto con ID " + id + " no existe."));
        expertRepository.delete(expert);
    }
}