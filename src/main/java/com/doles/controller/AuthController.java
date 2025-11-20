package com.doles.controller;

import com.doles.dto.ActualizarPerfilRequest;
import com.doles.dto.AuthResponse;
import com.doles.dto.LoginRequest;
import com.doles.dto.RegisterRequest;
import com.doles.entity.Usuario;
import com.doles.repository.UsuarioRepository;
import com.doles.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PutMapping("/perfil")
    public ResponseEntity<Usuario> actualizarPerfil(
            @Valid @RequestBody ActualizarPerfilRequest request,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Usuario usuario = usuarioRepository.findByUsername(username)
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
