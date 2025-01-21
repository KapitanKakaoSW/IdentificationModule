package com.cermikengineering.identificationmodule.repositories;

import com.cermikengineering.identificationmodule.models.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}
