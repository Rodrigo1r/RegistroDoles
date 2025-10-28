# Registro Doles - Backend

Backend para el sistema de registro de auditorías 5S Doles.

## Tecnologías

- Java 21
- Spring Boot 3.4
- PostgreSQL
- Maven
- JPA/Hibernate

## Requisitos Previos

- Java 21 instalado
- PostgreSQL instalado y corriendo
- Maven instalado
- Base de datos PostgreSQL llamada `doles` creada

## Configuración

1. Asegúrate de tener PostgreSQL corriendo en localhost:5432
2. Crea la base de datos:
```sql
CREATE DATABASE doles;
```

3. Actualiza las credenciales en `src/main/resources/application.properties` si es necesario:
```properties
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Ejecutar la aplicación

```bash
cd backend
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints Principales

### Catálogos
- `GET /api/catalogos/empresas` - Listar empresas
- `GET /api/catalogos/clases` - Listar clases
- `GET /api/catalogos/n2` - Listar N2
- `GET /api/catalogos/n3` - Listar N3
- `GET /api/catalogos/personas` - Listar personas
- `GET /api/catalogos/preguntas` - Listar preguntas

### Registros Dole
- `POST /api/registros` - Crear nuevo registro
- `GET /api/registros` - Listar registros (con filtros opcionales: fechaInicio, fechaFin, n2Id, n3Id)
- `GET /api/registros/{id}` - Obtener detalle de un registro
- `POST /api/registros/{registroId}/preguntas/{preguntaId}/evidencias` - Subir evidencia

### Archivos
- `GET /api/files/{registroId}/{fileName}` - Descargar archivo de evidencia

## Estructura de la Base de Datos

El proyecto crea automáticamente las siguientes tablas:

- `empresas` - Catálogo de empresas
- `clases` - Catálogo de clases
- `n2` - Catálogo de niveles N2
- `n3` - Catálogo de niveles N3
- `personas` - Catálogo de personas
- `preguntas` - Catálogo de preguntas de auditoría
- `registros_dole` - Registros principales de auditoría
- `respuestas_pregunta` - Respuestas a cada pregunta
- `evidencias` - Archivos de evidencia

## Datos Iniciales

Para cargar datos iniciales (empresas, clases, preguntas, etc.), puedes usar los endpoints POST de catálogos o crear un archivo `data.sql` en `src/main/resources/`.
