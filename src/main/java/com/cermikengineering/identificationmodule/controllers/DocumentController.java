package com.cermikengineering.identificationmodule.controllers;

import com.cermikengineering.identificationmodule.models.GERResidencePermitDocument;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class DocumentController {

    @GetMapping("/document-form")
    public String showDocumentForm(Model model) {
        return "document-form";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        System.out.println("Missing parameter: " + ex.getParameterName());
        return "error";
    }

    /*@PostMapping("/submit-document")
    public String submitDocument(
            @RequestParam String permitType,
            @RequestParam String validFrom,
            @RequestParam String validUntil,
            @RequestParam String issuingAuthority,
            @RequestParam String address,
            @RequestParam String employmentStatus,
            @RequestParam String documentNumber,
            @RequestParam String issueDate,
            @RequestParam String issuedBy,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String gender,
            @RequestParam String nationality,
            @RequestParam String birthDate,
            @RequestParam String placeOfBirth) {

        // Создаем объект ResidencePermitDocument и заполняем его данными
        GERResidencePermitDocument document = new GERResidencePermitDocument();
        document.setValidUntil(LocalDate.parse(validUntil));
        document.setFirstName(firstName);
        document.setLastName(lastName);
        document.setGender(gender);
        document.setNationality(nationality);
        document.setBirthDate(LocalDate.parse(birthDate));

        // Сохраняем документ через сервис
        //documentService.saveDocument(document);

        return "redirect:/success";
    }*/

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}