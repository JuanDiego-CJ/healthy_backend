package com.healthy.service;

import com.healthy.dto.HabitCreateUpdateDTO;
import com.healthy.dto.HabitDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdminHabitService {
    List<HabitDetailsDTO> getAll();
    Page<HabitDetailsDTO> paginate(Pageable pageable);
    HabitDetailsDTO findById(Integer id);
    HabitDetailsDTO create(HabitCreateUpdateDTO habitCreateUpdateDTO);
    HabitDetailsDTO update(Integer id, HabitCreateUpdateDTO habitCreateUpdateDTO);
    void delete(Integer id);
}