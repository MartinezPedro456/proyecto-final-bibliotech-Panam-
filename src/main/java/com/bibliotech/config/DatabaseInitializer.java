package com.bibliotech.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initialize() {

        String sqlFilePath = "sql/01_schema.sql";

        try {
            String sqlContent = Files.readString(Path.of(sqlFilePath));
            Connection conn = DatabaseConnection.getInstance().getConnection();

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sqlContent);
                logger.info("tablas creadas, verificadas automaticamente en supabase");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "no se pudo leer el archivo sql en la ruta: " + sqlFilePath, e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "error al ejecutar el script sql en la base de datos", e);
        }
    }
}