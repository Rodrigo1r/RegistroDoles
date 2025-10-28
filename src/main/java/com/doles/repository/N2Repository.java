package com.doles.repository;

import com.doles.entity.N2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface N2Repository extends JpaRepository<N2, Long> {
    List<N2> findByActivoTrue();
    List<N2> findByN3IdAndActivoTrue(Long n3Id);
    Optional<N2> findByNombre(String nombre);
}
