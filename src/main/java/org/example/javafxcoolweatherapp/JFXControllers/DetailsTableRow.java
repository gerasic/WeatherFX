package org.example.javafxcoolweatherapp.JFXControllers;

import javafx.scene.control.Label;

public class DetailsTableRow {
    private final Label param;
    private final Label value;

    public DetailsTableRow(Label param, Label value) {
        this.param = param;
        this.value = value;
    }

    public Label getParam() {
        return param;
    }

    public Label getValue() {
        return value;
    }
}
