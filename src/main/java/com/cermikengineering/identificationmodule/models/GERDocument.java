package com.cermikengineering.identificationmodule.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class GERDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String serialNumber;
    private String firstName;
    private String lastName;
    private String gender;
    private String nationality;
    private LocalDate birthDate;
    private LocalDate validUntil;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;

    @Transient
    private String tempFilePath;
}