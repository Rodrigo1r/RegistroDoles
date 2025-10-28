package com.doles.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDoleRequest {

    @NotNull(message = "La empresa es obligatoria")
    private Long empresaId;

    @NotNull(message = "La clase es obligatoria")
    private Long claseId;

    @NotNull(message = "El N2 es obligatorio")
    private Long n2Id;

    @NotNull(message = "El N3 es obligatorio")
    private Long n3Id;

    @NotNull(message = "La persona evaluada es obligatoria")
    private Long personaEvaluadaId;

    private String observaciones;

    @NotNull(message = "Las respuestas son obligatorias")
    private List<RespuestaPreguntaRequest> respuestas;
}
