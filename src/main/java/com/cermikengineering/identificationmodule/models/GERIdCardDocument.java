package com.cermikengineering.identificationmodule.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GERIdCardDocument extends GERDocument {
    private String personalIdNumber;

}