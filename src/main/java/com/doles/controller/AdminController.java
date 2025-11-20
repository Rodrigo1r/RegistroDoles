package com.doles.controller;

import com.doles.dto.ActualizarPerfilRequest;
import com.doles.entity.Role;
import com.doles.entity.Usuario;
import com.doles.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PutMapping("/usuarios/{id}/role")
    public ResponseEntity<Usuario> updateUsuarioRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String roleStr = request.get("role");
        Role role = Role.valueOf(roleStr);
        usuario.setRole(role);

        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @PutMapping("/usuarios/{id}/estado")
    public ResponseEntity<Usuario> toggleUsuarioEstado(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(!usuario.getActivo());

        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarPerfilRequest request) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que el email no esté en uso por otro usuario
        if (!usuario.getEmail().equals(request.getEmail())) {
            usuarioRepository.findByEmail(request.getEmail())
                    .ifPresent(u -> {
                        throw new RuntimeException("El email ya está en uso");
                    });
        }

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setCargo(request.getCargo());

        // Si se proporciona nueva contraseña, actualizarla
        if (request.getNuevaPassword() != null && !request.getNuevaPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(request.getNuevaPassword()));
        }

        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }
}
