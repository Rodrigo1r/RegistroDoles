package com.doles.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "n2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class N2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "n3_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private N3 n3;

    @Column(nullable = false)
    private Boolean activo = true;
}
