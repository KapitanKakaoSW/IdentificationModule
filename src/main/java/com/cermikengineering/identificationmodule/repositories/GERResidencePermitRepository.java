package com.cermikengineering.identificationmodule.repositories;

import com.cermikengineering.identificationmodule.models.GERResidencePermitDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GERResidencePermitRepository extends JpaRepository<GERResidencePermitDocument, Long> {
}
