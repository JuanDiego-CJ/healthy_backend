package com.healthy.api;


import com.healthy.dto.ProfileResourceCreateUpdateDTO;
import com.healthy.dto.ProfileResourceDetailsDTO;
import com.healthy.service.ProfileResourceService;
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
@RequestMapping("/admin/profile_resources")
@PreAuthorize("hasRole('USER')")
public class AdminProfileResourceController {
    private final ProfileResourceService adminProfileResourceService;

    @GetMapping

    public ResponseEntity<List<ProfileResourceDetailsDTO>> getAll() {
        List<ProfileResourceDetailsDTO> profileResource = adminProfileResourceService.getAll();
        return new ResponseEntity<>(profileResource, HttpStatus.OK);
    }

    @GetMapping("/page")

    public ResponseEntity<Page<ProfileResourceDetailsDTO>> paginate(
            @PageableDefault(size=5) Pageable pageable)
    {
        Page<ProfileResourceDetailsDTO> profileResource = adminProfileResourceService.paginate(pageable);
        return new ResponseEntity<>(profileResource, HttpStatus.OK);
    }

    @GetMapping("/{id}")

    public ResponseEntity<ProfileResourceDetailsDTO> getById(@PathVariable Integer id) {
        ProfileResourceDetailsDTO profileResource = adminProfileResourceService.findById(id);
        return new ResponseEntity<>(profileResource, HttpStatus.OK);
    }

    @PostMapping

    public ResponseEntity<ProfileResourceDetailsDTO> create(@Valid @RequestBody ProfileResourceCreateUpdateDTO profileResourceFromDto) {
        ProfileResourceDetailsDTO newProfileResource = adminProfileResourceService.create(profileResourceFromDto);
        return new ResponseEntity<>(newProfileResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")

    public ResponseEntity<ProfileResourceDetailsDTO> update(@PathVariable Integer id,
                                                            @Valid @RequestBody ProfileResourceCreateUpdateDTO profileResourceFromDto) {
        ProfileResourceDetailsDTO updatedProfileResource = adminProfileResourceService.update(id, profileResourceFromDto);
        return new ResponseEntity<>(updatedProfileResource, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<ProfileResourceDetailsDTO> delete(@PathVariable("id") Integer id) {
        adminProfileResourceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}