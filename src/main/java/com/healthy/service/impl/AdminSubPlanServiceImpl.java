package com.healthy.service.impl;

import com.healthy.dto.SubPlanDTO;
import com.healthy.exception.BadRequestException;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.SubPlanMapper;
import com.healthy.model.entity.SubPlan;
import com.healthy.repository.SubPlanRepository;
import com.healthy.service.SubPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminSubPlanServiceImpl implements SubPlanService {

    private final SubPlanRepository subPlanRepository;
    private final SubPlanMapper subPlanMapper;

    @Transactional
    @Override
    public List<SubPlanDTO> getAll() {
        List<SubPlan> profiles = subPlanRepository.findAll();
        return profiles.stream()
                .map(subPlanMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SubPlanDTO> paginate(Pageable pageable){
        return subPlanRepository.findAll(pageable)
                .map(subPlanMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public SubPlanDTO findById(Integer id) {
        SubPlan subPlan = subPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el plan de suscripcion: " + id));
        return subPlanMapper.toDTO(subPlan);
    }

    @Transactional
    @Override
    public SubPlanDTO create(SubPlanDTO subPlanDTO) {
        subPlanRepository.findByName(subPlanDTO.getName())
                .ifPresent(existingSubPlan -> {
                    throw new BadRequestException("El plan de suscripcion ya existe");
                });
        SubPlan subPlan = subPlanMapper.toEntity(subPlanDTO);
        subPlan = subPlanRepository.save(subPlan);
        return subPlanMapper.toDTO(subPlan);
    }

    @Transactional
    @Override
    public SubPlanDTO update(Integer id, SubPlanDTO updateSubPlanDTO) {
        SubPlan subPlanFromDb = subPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el plan de suscripcion: " + id));

        subPlanRepository.findByName(updateSubPlanDTO.getName())
                .filter(existingSubPlan -> !existingSubPlan.getId().equals(id))
                .ifPresent(existingSubPlan -> {
                    throw new BadRequestException("El plan de suscripcion ya existe con el mismo nombre");
                });

        subPlanFromDb.setName(updateSubPlanDTO.getName());
        subPlanFromDb.setDescription(updateSubPlanDTO.getDescription());
        subPlanFromDb.setPrice(updateSubPlanDTO.getPrice());
        subPlanFromDb.setDurationDays(updateSubPlanDTO.getDurationDays());

        subPlanFromDb = subPlanRepository.save(subPlanFromDb);
        return subPlanMapper.toDTO(subPlanFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        SubPlan subPlan = subPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El plan de suscripcion: " + id + "no fue encontrada"));
        subPlanRepository.delete(subPlan);
    }
}