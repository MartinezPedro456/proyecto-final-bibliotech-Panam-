package com.bibliotech.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {

    private static final Properties PROPS = load();

    private AppConfig() {
    }

    private static Properties load() {

        Properties props = new Properties();

        try (InputStream input = AppConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "No se encontro application.properties");
            }

            props.load(input);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error al cargar la configuracion", e);
        }

        return props;
    }

    public static String get(String key) {

        String value = PROPS.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Falta la configuracion: " + key);
        }

        return value.trim();
    }
}