package com.healthy.dto;

import com.healthy.model.enums.Frequency;
import lombok.Data;

@Data
public class HabitDetailsDTO {
    private Integer id;
    private String name;
    private String description;
    private Frequency frequency;

    private String habitTypeName;
    private String habitTypeDescription;
}
