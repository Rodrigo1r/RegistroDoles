package com.doles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaPorN3Response {

    private String nombreN3;
    private String nombreClase;
    private Long cantidadRegistros;
    private Double puntajePromedio;
    private Double porcentajeCumplimiento;
}
