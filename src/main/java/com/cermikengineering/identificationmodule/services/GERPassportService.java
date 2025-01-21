package com.cermikengineering.identificationmodule.services;

import com.cermikengineering.identificationmodule.models.GERPassportDocument;
import com.cermikengineering.identificationmodule.repositories.GERPassportRepository;
import org.springframework.stereotype.Service;

@Service
public class GERPassportService extends OCRService {

    private final GERPassportRepository gerPassportRepository;

    public GERPassportService(GERPassportRepository gerPassportRepository) {
        this.gerPassportRepository = gerPassportRepository;
    }

    public void save(GERPassportDocument data) {
        GERPassportDocument entity = new GERPassportDocument();
        entity.setSerialNumber(data.getSerialNumber());
        entity.setFirstName(data.getFirstName());
        entity.setLastName(data.getLastName());
        entity.setGender(data.getGender());
        entity.setNationality(data.getNationality());
        entity.setBirthDate(data.getBirthDate());
        entity.setValidUntil(data.getValidUntil());
        entity.setCountry(data.getCountry());
        entity.setAuthority(data.getAuthority());

        gerPassportRepository.save(entity);
    }

    //TODO

    public GERPassportDocument extractPersonData(String extractedText) {
        GERPassportDocument personData = new GERPassportDocument();

        String[] lines = extractedText.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[1].trim();

            if (line.contains("SCHLAND") ||
                    line.contains("REISEPASS") ||
                    line.contains("SEPASS") ||
                    line.contains("REISE")) {
                personData.setCountry("GERMANY");
            }
        }
        return null;
    }
}