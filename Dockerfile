
# Etapa 2: Construir el backend
FROM maven:3.9-eclipse-temurin-21-alpine AS backend-build
WORKDIR /app

# Copiar código fuente del backend
COPY . .


# Construir el backend (sin tests para agilizar)
RUN mvn clean package -DskipTests

# Etapa 3: Imagen final de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app


COPY --from=backend-build /app/target/*.jar app.jar


# Exponer puerto
EXPOSE 8080


# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
