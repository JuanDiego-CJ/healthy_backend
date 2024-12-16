package com.healthy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "El usuario es obligatorio")
    private String user;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
