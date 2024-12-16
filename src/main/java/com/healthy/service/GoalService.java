package com.healthy.service;

import com.healthy.dto.GoalCreateDTO;
import com.healthy.dto.GoalDTO;
import com.healthy.dto.PlanCreateDTO;
import com.healthy.dto.PlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoalService {
    GoalDTO create(GoalCreateDTO goalCreateDTO);
    Page<GoalDTO> paginate(Pageable pageable);
    List<GoalDTO> getAll();
    List<GoalDTO> getById(Integer id);
    GoalDTO update(Integer id, GoalCreateDTO updatedGoal);
    void deleteGoal(Integer id);
    List<GoalDTO> getDashboard(Integer profileId);
}
