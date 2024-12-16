package com.healthy.service.impl;

import com.healthy.dto.TrackingRecordCreateUpdateDTO;
import com.healthy.dto.TrackingRecordDetailsDTO;
import com.healthy.exception.ResourceNotFoundException;
import com.healthy.mapper.TrackingRecordMapper;
import com.healthy.model.entity.Goal;
import com.healthy.model.entity.TrackingRecord;
import com.healthy.repository.TrackingRecordRepository;
import com.healthy.repository.HabitRepository;
import com.healthy.repository.HabitTypeRepository;
import com.healthy.repository.GoalRepository;


import com.healthy.service.AdminTrackingRecordService;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminTrackingRecordServiceImpl implements AdminTrackingRecordService {

    private final TrackingRecordRepository trackingRecordRepository;
    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;
    private final HabitTypeRepository habitTypeRepository;
    private final TrackingRecordMapper trackingRecordMapper;

    @Transactional(readOnly = true)
    @Override
    public List<TrackingRecordDetailsDTO> getAll() {
        List<TrackingRecord> trackingRecords = trackingRecordRepository.findAll();
        return trackingRecords.stream()
                .map(trackingRecordMapper::toDetailsDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TrackingRecordDetailsDTO> paginate(Pageable pageable) {
        return trackingRecordRepository.findAll(pageable)
                .map(trackingRecordMapper::toDetailsDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public TrackingRecordDetailsDTO findById(int id) {
        TrackingRecord trackingRecord = trackingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found with id " + id));
        return trackingRecordMapper.toDetailsDTO(trackingRecord);
    }

    @Transactional
    @Override
    public TrackingRecordDetailsDTO create(TrackingRecordCreateUpdateDTO trackingRecordCreateUpdateDTO) {
        Goal goal = goalRepository.findById(trackingRecordCreateUpdateDTO.getGoal_id())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: "+trackingRecordCreateUpdateDTO.getGoal_id()));

        TrackingRecord trackingRecord = trackingRecordMapper.toEntity(trackingRecordCreateUpdateDTO);
        trackingRecord.setGoal(goal);
        trackingRecord.setDate(LocalDateTime.now());
        return trackingRecordMapper.toDetailsDTO(trackingRecordRepository.save(trackingRecord));
    }

    @Transactional
    @Override
    public TrackingRecordDetailsDTO update(Integer id, TrackingRecordCreateUpdateDTO updateTrackingRecord) {
        TrackingRecord trackingRecordFromDb = trackingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found with id " + id));

        Goal goal = goalRepository.findById(updateTrackingRecord.getGoal_id())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: "+updateTrackingRecord.getGoal_id()));

        trackingRecordFromDb.setGoal(goal);
        trackingRecordFromDb.setDate(LocalDateTime.now());
        trackingRecordFromDb.setNote(updateTrackingRecord.getNote());
        trackingRecordFromDb.setValue(updateTrackingRecord.getValue());
        return trackingRecordMapper.toDetailsDTO(trackingRecordRepository.save(trackingRecordFromDb));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        TrackingRecord trackingrecord = trackingRecordRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No record found with id " + id));
        trackingRecordRepository.delete(trackingrecord);
    }
}
