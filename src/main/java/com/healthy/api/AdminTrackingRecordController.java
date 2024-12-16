package com.healthy.api;

import com.healthy.dto.TrackingRecordCreateUpdateDTO;
import com.healthy.dto.TrackingRecordDetailsDTO;
import com.healthy.model.entity.TrackingRecord;
import com.healthy.service.AdminTrackingRecordService;
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
@RequestMapping("/admin/trackingrecords")
@PreAuthorize("hasRole('USER')")
public class AdminTrackingRecordController {
    private final AdminTrackingRecordService adminTrackingRecordService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<TrackingRecordDetailsDTO>> getAllTrackingRecords(){
        List<TrackingRecordDetailsDTO> trackingRecords = adminTrackingRecordService.getAll();
        return new ResponseEntity<>(trackingRecords, HttpStatus.OK);
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<TrackingRecordDetailsDTO>> paginateTrackingRecords(
            @PageableDefault(size=10, sort="date") Pageable pageable)
    {
        Page<TrackingRecordDetailsDTO> trackingRecords = adminTrackingRecordService.paginate(pageable);
        return new ResponseEntity<>(trackingRecords,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TrackingRecordDetailsDTO> getTrackingRecordById(@PathVariable("id") Integer id){
        TrackingRecordDetailsDTO trackingRecord = adminTrackingRecordService.findById(id);
        return new ResponseEntity<>(trackingRecord,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrackingRecordDetailsDTO> createTrackingRecord( @RequestBody TrackingRecordCreateUpdateDTO trackingRecord){
        TrackingRecordDetailsDTO newTrackingRecord = adminTrackingRecordService.create(trackingRecord);
        return new ResponseEntity<>(newTrackingRecord,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackingRecordDetailsDTO> updateTrackingRecord(@PathVariable("id") Integer id,
                                                                         @Valid @RequestBody TrackingRecordCreateUpdateDTO trackingRecord){
        TrackingRecordDetailsDTO updatedTrackingRecord = adminTrackingRecordService.update(id, trackingRecord);
        return new ResponseEntity<>(updatedTrackingRecord,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrackingRecord> deletePlan(@PathVariable("id") Integer id){
        adminTrackingRecordService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}