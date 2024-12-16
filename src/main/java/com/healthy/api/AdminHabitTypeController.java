package com.healthy.api;

import com.healthy.dto.HabitTypeDTO;
import com.healthy.service.AdminHabitTypeService;
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
@RequestMapping("/admin/habitTypes")
@PreAuthorize("hasRole('ADMIN')")
public class AdminHabitTypeController {
    private final AdminHabitTypeService adminHabitTypeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<HabitTypeDTO>> getAllHabitTypes() {
        List<HabitTypeDTO> habitTypes = adminHabitTypeService.getAll();
        return new ResponseEntity<>(habitTypes, HttpStatus.OK);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<HabitTypeDTO>> paginateHabitTypes(
            @PageableDefault(size = 5, sort = "name")Pageable pageable) {
        Page<HabitTypeDTO> habitTypes = adminHabitTypeService.paginate(pageable);
        return new ResponseEntity<>(habitTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<HabitTypeDTO> getHabitTypeById(@PathVariable("id") Integer id) {
        HabitTypeDTO habitType = adminHabitTypeService.findById(id);
        return new ResponseEntity<>(habitType, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HabitTypeDTO> createHabitType(@Valid @RequestBody HabitTypeDTO habitTypeDTO) {
        HabitTypeDTO newHabitType = adminHabitTypeService.create(habitTypeDTO);
        return new ResponseEntity<>(newHabitType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitTypeDTO> updateHabitType(@PathVariable("id") Integer id,
                                                        @Valid @RequestBody HabitTypeDTO habitTypeDTO) {
        HabitTypeDTO updatedHabitType = adminHabitTypeService.update(id, habitTypeDTO);
        return new ResponseEntity<>(updatedHabitType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabitType(@PathVariable("id") Integer id) {
        adminHabitTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*w*/
}