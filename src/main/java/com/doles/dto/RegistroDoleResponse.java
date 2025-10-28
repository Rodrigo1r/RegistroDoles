package com.doles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroDoleResponse {

    private Long id;
    private String empresa;
    private String clase;
    private String n2;
    private String n3;
    private String personaEvaluada;
    private String creadoPor;
    private LocalDateTime fechaRegistro;
    private Integer puntaje;
    private String observaciones;
    private List<RespuestaPreguntaResponse> respuestas;
}
