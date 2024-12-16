package com.healthy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExpertDTO {
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 20, message = "El nombre debe tener 20 caracteres o menos")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(max = 20, message = "El apellido debe tener 20 caracteres o menos")
    private String lastName;

    @NotBlank(message = "La biografia es obligatoria.")
    @Size(max = 500, message = "La biografia debe tener 500 caracteres o menos")
    private String bio;

    @NotBlank(message = "La especialidad es obligatoria.")
    @Size(max = 20, message = "La especialidad debe tener 20 caracteres o menos")
    private String expertise;
}