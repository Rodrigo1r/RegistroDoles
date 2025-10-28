package com.doles.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "n3")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class N3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clase_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Clase clase;

    @Column(nullable = false)
    private Boolean activo = true;
}
