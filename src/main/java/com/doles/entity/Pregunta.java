package com.doles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preguntas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(nullable = false)
    private Integer orden;

    @Column(nullable = false)
    private Boolean requiereEvidencia = true;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column
    private String categoria; // Por ejemplo: "Clasificar", "Ordenar", "Limpiar", etc.
}
