package com.doles.repository;

import com.doles.entity.N3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface N3Repository extends JpaRepository<N3, Long> {
    List<N3> findByActivoTrue();
    List<N3> findByClaseIdAndActivoTrue(Long claseId);
    Optional<N3> findByNombre(String nombre);
}
