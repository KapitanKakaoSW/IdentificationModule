package com.cermikengineering.identificationmodule.repositories;

import com.cermikengineering.identificationmodule.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByUserName(String username);
}
