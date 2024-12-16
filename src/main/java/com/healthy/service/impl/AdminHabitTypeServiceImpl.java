package com.healthy.service.impl;

import com.healthy.dto.HabitTypeDTO;
import com.healthy.exception.BadRequestException;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.HabitTypeMapper;
import com.healthy.model.entity.HabitType;
import com.healthy.repository.HabitTypeRepository;
import com.healthy.service.AdminHabitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminHabitTypeServiceImpl implements AdminHabitTypeService {

    private final HabitTypeRepository habitTypeRepository;
    private final HabitTypeMapper habitTypeMapper;

    @Transactional(readOnly = true)
    @Override
    public List<HabitTypeDTO> getAll() {
        List<HabitType> habitTypes = habitTypeRepository.findAll();
        return habitTypes.stream()
                .map(habitTypeMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<HabitTypeDTO> paginate(Pageable pageable) {
        Page<HabitType> habitTypes = habitTypeRepository.findAll(pageable);
        return habitTypes.map(habitTypeMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public HabitTypeDTO findById(Integer id) {
        HabitType habitType = habitTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoria con ID " + id + " no fue encontrada"));
        return habitTypeMapper.toDTO(habitType);
    }

    @Transactional
    @Override
    public HabitTypeDTO create(HabitTypeDTO habitTypeDTO) {
        habitTypeRepository.findByName(habitTypeDTO.getName())
                .ifPresent(existingHabitType -> {
                    throw new BadRequestException("El tipo de habito ya existe con el mismo nombre");
                });
        HabitType habitType = habitTypeMapper.toEntity(habitTypeDTO);
        habitType.setCreatedAt(LocalDateTime.now());
        habitType = habitTypeRepository.save(habitType);
        return habitTypeMapper.toDTO(habitType);
    }

    @Transactional
    @Override
    public HabitTypeDTO update(Integer id, HabitTypeDTO updateHabitTypeDTO) {
        HabitType habitTypeFromDb = habitTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de habito con ID " + id + " no fue encontrada"));

        habitTypeRepository.findByName(updateHabitTypeDTO.getName())
                .filter(existingHabitType -> !existingHabitType.getId().equals(id))
                .ifPresent(existingHabitType -> {
                    throw new BadRequestException("El tipo de habito ya existe con el mismo nombre");
                });

        habitTypeFromDb.setName(updateHabitTypeDTO.getName());
        habitTypeFromDb.setDescription(updateHabitTypeDTO.getDescription());
        habitTypeFromDb.setUpdatedAt(LocalDateTime.now());

        habitTypeFromDb = habitTypeRepository.save(habitTypeFromDb);
        return habitTypeMapper.toDTO(habitTypeFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        HabitType habitType = habitTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de habito con ID " + id + " no fue encontrada"));
        habitTypeRepository.delete(habitType);
    }
}