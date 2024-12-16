package com.healthy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingRecordCreateUpdateDTO {

    private Integer id;

    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser un valor positivo")
    private Float value;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción debe tener 500 caracteres o menos")
    private String note;

    private LocalDateTime date;

    @NotNull(message = "El id del objetivo es obligatorio")
    private Integer goal_id;

}