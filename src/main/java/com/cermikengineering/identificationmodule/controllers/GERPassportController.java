package com.cermikengineering.identificationmodule.controllers;

import com.cermikengineering.identificationmodule.models.GERPassportDocument;
import com.cermikengineering.identificationmodule.services.GERPassportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GERPassportController {

    private final GERPassportService gerPassportService;

    public GERPassportController(GERPassportService gerPassportService) {
        this.gerPassportService = gerPassportService;
    }

    @PostMapping("/saveGERPassportDocument")
    public String submitForm(@RequestBody GERPassportDocument data) {
        gerPassportService.save(data);
        return "Daten erfolgreich gesendet!";
    }

    public ResponseEntity<GERPassportDocument> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("cropX") float cropX,
            @RequestParam("cropY") float cropY,
            @RequestParam("cropWidth") float cropWidth,
            @RequestParam("cropHeight") float cropHeight) {
        try {
            if (file.isEmpty()) {
                System.out.println("Uploaded file is empty.");
                return ResponseEntity.badRequest().body(null);
            }

            Path tempDir = Files.createTempDirectory("");
            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());
            System.out.println("Temporary file created: " + tempFile.toString());

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

            Path croppedFile = tempDir.resolve("cropped_" + file.getOriginalFilename());
            ImageIO.write(croppedImage, "png", croppedFile.toFile());
            System.out.println("Cropped image saved: " + croppedFile.toString());

            String extractedText = gerPassportService.extractTextFromImage(croppedFile.toString());
            System.out.println("Extracted text: " + extractedText);
            GERPassportDocument personData = gerPassportService.extractPersonData(extractedText);

            return ResponseEntity.ok(personData);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Возвращаем 500 Internal Server Error
        }
    }
}
