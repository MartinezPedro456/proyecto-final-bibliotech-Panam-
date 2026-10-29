package com.bibliotech;

import com.bibliotech.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {

        System.out.println("...............................");
        System.out.println("       BIBLIOTECH PANAMA");
        System.out.println(".................................");

        try (Connection conexion = DatabaseConnection.getConnection()) {

            System.out.println("Conexion exitosa con Supabase");
            System.out.println("Base de datos: "
                    + conexion.getCatalog());

            System.out.println("Bibliotech esta conectado.");

        } catch (SQLException e) {

            System.out.println("Error de conexion con Supabase");
            System.out.println(e.getMessage());
        }
    }
}