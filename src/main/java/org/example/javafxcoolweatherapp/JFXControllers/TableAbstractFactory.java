package org.example.javafxcoolweatherapp.JFXControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

public final class TableAbstractFactory {
    public static int MAX_RECENT_CITIES_AMOUNT = 6;

    public static ArrayList<HourlyTableRow> createHourlyTable(final GridPane hourlyGridPane) {
        Font font = new Font(18.0);
        ArrayList<HourlyTableRow> table = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            Label time = new Label(Formatter.formatHour(row * 3));
            Label temp = new Label();

            time.setFont(font);
            temp.setFont(font);

            hourlyGridPane.add(time, 0, row);
            hourlyGridPane.add(temp, 1, row);
            table.add(new HourlyTableRow(time, temp));
        }

        return table;
    }

    public static ArrayList<DetailsTableRow> createDetailsTable(final GridPane detailsGridPane) {
        Font font = new Font(12.0);
        ArrayList<DetailsTableRow> table = new ArrayList<>();
        String[] paramNameList = {"Temperature", "Feels like", "Pressure", "Humidity", "Wind speed"};

        for (int row = 0; row < paramNameList.length; row++) {
            Label param = new Label(paramNameList[row] + ": ");
            Label value = new Label();

            param.setFont(font);
            value.setFont(font);

            detailsGridPane.add(param, 0, row);
            detailsGridPane.add(value, 1, row);
            table.add(new DetailsTableRow(param, value));
        }

        return table;
    }

    public static ArrayList<RecentCitiesTableRow> createRecentCitiesTable(
            final GridPane recentCitiesGridPane)
    {
        Font font = new Font(12.0);
        ArrayList<RecentCitiesTableRow> table = new ArrayList<>();

        for (int row = 0; row < MAX_RECENT_CITIES_AMOUNT; row++) {
            Label city = new Label();
            Button delete = new Button("X");
            Button load = new Button(">");

            city.setFont(font);
            delete.setFont(font);
            delete.setOpacity(0.0);
            load.setFont(font);
            load.setOpacity(0.0);

            recentCitiesGridPane.add(city, 0, row);
            recentCitiesGridPane.add(load, 1, row);
            recentCitiesGridPane.add(delete, 2, row);

            table.add(new RecentCitiesTableRow(city, delete, load));
        }

        return table;
    }
}
