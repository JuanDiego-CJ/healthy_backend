package com.healthy.api;

import com.healthy.dto.ProfileCreateDTO;
import com.healthy.dto.ProfileDTO;
import com.healthy.dto.ProfileUpdateDTO;
import com.healthy.model.entity.Profile;
import com.healthy.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/profiles")
@PreAuthorize("hasRole('USER')")
public class AdminProfileController {
    private final ProfileService profileService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<ProfileDTO>> getAll(){
        List<ProfileDTO> profile = profileService.getAll();
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ProfileDTO> findByUsername(@PathVariable("username") String username){
        ProfileDTO profile = profileService.findByUsername(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProfileCreateDTO> create(@RequestBody @Valid ProfileCreateDTO profile){
        ProfileCreateDTO newProfile = profileService.create(profile);
        return new ResponseEntity<>(newProfile, HttpStatus.CREATED);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existProfile(@RequestParam String username){
        boolean exists = profileService.profileExists(username);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> update(@RequestBody @Valid ProfileCreateDTO profile){
        ProfileDTO updatedProfile = profileService.update(profile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }
}
