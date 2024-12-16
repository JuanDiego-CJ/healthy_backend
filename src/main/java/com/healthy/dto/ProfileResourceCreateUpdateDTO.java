package com.healthy.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileResourceCreateUpdateDTO {
    private Integer id;
    private boolean is_active;
    private LocalDateTime access_expiration;

    private Integer profileId;
    private Integer resourceId;
}
