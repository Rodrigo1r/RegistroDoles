package com.doles.controller;

import com.doles.entity.*;
import com.doles.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CatalogosController {

    private final EmpresaRepository empresaRepository;
    private final ClaseRepository claseRepository;
    private final N2Repository n2Repository;
    private final N3Repository n3Repository;
    private final PersonaRepository personaRepository;
    private final PreguntaRepository preguntaRepository;

    @GetMapping("/empresas")
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        return ResponseEntity.ok(empresaRepository.findByActivoTrue());
    }

    @GetMapping("/clases")
    public ResponseEntity<List<Clase>> listarClases() {
        return ResponseEntity.ok(claseRepository.findByActivoTrue());
    }

    @GetMapping("/n3")
    public ResponseEntity<List<N3>> listarN3(@RequestParam(required = false) Long claseId) {
        if (claseId != null) {
            return ResponseEntity.ok(n3Repository.findByClaseIdAndActivoTrue(claseId));
        }
        return ResponseEntity.ok(n3Repository.findByActivoTrue());
    }

    @GetMapping("/n2")
    public ResponseEntity<List<N2>> listarN2(@RequestParam(required = false) Long n3Id) {
        if (n3Id != null) {
            return ResponseEntity.ok(n2Repository.findByN3IdAndActivoTrue(n3Id));
        }
        return ResponseEntity.ok(n2Repository.findByActivoTrue());
    }

    @GetMapping("/personas")
    public ResponseEntity<List<Persona>> listarPersonas() {
        return ResponseEntity.ok(personaRepository.findByActivoTrue());
    }

    @GetMapping("/preguntas")
    public ResponseEntity<List<Pregunta>> listarPreguntas() {
        return ResponseEntity.ok(preguntaRepository.findByActivoTrueOrderByOrdenAsc());
    }

    @GetMapping("/preguntas/todas")
    public ResponseEntity<List<Pregunta>> listarTodasLasPreguntas() {
        return ResponseEntity.ok(preguntaRepository.findAllByOrderByOrdenAsc());
    }

    // Endpoints para crear catálogos
    @PostMapping("/empresas")
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaRepository.save(empresa));
    }

    @PostMapping("/clases")
    public ResponseEntity<Clase> crearClase(@RequestBody Clase clase) {
        return ResponseEntity.ok(claseRepository.save(clase));
    }

    @PostMapping("/n2")
    public ResponseEntity<N2> crearN2(@RequestBody N2 n2) {
        return ResponseEntity.ok(n2Repository.save(n2));
    }

    @PostMapping("/n3")
    public ResponseEntity<N3> crearN3(@RequestBody N3 n3) {
        return ResponseEntity.ok(n3Repository.save(n3));
    }

    @PostMapping("/personas")
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        return ResponseEntity.ok(personaRepository.save(persona));
    }

    @PostMapping("/preguntas")
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) {
        return ResponseEntity.ok(preguntaRepository.save(pregunta));
    }

    // Endpoints para actualizar catálogos
    @PutMapping("/empresas/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        Empresa existente = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        existente.setNombre(empresa.getNombre());
        existente.setDescripcion(empresa.getDescripcion());
        existente.setActivo(empresa.getActivo());
        return ResponseEntity.ok(empresaRepository.save(existente));
    }

    @PutMapping("/clases/{id}")
    public ResponseEntity<Clase> actualizarClase(@PathVariable Long id, @RequestBody Clase clase) {
        Clase existente = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));
        existente.setNombre(clase.getNombre());
        existente.setDescripcion(clase.getDescripcion());
        existente.setActivo(clase.getActivo());
        return ResponseEntity.ok(claseRepository.save(existente));
    }

    @PutMapping("/n2/{id}")
    public ResponseEntity<N2> actualizarN2(@PathVariable Long id, @RequestBody N2 n2) {
        N2 existente = n2Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("N2 no encontrado"));
        existente.setNombre(n2.getNombre());
        existente.setDescripcion(n2.getDescripcion());
        existente.setActivo(n2.getActivo());
        return ResponseEntity.ok(n2Repository.save(existente));
    }

    @PutMapping("/n3/{id}")
    public ResponseEntity<N3> actualizarN3(@PathVariable Long id, @RequestBody N3 n3) {
        N3 existente = n3Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("N3 no encontrado"));
        existente.setNombre(n3.getNombre());
        existente.setDescripcion(n3.getDescripcion());
        existente.setActivo(n3.getActivo());
        return ResponseEntity.ok(n3Repository.save(existente));
    }

    @PutMapping("/personas/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona persona) {
        Persona existente = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
        existente.setNombre(persona.getNombre());
        existente.setApellido(persona.getApellido());
        existente.setCargo(persona.getCargo());
        existente.setEmail(persona.getEmail());
        existente.setActivo(persona.getActivo());
        return ResponseEntity.ok(personaRepository.save(existente));
    }

    @PutMapping("/preguntas/{id}")
    public ResponseEntity<Pregunta> actualizarPregunta(@PathVariable Long id, @RequestBody Pregunta pregunta) {
        Pregunta existente = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        existente.setTexto(pregunta.getTexto());
        existente.setOrden(pregunta.getOrden());
        existente.setRequiereEvidencia(pregunta.getRequiereEvidencia());
        existente.setCategoria(pregunta.getCategoria());
        existente.setActivo(pregunta.getActivo());
        return ResponseEntity.ok(preguntaRepository.save(existente));
    }
}
