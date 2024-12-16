package com.healthy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GoalTrackingRecordCreateDTO {
    private LocalDateTime date;
    private Float value;
    private String note;
}
