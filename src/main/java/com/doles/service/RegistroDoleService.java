package com.doles.service;

import com.doles.dto.*;
import com.doles.entity.*;
import com.doles.exception.ResourceNotFoundException;
import com.doles.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroDoleService {

    private final RegistroDoleRepository registroDoleRepository;
    private final EmpresaRepository empresaRepository;
    private final ClaseRepository claseRepository;
    private final N2Repository n2Repository;
    private final N3Repository n3Repository;
    private final PersonaRepository personaRepository;
    private final PreguntaRepository preguntaRepository;
    private final RespuestaPreguntaRepository respuestaPreguntaRepository;
    private final FileStorageService fileStorageService;
    private final AuthService authService;

    @Transactional
    public RegistroDoleResponse crearRegistro(RegistroDoleRequest request) {
        // Validar y obtener entidades
        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));

        Clase clase = claseRepository.findById(request.getClaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Clase no encontrada"));

        N2 n2 = n2Repository.findById(request.getN2Id())
                .orElseThrow(() -> new ResourceNotFoundException("N2 no encontrado"));

        N3 n3 = n3Repository.findById(request.getN3Id())
                .orElseThrow(() -> new ResourceNotFoundException("N3 no encontrado"));

        Persona personaEvaluada = personaRepository.findById(request.getPersonaEvaluadaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona evaluada no encontrada"));

        // Obtener el usuario actual de la sesión
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioActual = authService.getCurrentUser(username);
        Persona creadoPor = usuarioActual.getPersona();

        // Crear registro
        RegistroDole registro = new RegistroDole();
        registro.setEmpresa(empresa);
        registro.setClase(clase);
        registro.setN2(n2);
        registro.setN3(n3);
        registro.setPersonaEvaluada(personaEvaluada);
        registro.setCreadoPor(creadoPor);
        registro.setObservaciones(request.getObservaciones());

        // Crear respuestas
        for (RespuestaPreguntaRequest respuestaRequest : request.getRespuestas()) {
            Pregunta pregunta = preguntaRepository.findById(respuestaRequest.getPreguntaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada"));

            RespuestaPregunta respuesta = new RespuestaPregunta();
            respuesta.setRegistroDole(registro);
            respuesta.setPregunta(pregunta);
            respuesta.setRespuesta(respuestaRequest.getRespuesta());

            registro.getRespuestas().add(respuesta);
        }

        // Guardar registro
        registro = registroDoleRepository.save(registro);

        return mapToResponse(registro);
    }

    @Transactional
    public void agregarEvidencia(Long registroId, Long preguntaId, MultipartFile file) {
        RegistroDole registro = registroDoleRepository.findById(registroId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));

        RespuestaPregunta respuesta = registro.getRespuestas().stream()
                .filter(r -> r.getPregunta().getId().equals(preguntaId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));

        String rutaArchivo = fileStorageService.guardarArchivo(file, registroId);

        Evidencia evidencia = new Evidencia();
        evidencia.setRespuestaPregunta(respuesta);
        evidencia.setNombreArchivo(file.getOriginalFilename());
        evidencia.setRutaArchivo(rutaArchivo);
        evidencia.setTipoMime(file.getContentType());
        evidencia.setTamanoBytes(file.getSize());

        respuesta.getEvidencias().add(evidencia);
        registroDoleRepository.save(registro);
    }

    @Transactional(readOnly = true)
    public List<RegistroDoleListResponse> listarRegistros(LocalDate fechaInicio, LocalDate fechaFin, Long n2Id, Long n3Id) {
        Specification<RegistroDole> spec = Specification.where(null);

        if (fechaInicio != null && fechaFin != null) {
            LocalDateTime inicio = fechaInicio.atStartOfDay();
            LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("fechaRegistro"), inicio, fin));
        }

        if (n2Id != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("n2").get("id"), n2Id));
        }

        if (n3Id != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("n3").get("id"), n3Id));
        }

        return registroDoleRepository.findAll(spec).stream()
                .map(this::mapToListResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RegistroDoleResponse obtenerRegistro(Long id) {
        RegistroDole registro = registroDoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));
        return mapToResponse(registro);
    }

    private RegistroDoleResponse mapToResponse(RegistroDole registro) {
        List<RespuestaPreguntaResponse> respuestas = registro.getRespuestas().stream()
                .map(r -> RespuestaPreguntaResponse.builder()
                        .id(r.getId())
                        .preguntaTexto(r.getPregunta().getTexto())
                        .respuesta(r.getRespuesta())
                        .evidencias(r.getEvidencias().stream()
                                .map(e -> EvidenciaResponse.builder()
                                        .id(e.getId())
                                        .nombreArchivo(e.getNombreArchivo())
                                        .rutaArchivo(e.getRutaArchivo())
                                        .tipoMime(e.getTipoMime())
                                        .tamanoBytes(e.getTamanoBytes())
                                        .fechaSubida(e.getFechaSubida())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return RegistroDoleResponse.builder()
                .id(registro.getId())
                .empresa(registro.getEmpresa().getNombre())
                .clase(registro.getClase().getNombre())
                .n2(registro.getN2().getNombre())
                .n3(registro.getN3().getNombre())
                .personaEvaluada(registro.getPersonaEvaluada().getNombre() + " " +
                        (registro.getPersonaEvaluada().getApellido() != null ? registro.getPersonaEvaluada().getApellido() : ""))
                .creadoPor(registro.getCreadoPor().getNombre() + " " +
                        (registro.getCreadoPor().getApellido() != null ? registro.getCreadoPor().getApellido() : ""))
                .fechaRegistro(registro.getFechaRegistro())
                .puntaje(registro.getPuntaje())
                .observaciones(registro.getObservaciones())
                .respuestas(respuestas)
                .build();
    }

    private RegistroDoleListResponse mapToListResponse(RegistroDole registro) {
        return RegistroDoleListResponse.builder()
                .id(registro.getId())
                .empresa(registro.getEmpresa().getNombre())
                .clase(registro.getClase().getNombre())
                .n2(registro.getN2().getNombre())
                .n3(registro.getN3().getNombre())
                .personaEvaluada(registro.getPersonaEvaluada().getNombre() + " " +
                        (registro.getPersonaEvaluada().getApellido() != null ? registro.getPersonaEvaluada().getApellido() : ""))
                .fechaRegistro(registro.getFechaRegistro())
                .puntaje(registro.getPuntaje())
                .build();
    }

    @Transactional(readOnly = true)
    public List<EstadisticaPorAreaResponse> obtenerEstadisticasPorArea() {
        List<RegistroDole> todosLosRegistros = registroDoleRepository.findAll();

        // Agrupar por clase (área)
        Map<String, List<RegistroDole>> registrosPorArea = todosLosRegistros.stream()
                .collect(Collectors.groupingBy(r -> r.getClase().getNombre()));

        // Calcular estadísticas por área
        return registrosPorArea.entrySet().stream()
                .map(entry -> {
                    String nombreArea = entry.getKey();
                    List<RegistroDole> registros = entry.getValue();

                    long cantidadRegistros = registros.size();
                    double puntajePromedio = registros.stream()
                            .mapToInt(RegistroDole::getPuntaje)
                            .average()
                            .orElse(0.0);

                    return EstadisticaPorAreaResponse.builder()
                            .nombreArea(nombreArea)
                            .cantidadRegistros(cantidadRegistros)
                            .puntajePromedio(Math.round(puntajePromedio * 100.0) / 100.0)
                            .porcentajeCumplimiento(Math.round(puntajePromedio * 100.0) / 100.0)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstadisticaPorN3Response> obtenerEstadisticasPorN3() {
        List<RegistroDole> todosLosRegistros = registroDoleRepository.findAll();

        // Agrupar por N3
        Map<String, List<RegistroDole>> registrosPorN3 = todosLosRegistros.stream()
                .collect(Collectors.groupingBy(r -> r.getN3().getNombre()));

        // Calcular estadísticas por N3
        return registrosPorN3.entrySet().stream()
                .map(entry -> {
                    String nombreN3 = entry.getKey();
                    List<RegistroDole> registros = entry.getValue();

                    long cantidadRegistros = registros.size();
                    double puntajePromedio = registros.stream()
                            .mapToInt(RegistroDole::getPuntaje)
                            .average()
                            .orElse(0.0);

                    // Obtener el nombre de la clase del primer registro (todos tienen la misma)
                    String nombreClase = registros.isEmpty() ? "" : registros.get(0).getN3().getClase().getNombre();

                    return EstadisticaPorN3Response.builder()
                            .nombreN3(nombreN3)
                            .nombreClase(nombreClase)
                            .cantidadRegistros(cantidadRegistros)
                            .puntajePromedio(Math.round(puntajePromedio * 100.0) / 100.0)
                            .porcentajeCumplimiento(Math.round(puntajePromedio * 100.0) / 100.0)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
