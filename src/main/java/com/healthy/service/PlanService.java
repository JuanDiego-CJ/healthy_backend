package com.healthy.service;

import com.healthy.dto.PlanCreateDTO;
import com.healthy.dto.PlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanService {
    List<PlanDTO> getAll();
    Page<PlanDTO> paginate(Pageable pageable);
    PlanDTO findById(Integer id);
    PlanDTO create(PlanCreateDTO plan);
    PlanDTO update(Integer id, PlanCreateDTO updatePlanDTO);
    void delete(Integer id);
}
