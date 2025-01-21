package com.cermikengineering.identificationmodule.services;

import com.cermikengineering.identificationmodule.models.GERResidencePermitDocument;
import com.cermikengineering.identificationmodule.repositories.GERPassportRepository;
import com.cermikengineering.identificationmodule.repositories.GERResidencePermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class GERResidencePermitService extends OCRService {

    private final GERResidencePermitRepository gerResidencePermitRepository;

    public GERResidencePermitService(GERResidencePermitRepository gerResidencePermitRepository) {
        this.gerResidencePermitRepository = gerResidencePermitRepository;
    }

    public void save(GERResidencePermitDocument data) {
        GERResidencePermitDocument entity = new GERResidencePermitDocument();
        entity.setSerialNumber(data.getSerialNumber());
        entity.setFirstName(data.getFirstName());
        entity.setLastName(data.getLastName());
        entity.setGender(data.getGender());
        entity.setNationality(data.getNationality());
        entity.setBirthDate(data.getBirthDate());
        entity.setValidUntil(data.getValidUntil());
        entity.setImage(data.getImage());

        gerResidencePermitRepository.save(entity);
    }

    private static String fixDateFormat(String line) {
        return line.replaceAll("(\\d{2})(\\d{2})(\\d{4})", "$1 $2 $3");
    }

    public GERResidencePermitDocument extractPersonData(String extractedText) {
        GERResidencePermitDocument personData = new GERResidencePermitDocument();

        String[] lines = extractedText.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.contains("TITEL")) {
                String[] parts = line.split("\\s+");
                for (String part : parts) {
                    if (part.matches("\\b[A-Z0-9]{9}\\b")) {
                        personData.setSerialNumber(part);
                        break;
                    }
                }
            }

            if (line.contains("NAMEN") || line.contains("Forenames")) {
                if (i + 1 < lines.length) {
                    personData.setLastName(lines[i + 1].trim());
                }
                if (i + 2 < lines.length) {
                    personData.setFirstName(lines[i + 2].trim());
                }
            }

            if (line.contains("GESCHLECHT") || line.contains("SEX")) {
                if (i + 1 < lines.length) {
                    String nextLine = lines[i + 2].trim();
                    if (nextLine.contains("M")) {
                        personData.setGender("Male");
                    } else if (nextLine.contains("F")) {
                        personData.setGender("Female");
                    } else {
                        System.out.println("Gender not recognized: " + nextLine);
                    }
                }
            }

            if (line.contains("NATIONALITY")) {
                if (i + 1 < lines.length) {
                    String nationalityLine = lines[i + 1].trim();
                    if (nationalityLine.length() > 4) {
                        String nationality = nationalityLine.substring(2, 5);
                        personData.setNationality(nationality);
                    } else {
                        System.out.println("Invalid nationality format: " + nationalityLine);
                    }
                }
            }

            if (line.contains("DATE OF BIRTH")) {
                if (i + 1 < lines.length) {
                    String birthLine = lines[i + 1].trim();
                    String[] birthParts = birthLine.split("\\s+");
                    if (birthParts.length >= 3) {
                        String birthDateString = birthParts[birthParts.length - 3] + " " + birthParts[birthParts.length - 2] + " " + birthParts[birthParts.length - 1];
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                            LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
                            personData.setBirthDate(birthDate);
                        } catch (DateTimeParseException e) {
                            System.err.println("Failed to parse birth date: " + birthDateString);
                        }
                    }
                }
            }

            if (line.contains("GÜLTIG BIS") || line.contains("CARD EXPIRY")) {
                if (i + 1 < lines.length) {
                    String validUntilLine = lines[i + 1].trim();
                    String[] validUntilParts = validUntilLine.split("\\s+");
                    if (validUntilParts.length >= 3) {
                        String validUntilString = validUntilParts[validUntilParts.length - 3] + " " + validUntilParts[validUntilParts.length - 2] + " " + validUntilParts[validUntilParts.length - 1];
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                            LocalDate validUntilDate = LocalDate.parse(validUntilString, formatter);
                            personData.setValidUntil(validUntilDate);
                        } catch (DateTimeParseException e) {
                            System.err.println("Failed to parse valid until date: " + validUntilString);
                        }
                    }
                }
            }
        }

        System.out.println("Extracted person data: " + personData);

        return personData;
    }
}