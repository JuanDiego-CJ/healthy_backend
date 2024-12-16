package com.healthy.dto;

import com.healthy.model.enums.Gender;
import lombok.Data;

@Data
public class ProfileUpdateDTO {
    private String userName;
    private Integer age;
    private Float height;
    private Float weight;
    private Gender gender;
    private String healthConditions;
}
