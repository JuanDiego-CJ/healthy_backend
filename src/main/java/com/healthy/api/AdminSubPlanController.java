package com.healthy.api;

import com.healthy.dto.SubPlanDTO;
import com.healthy.service.SubPlanService;
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
@RequestMapping("/admin/sub_plans")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSubPlanController {
    private final SubPlanService adminSubPlanService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<SubPlanDTO>> listAll() {
        List<SubPlanDTO> subPlans = adminSubPlanService.getAll();
        return new ResponseEntity<>(subPlans, HttpStatus.OK);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<SubPlanDTO>> paginate(
            @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<SubPlanDTO> page = adminSubPlanService.paginate(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<SubPlanDTO> getById(@PathVariable Integer id) {
        SubPlanDTO expert = adminSubPlanService.findById(id);
        return new ResponseEntity<>(expert, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubPlanDTO> create(@Valid @RequestBody SubPlanDTO subPlanDTO) {
        SubPlanDTO createdSubPlan = adminSubPlanService.create(subPlanDTO);
        return new ResponseEntity<>(createdSubPlan, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<SubPlanDTO> update(@PathVariable Integer id,
                                             @Valid @RequestBody SubPlanDTO subPlanDTO) {
        SubPlanDTO updatedSubPlan = adminSubPlanService.update(id, subPlanDTO);
        return new ResponseEntity<>(updatedSubPlan, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adminSubPlanService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}