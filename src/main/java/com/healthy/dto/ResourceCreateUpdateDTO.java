package com.healthy.dto;

import com.healthy.model.enums.ResourceType;
import lombok.Data;

@Data
public class ResourceCreateUpdateDTO {
    private Integer id;

    private String title;

    private String description;

    private String content;

    private ResourceType resourceType;

    private Integer expertId;

    private Integer subPlanId;
}
