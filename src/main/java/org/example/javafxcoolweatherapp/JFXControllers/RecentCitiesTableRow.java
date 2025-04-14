package org.example.javafxcoolweatherapp.JFXControllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RecentCitiesTableRow {
    private final Label name;
    private final Button delete, load;

    public RecentCitiesTableRow(Label name, Button delete, Button load) {
        this.name = name;
        this.delete = delete;
        this.load = load;
    }

    public Label getName() {
        return name;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getLoad() {
        return load;
    }
}
