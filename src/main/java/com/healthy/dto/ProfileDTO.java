package com.healthy.dto;

import com.healthy.model.enums.Gender;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDTO {
    private Integer id;
    private String name;
    private Float height;
    private Float weight;
    private Integer age;
    private Gender gender;
    private String healthConditions;

    private List<PlanDTO> plans;
    private List<ProfileResourceDetailsDTO> resources;
    private List<ProfileSubscriptionDTO> subPlans;
}
