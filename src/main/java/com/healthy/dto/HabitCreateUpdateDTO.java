package com.healthy.dto;

import com.healthy.model.enums.Frequency;
import lombok.Data;
import jakarta.validation.constraints.*;
@Data

public class HabitCreateUpdateDTO {

    // El ID es opcional, ya que se usa para actualizaciones. No se valida para la creación.
    private Integer id;

    // Nombre del hábito: obligatorio, debe tener entre 1 y 50 caracteres
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    private String name;

    // Descripción: opcional, pero si se proporciona, debe tener un máximo de 100 caracteres
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 100, message = "La descripción no debe exceder los 100 caracteres")
    private String description;

    // Frecuencia: obligatorio, no puede ser nulo
    @NotNull(message = "La frecuencia es obligatoria")
    private Frequency frequency;

    // ID del tipo de hábito: obligatorio, debe ser un número positivo
    @NotNull(message = "El ID del tipo de hábito es obligatorio")
    @Positive(message = "El ID del tipo de hábito debe ser un número positivo")
    private Integer habitTypeId;

}