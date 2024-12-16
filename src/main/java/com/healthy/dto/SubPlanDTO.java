package com.healthy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SubPlanDTO {

    private Integer id;
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 20, message = "El nombre debe tener 20 caracteres o menos")
    private String name;
    @NotBlank(message = "La descripcion es obligatoria.")
    @Size(max = 500, message = "La descripcion debe tener 500 caracteres o menos")
    private String description;
    private BigDecimal price;
    private Integer durationDays;
}
