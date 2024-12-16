package com.healthy.repository;

import com.healthy.model.entity.TrackingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRecordRepository extends JpaRepository<TrackingRecord, Integer> {
}
