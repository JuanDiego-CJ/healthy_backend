package com.healthy.api;

import com.healthy.dto.ExpertDTO;
import com.healthy.service.AdminExpertService;
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
@RequestMapping("/admin/experts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminExpertController {
    private final AdminExpertService adminExpertService;

    @GetMapping
    public ResponseEntity<List<ExpertDTO>> listAll() {
        List<ExpertDTO> experts = adminExpertService.getAll();
        return new ResponseEntity<>(experts, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ExpertDTO>> paginate(
            @PageableDefault(size = 5, sort = "firstName")Pageable pageable) {
        Page<ExpertDTO> page = adminExpertService.paginate(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpertDTO> getById(@PathVariable Integer id) {
        ExpertDTO expert = adminExpertService.findById(id);
        return new ResponseEntity<>(expert, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpertDTO> create(@Valid @RequestBody ExpertDTO expertDTO) {
        ExpertDTO createdExpert = adminExpertService.create(expertDTO);
        return new ResponseEntity<>(createdExpert, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpertDTO> update(@PathVariable Integer id,
                                            @Valid @RequestBody ExpertDTO expertDTO) {
        ExpertDTO updatedExpert = adminExpertService.update(id,expertDTO) ;
        return new ResponseEntity<>(updatedExpert, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adminExpertService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}