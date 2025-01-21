package com.cermikengineering.identificationmodule.utils;

import com.cermikengineering.identificationmodule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AdminSetupCommand implements CommandLineRunner {

    private final UserService userService;

    public AdminSetupCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя пользователя для администратора: ");
        String username = scanner.nextLine();

        System.out.print("Введите пароль для администратора: ");
        String password = scanner.nextLine();

        userService.addAdmin(username, password); // Метод для добавления администратора с введенными данными

        System.out.println("Администратор добавлен!");
    }
}

