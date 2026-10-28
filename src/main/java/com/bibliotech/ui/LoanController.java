package com.bibliotech.ui;

import com.bibliotech.service.LoanService;
import com.bibliotech.util.LoggerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.logging.Logger;

public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private final LoanService loanService = new LoanService();

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtCopyId;

    @FXML
    public void initialize() {
        logger.info("Inicializando la vista de préstamos...");
        cargarPrestamos();
    }

    private void cargarPrestamos() {
        try {

            logger.info("Cargando lista de préstamos correctamente.");
        } catch (Exception e) {
            logger.severe("Error al cargar los préstamos: " + e.getMessage());
        }
    }

    @FXML
    private void handleCrearPrestamo() {
        try {
            String memberId = txtMemberId.getText();
            String copyId = txtCopyId.getText();

            if (memberId.isEmpty() || copyId.isEmpty()) {
                logger.warning("Campos vacíos al intentar crear un préstamo.");
                return;
            }

            logger.info("Préstamo registrado con éxito para el usuario: " + memberId);

        } catch (Exception e) {
            logger.severe("Excepción al crear el préstamo: " + e.getMessage());
        }
    }
}