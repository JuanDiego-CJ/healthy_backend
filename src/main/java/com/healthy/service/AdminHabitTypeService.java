package com.healthy.service;

import com.healthy.dto.HabitTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminHabitTypeService {
    List<HabitTypeDTO> getAll();
    Page<HabitTypeDTO> paginate(Pageable pageable);
    HabitTypeDTO findById(Integer id);
    HabitTypeDTO create(HabitTypeDTO habitTypeDTO);
    HabitTypeDTO update(Integer id, HabitTypeDTO updateHabitTypeDTO);
    void delete(Integer id);
}