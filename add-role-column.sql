-- Script para agregar la columna role a la tabla usuarios
-- Ejecutar este script en la base de datos Doles

-- Agregar columna role si no existe
ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER';

-- Actualizar el primer usuario a ROLE_ADMIN
UPDATE usuarios SET role = 'ROLE_ADMIN' WHERE id = 1;

-- Verificar los cambios
SELECT id, username, email, role, activo FROM usuarios;
