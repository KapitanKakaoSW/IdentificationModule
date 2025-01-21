package com.cermikengineering.identificationmodule.controllers;

import com.cermikengineering.identificationmodule.services.GERResidencePermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/upload1")
public class FileUploadController {

    private final GERResidencePermitService gerResidencePermitService;

    public FileUploadController(GERResidencePermitService gerResidencePermitService) {
        this.gerResidencePermitService = gerResidencePermitService;
    }

    @Value("${upload.path}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);

            String extractedText = gerResidencePermitService.extractTextFromImage(path.toString());

            return ResponseEntity.ok(extractedText);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload file");
        }
    }
}