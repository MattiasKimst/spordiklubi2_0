package com.example.spordiklubi2_0;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Kontroller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}