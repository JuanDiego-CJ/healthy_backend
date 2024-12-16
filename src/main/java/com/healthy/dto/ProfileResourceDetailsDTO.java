package com.healthy.dto;

import com.healthy.model.enums.ResourceType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileResourceDetailsDTO {
    private boolean is_active;
    private LocalDateTime access_expiration;

    //RESOURCE EXPERT
    private String expertFirstName;
    private String expertLastName;
    private String expertise;

    //RESOURCE SubPlan
    private String subPlanName;

    //RESOURCE
    private String resourceTitle;
    private String description;
    private ResourceType resourceType;
    private String content;

    // USUARIO
    private String userName;
}
