package com.doles.controller;

import com.doles.dto.RegistroDoleListResponse;
import com.doles.dto.RegistroDoleRequest;
import com.doles.dto.RegistroDoleResponse;
import com.doles.service.RegistroDoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistroDoleController {

    private final RegistroDoleService registroDoleService;

    @PostMapping
    public ResponseEntity<RegistroDoleResponse> crearRegistro(@Valid @RequestBody RegistroDoleRequest request) {
        RegistroDoleResponse response = registroDoleService.crearRegistro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{registroId}/preguntas/{preguntaId}/evidencias")
    public ResponseEntity<Void> agregarEvidencia(
            @PathVariable Long registroId,
            @PathVariable Long preguntaId,
            @RequestParam("file") MultipartFile file) {
        registroDoleService.agregarEvidencia(registroId, preguntaId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<RegistroDoleListResponse>> listarRegistros(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Long n2Id,
            @RequestParam(required = false) Long n3Id) {
        List<RegistroDoleListResponse> registros = registroDoleService.listarRegistros(fechaInicio, fechaFin, n2Id, n3Id);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDoleResponse> obtenerRegistro(@PathVariable Long id) {
        RegistroDoleResponse response = registroDoleService.obtenerRegistro(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estadisticas/por-area")
    public ResponseEntity<List<com.doles.dto.EstadisticaPorAreaResponse>> obtenerEstadisticasPorArea() {
        List<com.doles.dto.EstadisticaPorAreaResponse> estadisticas = registroDoleService.obtenerEstadisticasPorArea();
        return ResponseEntity.ok(estadisticas);
    }
}
