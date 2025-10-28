package com.doles.service;

import com.doles.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento", ex);
        }
    }

    public String guardarArchivo(MultipartFile file, Long registroId) {
        try {
            // Limpiar el nombre original del archivo: remover espacios y caracteres especiales
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                originalFileName = "archivo.bin";
            }

            // Reemplazar espacios por guiones bajos y remover caracteres especiales
            String cleanFileName = originalFileName
                    .replaceAll("\\s+", "_")  // Espacios a guiones bajos
                    .replaceAll("[^a-zA-Z0-9._-]", "");  // Remover caracteres especiales

            String fileName = UUID.randomUUID() + "_" + cleanFileName;
            Path registroDir = this.fileStorageLocation.resolve(String.valueOf(registroId));
            Files.createDirectories(registroDir);

            Path targetLocation = registroDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return registroId + "/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo guardar el archivo", ex);
        }
    }

    public Resource cargarArchivo(String rutaArchivo) {
        try {
            Path filePath = this.fileStorageLocation.resolve(rutaArchivo).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("Archivo no encontrado: " + rutaArchivo);
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("Archivo no encontrado: " + rutaArchivo);
        }
    }
}
