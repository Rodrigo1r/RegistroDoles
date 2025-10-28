package com.doles.controller;

import com.doles.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileController {

    private final FileStorageService fileStorageService;

    @GetMapping("/{registroId}/{fileName:.+}")
    public ResponseEntity<Resource> descargarArchivo(
            @PathVariable Long registroId,
            @PathVariable String fileName) {

        // Decodificar el nombre del archivo para manejar espacios y caracteres especiales
        String decodedFileName;
        try {
            decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            decodedFileName = fileName;
        }

        String rutaArchivo = registroId + "/" + decodedFileName;
        Resource resource = fileStorageService.cargarArchivo(rutaArchivo);

        // Determinar el tipo MIME basado en la extensión del archivo decodificado
        String contentType = "application/octet-stream";
        String fileNameLower = decodedFileName.toLowerCase();

        if (fileNameLower.endsWith(".png")) {
            contentType = "image/png";
        } else if (fileNameLower.endsWith(".jpg") || fileNameLower.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (fileNameLower.endsWith(".gif")) {
            contentType = "image/gif";
        } else if (fileNameLower.endsWith(".webp")) {
            contentType = "image/webp";
        } else if (fileNameLower.endsWith(".bmp")) {
            contentType = "image/bmp";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
