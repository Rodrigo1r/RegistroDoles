package com.doles.entity;

public enum Role {
    ROLE_USER,       // Usuario normal - puede crear registros y visualizar datos
    ROLE_SUPERVISOR, // Supervisor - acceso a catálogos (Clases, N2, N3, Preguntas)
    ROLE_ADMIN       // Administrador - acceso total + gestión de usuarios
}
