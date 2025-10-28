package com.doles.repository;

import com.doles.entity.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
    List<Clase> findByActivoTrue();
    Optional<Clase> findByNombre(String nombre);
}
