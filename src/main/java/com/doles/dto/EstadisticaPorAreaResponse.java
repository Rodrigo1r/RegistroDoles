package com.doles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaPorAreaResponse {

    private String nombreArea;
    private Long cantidadRegistros;
    private Double puntajePromedio;
    private Double porcentajeCumplimiento;
}
