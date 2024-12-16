package com.healthy.dto;

import com.healthy.model.enums.Frequency;
import com.healthy.model.enums.GoalStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GoalDTO {

    //GOAL
    private Integer goalId;
    private Float targetValue;
    private Float currentValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private GoalStatus goalStatus;

    // PERFIL
    private String name;
    // HABITO
    private Integer habitId;
    private String habitName;
    private String habitTypeName;
    private Frequency frequency;

    private List<GoalTrackingRecordDTO> trackings;
}
