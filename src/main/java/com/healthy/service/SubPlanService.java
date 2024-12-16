package com.healthy.service;

import com.healthy.dto.SubPlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface SubPlanService {
    List<SubPlanDTO> getAll();
    Page<SubPlanDTO> paginate(Pageable pageable);
    SubPlanDTO findById(Integer id);
    SubPlanDTO create(SubPlanDTO subPlanDTO);
    SubPlanDTO update(Integer id, SubPlanDTO subPlanDTO);
    void delete(Integer id);
}
