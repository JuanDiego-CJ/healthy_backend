package com.healthy.service;

import com.healthy.dto.TrackingRecordCreateUpdateDTO;
import com.healthy.dto.TrackingRecordDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminTrackingRecordService {
    List<TrackingRecordDetailsDTO> getAll();
    Page<TrackingRecordDetailsDTO> paginate(Pageable pageable);
    TrackingRecordDetailsDTO findById(int id);
    TrackingRecordDetailsDTO create(TrackingRecordCreateUpdateDTO trackingRecordCreateUpdateDTO);
    TrackingRecordDetailsDTO update(Integer id, TrackingRecordCreateUpdateDTO updateTrackingRecordDTO);
    void delete(Integer id);
}
