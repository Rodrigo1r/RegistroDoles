package com.doles.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarPerfilRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    private String email;

    private String cargo;

    // Opcional - solo si el usuario quiere cambiar su contraseña
    private String nuevaPassword;
}
