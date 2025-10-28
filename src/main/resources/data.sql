-- Datos iniciales para la aplicación Registro Doles

-- Insertar preguntas de auditoría 5S (basadas en las imágenes)
INSERT INTO preguntas (id, texto, orden, requiere_evidencia, activo, categoria) VALUES
(1, '¿Se han eliminado materiales, documentos, objetos personales, equipos innecesarios del área - Todos los elementos presentes tienen un uso?', 1, true, true, 'Clasificar'),
(2, '¿Hay una zona de descarte y proceso de clasificación programado con responsables y fechas?', 2, true, true, 'Clasificar'),
(3, '¿El área, equipos, cajones, escritorios etc se encuentran limpios. Hay buen estado de pisos y paredes?', 3, true, true, 'Limpiar'),
(4, '¿Los utensilios y suministros de limpieza se guardan en un lugar accesible, ordenado y señalizado?', 4, true, true, 'Limpiar'),
(5, '¿Las áreas, procesos, maquinarias, gabinetes, mostradores están perfectamente señalizadas, rotuladas y se identifican los límites entre ellas?', 5, true, true, 'Señalizar'),
(6, '¿Las estaciones de almacenamiento, gabinetes, mostradores y estantes tienen un inventario de su contenido. Se han establecido máximos y mínimos?', 6, true, true, 'Estandarizar'),
(7, '¿Hay evidencias evaluación DOLEs en el área y se lleva indicador de DOLEs en N3, N2, N1.1?', 7, true, true, 'Disciplina'),
(8, 'Hay plan de acción actualizado de hallazgos con responsables, fechas de cierre, % de ejecución, etc.', 8, true, true, 'Disciplina'),
(9, '¿Hay mapa DOLEs visible y actualizado?', 9, true, true, 'Disciplina'),
(10, '¿Hay estándar de limpieza, así como rutinas de inspección de puntos importantes con frecuencia y responsables?', 10, true, true, 'Disciplina')
ON CONFLICT (id) DO NOTHING;

-- Ajustar secuencia de preguntas
SELECT setval('preguntas_id_seq', (SELECT MAX(id) FROM preguntas));

-- Insertar datos de ejemplo para empresas
INSERT INTO empresas (nombre, descripcion, activo) VALUES
('NIRSA', 'Negocios Industriales Real S.A.', true),
('PROPOSORJA', 'Procesadora Posorja Proposorja S.A.', true)
ON CONFLICT (nombre) DO NOTHING;

-- Insertar datos de ejemplo para clases
INSERT INTO clases (nombre, descripcion, activo) VALUES
('AREA SOPORTE', 'Áreas de soporte operativo', true),
('PRODUCCION', 'Departamento de produccion', true)
ON CONFLICT (nombre) DO NOTHING;


-- Insertar datos de ejemplo para N3
-- N3 requiere clase_id, así que obtenemos los IDs de las clases insertadas
INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'CONTROL DE CAL/MIC/SAN', 'Control de Calidad,Microbiologia y Sanitizacion', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'CONTROL DE CAL/MIC/SAN');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'FLOTA', 'Flota Atunera y Sardinera', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'FLOTA');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'LOGISTICA EMPACADORA', 'Operaciones logísticas de camaron', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'LOGISTICA EMPACADORA');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'MANTENIMIENTO', 'Mantenimiento de planta', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'MANTENIMIENTO');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'OTROS', 'Otras operaciones', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'OTROS');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'SEGURIDAD Y VIGILANCIA', 'Operaciones de seguridad', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'SEGURIDAD Y VIGILANCIA');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'SUPPLY CHAIN', 'Gestión de cadena de suministro', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'SUPPLY CHAIN');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'TTHH', 'Talento humano', true, id
FROM clases
WHERE nombre = 'AREA SOPORTE'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'TTHH');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'EMPACADORA', 'Planta Empacadora', true, id
FROM clases
WHERE nombre = 'PRODUCCION'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'EMPACADORA');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'PLANTA DE ATÚN', 'Planta Atunera', true, id
FROM clases
WHERE nombre = 'PRODUCCION'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'PLANTA DE ATÚN');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'PLANTA DE HARINA', 'Planta de harina', true, id
FROM clases
WHERE nombre = 'PRODUCCION'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'PLANTA DE HARINA');

INSERT INTO n3 (nombre, descripcion, activo, clase_id)
SELECT 'PLANTA DE SARDINA', 'Planta de sardina', true, id
FROM clases
WHERE nombre = 'PRODUCCION'
AND NOT EXISTS (SELECT 1 FROM n3 WHERE nombre = 'PLANTA DE SARDINA');


-- Insertar datos de ejemplo para N2
-- N2 requiere n3_id, así que obtenemos los IDs de los N3 insertados
INSERT INTO n2 (nombre, descripcion, activo, n3_id)
SELECT 'BOD PROD TERM CONS', 'Bodega de productos terminados conservas', true, id
FROM n3
WHERE nombre = 'SUPPLY CHAIN'
AND NOT EXISTS (SELECT 1 FROM n2 WHERE nombre = 'BOD PROD TERM CONS');

INSERT INTO n2 (nombre, descripcion, activo, n3_id)
SELECT 'CONTROL CALIDAD EMPA', 'Control de Calidad Empacadora', true, id
FROM n3
WHERE nombre = 'CONTROL DE CAL/MIC/SAN'
AND NOT EXISTS (SELECT 1 FROM n2 WHERE nombre = 'CONTROL CALIDAD EMPA');

INSERT INTO n2 (nombre, descripcion, activo, n3_id)
SELECT 'CONTROL CALIDAD HARI', 'Control de Calidad harina', true, id
FROM n3
WHERE nombre = 'CONTROL DE CAL/MIC/SAN'
AND NOT EXISTS (SELECT 1 FROM n2 WHERE nombre = 'CONTROL CALIDAD HARI');

INSERT INTO n2 (nombre, descripcion, activo, n3_id)
SELECT 'BODEGA EMPAQUES/INSU', 'Bodega de materiales de empaque', true, id
FROM n3
WHERE nombre = 'SUPPLY CHAIN'
AND NOT EXISTS (SELECT 1 FROM n2 WHERE nombre = 'BODEGA EMPAQUES/INSU');

