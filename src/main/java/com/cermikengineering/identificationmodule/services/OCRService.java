package com.cermikengineering.identificationmodule.services;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import java.io.IOException;

@Service
public class OCRService {

    public String extractTextFromImage(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Tesseract tesseract = new Tesseract();

            tesseract.setDatapath("C:/tesseract/tessdata");
            tesseract.setLanguage("deu");
            tesseract.setTessVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-/:üöäßÖÄÜ ");

            String result = tesseract.doOCR(image);
            System.out.println("OCR result: " + result);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while reading the image: " + e.getMessage());

            return "Error while reading the image.";

        } catch (TesseractException e) {
            e.printStackTrace();
            System.out.println("Error during OCR process: " + e.getMessage());

            return "Error during OCR process.";
        }
    }
}