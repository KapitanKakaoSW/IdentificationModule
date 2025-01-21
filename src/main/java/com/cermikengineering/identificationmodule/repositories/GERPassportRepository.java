package com.cermikengineering.identificationmodule.repositories;

import com.cermikengineering.identificationmodule.models.GERPassportDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GERPassportRepository extends JpaRepository<GERPassportDocument, Long> {
}
