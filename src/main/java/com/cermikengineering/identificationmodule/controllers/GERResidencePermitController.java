package com.cermikengineering.identificationmodule.controllers;

import com.cermikengineering.identificationmodule.models.GERResidencePermitDocument;
import com.cermikengineering.identificationmodule.models.ImageEntity;
import com.cermikengineering.identificationmodule.repositories.ImageRepository;
import com.cermikengineering.identificationmodule.services.GERResidencePermitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import javax.imageio.ImageIO;

@RestController
public class GERResidencePermitController {

    private final GERResidencePermitService gerResidencePermitService;

    private final ImageRepository imageRepository;

    public GERResidencePermitController(GERResidencePermitService gerResidencePermitService, ImageRepository imageRepository) {
        this.gerResidencePermitService = gerResidencePermitService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/saveGERResidencePermitDocument")
    public ResponseEntity<String> saveGERResidencePermitDocument(@RequestBody GERResidencePermitDocument data) {

        try {
            Path tempFilePath = Paths.get(data.getTempFilePath());
            if (!Files.exists(tempFilePath)) {
                return ResponseEntity.badRequest().body("Temporäre Datei nicht gefunden.");
            }

            ImageEntity image = new ImageEntity();
            image.setImageData(Files.readAllBytes(tempFilePath));
            image.setUploadTime(LocalDateTime.now());
            ImageEntity savedImage = imageRepository.save(image);

            data.setImage(savedImage);
            gerResidencePermitService.save(data);

            Files.delete(tempFilePath);

            return ResponseEntity.ok("Daten erfolgreich gesendet!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Speichern der Daten.");
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Received file: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());

            /*Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "temp_images");
            if (!Files.exists(tempDir)) {
                Files.createDirectories(tempDir);
                System.out.println("Temporary directory created: " + tempDir.toString());
            }*/

            Path tempDir = Paths.get("C:/Users/User/IdeaProjects/IdentificationModule1/temp_images");
            Files.createDirectories(tempDir);

            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path tempFile = tempDir.resolve(uniqueFileName);

            Files.write(tempFile, file.getBytes());
            System.out.println("Temporary file saved: " + tempFile.toString());

            return ResponseEntity.ok(Map.of("tempFilePath", tempFile.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Hochladen des Bildes.");
        }
    }

    @PostMapping("/uploadGERResidencePermitDocument")
    public ResponseEntity<GERResidencePermitDocument> handleFileUpload(
            @RequestBody Map<String, Object> payload) {
        try {
            String tempFilePath = (String) payload.get("tempFilePath");
            float cropX = ((Number) payload.get("cropX")).floatValue();
            float cropY = ((Number) payload.get("cropY")).floatValue();
            float cropWidth = ((Number) payload.get("cropWidth")).floatValue();
            float cropHeight = ((Number) payload.get("cropHeight")).floatValue();

            Path tempFile = Paths.get(tempFilePath);
            if (!Files.exists(tempFile)) {
                System.out.println("Temporary file not found: " + tempFilePath);
            } else {
                System.out.println("File exists: " + tempFilePath);
            }

            BufferedImage image = ImageIO.read(tempFile.toFile());
            if (image == null) {
                System.out.println("Failed to read image from file.");
                return ResponseEntity.badRequest().body(null);
            }

            BufferedImage croppedImage = image.getSubimage(
                    Math.round(cropX),
                    Math.round(cropY),
                    Math.round(cropWidth),
                    Math.round(cropHeight)
            );

            Path croppedFile = tempFile.getParent().resolve("cropped_" + tempFile.getFileName());
            ImageIO.write(croppedImage, "png", croppedFile.toFile());
            System.out.println("Cropped image saved: " + croppedFile.toString());

            System.out.println("Payload received: " + payload);
            String tempFilePathE = (String) payload.get("tempFilePath");
            System.out.println("tempFilePath: " + tempFilePathE);

            if (tempFilePathE == null || tempFilePathE.isEmpty()) {
                System.out.println("tempFilePath отсутствует в запросе.");
                return ResponseEntity.badRequest().body(null);
            }

            String extractedText = gerResidencePermitService.extractTextFromImage(croppedFile.toString());
            System.out.println("Extracted text: " + extractedText);

            Path tempFileE = Paths.get(tempFilePath);
            if (!Files.exists(tempFileE)) {
                System.out.println("Temporary file not found: " + tempFilePath);
                return ResponseEntity.badRequest().body(null);
            }

            GERResidencePermitDocument personData = gerResidencePermitService.extractPersonData(extractedText);

            return ResponseEntity.ok(personData);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}