package com.healthy.api;

import com.healthy.dto.HabitCreateUpdateDTO;
import com.healthy.dto.HabitDetailsDTO;
import com.healthy.model.entity.Habit;
import com.healthy.service.AdminHabitService;
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
@RequestMapping("/admin/habits")
@PreAuthorize("hasRole('USER')")
public class AdminHabitController {
    private final AdminHabitService adminHabitService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<HabitDetailsDTO>> getAllHabits(){
        List<HabitDetailsDTO> habits = adminHabitService.getAll();
        return new ResponseEntity<>(habits, HttpStatus.OK);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<HabitDetailsDTO>> paginateHabits(
            @PageableDefault(size=10, sort="name") Pageable pageable)
    {
        Page<HabitDetailsDTO> habits = adminHabitService.paginate(pageable);
        return new ResponseEntity<>(habits,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<HabitDetailsDTO> getHabitById(@PathVariable Integer id){
        HabitDetailsDTO habit = adminHabitService.findById(id);
        return new ResponseEntity<>(habit,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HabitDetailsDTO> createHabit(@Valid @RequestBody HabitCreateUpdateDTO habitFromDto){
        HabitDetailsDTO newHabit = adminHabitService.create(habitFromDto);
        return new ResponseEntity<>(newHabit,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitDetailsDTO> updateHabit(@PathVariable Integer id,
                                                       @Valid @RequestBody HabitCreateUpdateDTO habitFromDto){
        HabitDetailsDTO updatedHabit  = adminHabitService.update(id, habitFromDto);
        return new ResponseEntity<>(updatedHabit,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Habit> deleteHabit(@PathVariable("id") Integer id){
        adminHabitService.delete(id);
        return new ResponseEntity<Habit>(HttpStatus.NO_CONTENT);
    }
}