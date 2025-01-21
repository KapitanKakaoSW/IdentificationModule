package com.cermikengineering.identificationmodule.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GERPassportDocument extends GERDocument {
    private String country;
    private String authority;
}
