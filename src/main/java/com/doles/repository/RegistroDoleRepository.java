package com.doles.repository;

import com.doles.entity.RegistroDole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroDoleRepository extends JpaRepository<RegistroDole, Long>, JpaSpecificationExecutor<RegistroDole> {

    List<RegistroDole> findByFechaRegistroBetween(LocalDateTime inicio, LocalDateTime fin);

    List<RegistroDole> findByN2Id(Long n2Id);

    List<RegistroDole> findByN3Id(Long n3Id);

    List<RegistroDole> findByEmpresaId(Long empresaId);
}
