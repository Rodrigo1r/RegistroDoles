-- Script para actualizar el primer usuario a ROLE_ADMIN
-- Ejecutar este script después de registrar el primer usuario

-- Actualizar el primer usuario a ROLE_ADMIN
UPDATE usuarios SET role = 'ROLE_ADMIN' WHERE id = 1;

-- Verificar el cambio
SELECT id, username, email, role, activo FROM usuarios;
