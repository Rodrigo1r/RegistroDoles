package com.doles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvidenciaResponse {

    private Long id;
    private String nombreArchivo;
    private String rutaArchivo;
    private String tipoMime;
    private Long tamanoBytes;
    private LocalDateTime fechaSubida;
}
