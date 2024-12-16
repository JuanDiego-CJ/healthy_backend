package com.healthy.dto;

import com.healthy.model.enums.PlanStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlanDTO {
    private Integer planId;
    private Integer profileId;
    private String planName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PlanStatus planStatus;
    private List<GoalDTO> goals;

}
