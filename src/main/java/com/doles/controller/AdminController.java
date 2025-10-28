package com.doles.controller;

import com.doles.entity.Role;
import com.doles.entity.Usuario;
import com.doles.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
}
