package com.doles.repository;

import com.doles.entity.RespuestaPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaPreguntaRepository extends JpaRepository<RespuestaPregunta, Long> {
    List<RespuestaPregunta> findByRegistroDoleId(Long registroDoleId);
}
