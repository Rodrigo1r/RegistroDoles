package com.doles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registros_dole")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id", nullable = false)
    private Clase clase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "n2_id", nullable = false)
    private N2 n2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "n3_id", nullable = false)
    private N3 n3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_evaluada_id", nullable = false)
    private Persona personaEvaluada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por_id", nullable = false)
    private Persona creadoPor;

    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private Integer puntaje;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @OneToMany(mappedBy = "registroDole", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaPregunta> respuestas = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void calcularPuntaje() {
        if (respuestas != null && !respuestas.isEmpty()) {
            long respuestasPositivas = respuestas.stream()
                    .filter(RespuestaPregunta::getRespuesta)
                    .count();
            this.puntaje = (int) ((respuestasPositivas * 100) / respuestas.size());
        } else {
            this.puntaje = 0;
        }
    }
}
