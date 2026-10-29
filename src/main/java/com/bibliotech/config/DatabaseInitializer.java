package com.bibliotech.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {

    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initialize() {

        try (InputStream input = DatabaseInitializer.class
                .getClassLoader()
                .getResourceAsStream("sql/01_schema.sql")) {

            if (input == null) {
                throw new IllegalStateException(
                        "No se encontro sql/01_schema.sql");
            }

            String sql = new String(
                    input.readAllBytes(),
                    StandardCharsets.UTF_8);

            try (Connection conn = DatabaseConnection.getConnection();
                    Statement stmt = conn.createStatement()) {

                stmt.execute(sql);

                logger.info(
                        "Tablas de Bibliotech creadas correctamente");
            }

        } catch (Exception e) {
            logger.log(
                    Level.SEVERE,
                    "Error al crear las tablas de Bibliotech",
                    e);
        }
    }
}