package com.healthy.service.impl;

import com.healthy.dto.ResourceCreateUpdateDTO;
import com.healthy.dto.ResourceDetailsDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.ResourceMapper;
import com.healthy.model.entity.Expert;
import com.healthy.model.entity.Resource;
import com.healthy.model.entity.SubPlan;
import com.healthy.repository.ExpertRepository;
import com.healthy.repository.ResourceRepository;
import com.healthy.repository.SubPlanRepository;
import com.healthy.service.AdminResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminResourceServiceImpl implements AdminResourceService {

    private final ResourceMapper resourceMapper;
    private final ResourceRepository resourceRepository;
    private final ExpertRepository expertRepository;
    private final SubPlanRepository subPlanRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ResourceDetailsDTO> getAll() {
        List<Resource> resources = resourceRepository.findAll();
        return resources.stream()
                .map(resourceMapper::toDetailsDTO)
                .toList();
    }

    @Override
    public Page<ResourceDetailsDTO> paginate(Pageable pageable) {
        return resourceRepository.findAll(pageable)
                .map(resourceMapper::toDetailsDTO);
    }

    @Override
    public ResourceDetailsDTO findById(Integer id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado"));
        return resourceMapper.toDetailsDTO(resource);
    }

    @Transactional
    @Override
    public ResourceDetailsDTO create(ResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        Expert expert = expertRepository.findById(resourceCreateUpdateDTO.getExpertId())
                .orElseThrow(() -> new ResourceNotFoundException("Experto no encontrado"));

        SubPlan subPlan = subPlanRepository.findById(resourceCreateUpdateDTO.getSubPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan de suscripcion no encontrado"));

        Resource resource = resourceMapper.toResource(resourceCreateUpdateDTO);
        resource.setExpert(expert);
        resource.setSubPlan(subPlan);

        return resourceMapper.toDetailsDTO(resourceRepository.save(resource));
    }

    @Transactional
    @Override
    public ResourceDetailsDTO update(Integer id, ResourceCreateUpdateDTO resourceCreateUpdateDTO) {
        Resource resourceFromDb = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado"));

        Expert expertFromDb = expertRepository.findById(resourceCreateUpdateDTO.getExpertId())
                .orElseThrow(() -> new ResourceNotFoundException("Experto no encontrado"));

        SubPlan subPlanFromDb = subPlanRepository.findById(resourceCreateUpdateDTO.getSubPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan de suscripcion no encontrado"));

        resourceFromDb.setExpert(expertFromDb);
        resourceFromDb.setSubPlan(subPlanFromDb);
        resourceFromDb.setId(resourceCreateUpdateDTO.getId());
        resourceFromDb.setTitle(resourceCreateUpdateDTO.getTitle());
        resourceFromDb.setDescription(resourceCreateUpdateDTO.getDescription());
        resourceFromDb.setResourceType(resourceCreateUpdateDTO.getResourceType());
        resourceFromDb.setContent(resourceCreateUpdateDTO.getContent());

        return resourceMapper.toDetailsDTO(resourceRepository.save(resourceFromDb));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado"));
        resourceRepository.delete(resource);
    }
}