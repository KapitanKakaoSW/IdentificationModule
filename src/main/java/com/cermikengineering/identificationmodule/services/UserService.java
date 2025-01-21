package com.cermikengineering.identificationmodule.services;

import com.cermikengineering.identificationmodule.models.UserModel;
import com.cermikengineering.identificationmodule.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel saveUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void addAdmin(String username, String password) {
        UserModel admin = new UserModel();
        admin.setUserName(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ROLE_ADMIN");

        userRepository.save(admin);
    }
}
