package com.healthy.api;

import com.healthy.dto.GoalCreateDTO;
import com.healthy.dto.GoalDTO;
import com.healthy.model.entity.Goal;
import com.healthy.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/goals")
@PreAuthorize("hasRole('USER')")
public class AdminGoalController {
    private final GoalService goalService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<GoalDTO>> getById(@PathVariable("id") Integer id) {
        List<GoalDTO> goal = goalService.getById(id);
        return ResponseEntity.ok(goal);
    }
    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<GoalDTO>> getPage(@PageableDefault(size=5) Pageable pageable){
        Page<GoalDTO> goal = goalService.paginate(pageable);
        return new ResponseEntity<>(goal, HttpStatus.OK);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<GoalDTO>> getAll() {
        List<GoalDTO> goal = goalService.getAll();
        return ResponseEntity.ok(goal);
    }
    @PostMapping
    public ResponseEntity<GoalDTO> createGoal(@RequestBody GoalCreateDTO goalDTO) {
        GoalDTO goal = goalService.create(goalDTO);
        return new ResponseEntity<>(goal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody GoalCreateDTO goalDTO) {
        GoalDTO updatedGoal = goalService.update(id,goalDTO);
        return new ResponseEntity<>(updatedGoal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Goal> deleteGoal(@PathVariable("id") Integer id) {
        goalService.deleteGoal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/dashboard/{profileId}")
    public ResponseEntity<List<GoalDTO>> getDashboard(@PathVariable Integer profileId) {
        List<GoalDTO> dashboard = goalService.getDashboard(profileId);
        return ResponseEntity.ok(dashboard);
    }
}
