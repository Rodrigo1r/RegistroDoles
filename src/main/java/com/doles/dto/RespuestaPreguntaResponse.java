package com.doles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaPreguntaResponse {

    private Long id;
    private String preguntaTexto;
    private Boolean respuesta;
    private List<EvidenciaResponse> evidencias;
}
