package com.healthy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HabitTypeDTO {
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener 50 caracteres o menos")
    private String name;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 500, message = "La descripcion debe tener 500 caracteres o menos")
    private String description;
}
