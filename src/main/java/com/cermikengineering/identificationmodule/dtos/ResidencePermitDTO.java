package com.cermikengineering.identificationmodule.dtos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ResidencePermitDTO {
    private Integer id;
    private String permitType;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private String issuingAuthority;
    private String address;
    private String employmentStatus;
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate birthDate;
    private String placeOfBirth;
}
//useless