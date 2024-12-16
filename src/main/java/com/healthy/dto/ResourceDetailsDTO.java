package com.healthy.dto;

import com.healthy.model.enums.ResourceType;
import lombok.Data;

@Data
public class ResourceDetailsDTO {

    //private Integer id;

    private String title;
    private String description;
    private ResourceType resourceType;
    private String content;

    private String expertFirstName;
    private String expertLastName;
    private String expertExpertise;

    private String subPlanName;
    private String subPlanPrice;
}