/*
package com.cermikengineering.identificationmodule.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:C:\\Users\\User\\IdeaProjects\\IdentificationModule\\src\\main\\resources\\database\\database.db";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Соединение с базой данных успешно установлено.");
        } catch (SQLException e) {
            System.out.println("Ошибка соединения: " + e.getMessage());
        }
        return connection;
    }
}*/
