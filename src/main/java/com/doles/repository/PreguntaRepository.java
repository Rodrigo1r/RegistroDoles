package com.doles.repository;

import com.doles.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByActivoTrueOrderByOrdenAsc();
    List<Pregunta> findAllByOrderByOrdenAsc();
}
