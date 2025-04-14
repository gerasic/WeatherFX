package org.example.javafxcoolweatherapp.JFXControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.example.javafxcoolweatherapp.APIServices.Exceptions.CacheException;
import org.example.javafxcoolweatherapp.APIServices.OpenWeather.GeoAPIService;
import org.example.javafxcoolweatherapp.APIServices.OpenWeather.ThreeHourForecastAPIService;
import org.example.javafxcoolweatherapp.DataObjects.ThreeHourForecast;
import org.example.javafxcoolweatherapp.DataObjects.TimeStamp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.javafxcoolweatherapp.JFXControllers.Formatter.formatDateTime;
import static org.example.javafxcoolweatherapp.JFXControllers.Formatter.getAvgTempForThreeHour;

public final class JavaFXController {
    final private static String APIKey = "5444a50a846c6b05227cf5d443fa903c";
    final private ThreeHourForecastAPIService serviceAPI
            = new ThreeHourForecastAPIService(APIKey, new GeoAPIService(APIKey));

    public void showData(String city) {
        try {
            showData(serviceAPI.getData(city), city);
            updateDate.setText(formatDateTime(LocalDateTime.now()));
            errorLabel.setText("");

        } catch (IOException e) {
            if (serviceAPI.hasCachedData(city)) {
                try {
                    showData(serviceAPI.getCachedData(city), city);
                    updateDate.setText(formatDateTime(serviceAPI.getLastModified(city)));
                    errorLabel.setText("Can't get actual data.");
                    return;
                } catch (CacheException ce) {
                    errorLabel.setText(e.getMessage());
                }
            }
            errorLabel.setText("Can't get any data.");
        }
    }

    private void showData(final ThreeHourForecast threeHourForecast, String cityName) {
        showCurrentForecast(threeHourForecast.getTimeStamp(0), cityName);

        updateHourly(threeHourForecast);
        updateDetails(threeHourForecast);
        updateRecentCities();

        showDownPane(threeHourForecast);
    }

    /*
       LEFT SECTION
     */

    @FXML
    private TextField cityNameField;
    @FXML
    private Label errorLabel;
    @FXML
    private GridPane recentCitiesTable;
    private ArrayList<RecentCitiesTableRow> recentCitiesNodeTable;

    @FXML
    public void onCitySearchButtonClick() {
        String city = Formatter.formatCaption(cityNameField.getText());
        showData(city);
        cityNameField.clear();
    }

    private void updateRecentCities() {
        if (recentCitiesNodeTable == null) {
            recentCitiesNodeTable = TableAbstractFactory.createRecentCitiesTable(recentCitiesTable);
        }

        List<String> citiesSortedList = serviceAPI.getRecentList();
        citiesSortedList.sort(Comparator
                .comparingLong(serviceAPI::getLastModified)
                .reversed()
        );

        for (int row = 0; row < TableAbstractFactory.MAX_RECENT_CITIES_AMOUNT; row++) {
            RecentCitiesTableRow tableRow = recentCitiesNodeTable.get(row);
            if (row >= citiesSortedList.size()) {
                tableRow.getName().setText("");
                tableRow.getDelete().setOpacity(0.0);
                tableRow.getLoad().setOpacity(0.0);
            } else {
                String city = citiesSortedList.get(row);
                tableRow.getName().setText(city);


                tableRow.getDelete().setOpacity(1.0);
                tableRow.getDelete().setOnAction(actionEvent -> {
                        serviceAPI.deleteRecent(city);
                        updateRecentCities();
                    }
                );

                tableRow.getLoad().setOpacity(1.0);
                tableRow.getLoad().setOnAction(actionEvent -> {
                    showData(city);
                });
            }
        }
    }

    /*
        CENTER SECTION
     */

    @FXML
    private Label cityName;
    @FXML
    private Label temp;
    @FXML
    private Label feelsLike;
    @FXML
    private Label weatherDescription;
    @FXML
    private Label updateDate;

    private void setCurrentCityName(String name) {
        cityName.setText(Formatter.formatCaption(name));
    }

    private void showCurrentForecast(final TimeStamp currentTimeStamp, String cityName) {
        setCurrentCityName(cityName);

        temp.setText(Formatter.formatTemp(
                currentTimeStamp.getTempCelsius()));

        feelsLike.setText("Feels like: " + Formatter.formatTemp(
                currentTimeStamp.getFeelsLikeCelsius()));

        weatherDescription.setText(
                currentTimeStamp.getWeatherDescription());
    }


    /*
        RIGHT SECTION
     */

    @FXML
    private GridPane hourlyDetails;
    private ArrayList<HourlyTableRow> hourlyNodeTable;

    @FXML
    private GridPane detailsTable;
    private ArrayList<DetailsTableRow> detailsNodeTable;

    private void updateHourly(final ThreeHourForecast threeHourForecast) {
        if (hourlyNodeTable == null) {
            hourlyNodeTable = TableAbstractFactory.createHourlyTable(hourlyDetails);
        }
        for (int row = 0; row < 8; row++) {
            hourlyNodeTable.get(row).getTemp().setText(Formatter.formatTemp(
                    threeHourForecast.getTimeStamp(row * 3, 0).getTempCelsius()));
        }
    }

    private void updateDetails(final ThreeHourForecast threeHourForecast) {
        if (detailsNodeTable == null) {
            detailsNodeTable = TableAbstractFactory.createDetailsTable(detailsTable);
        }
        TimeStamp stamp = threeHourForecast.getTimeStamp(12);
        for (int row = 0; row < detailsNodeTable.size(); row++) {
            String value = getDetailsRowValue(row, stamp);
            detailsNodeTable.get(row).getValue().setText(value);
        }
    }

    private String getDetailsRowValue(int row, TimeStamp stamp) {
        String value = "";
        if (row == 0) {
            value = Formatter.formatTemp(stamp.getTempCelsius());
        } else if (row == 1) {
            value = Formatter.formatTemp(stamp.getFeelsLikeCelsius());
        } else if (row == 2) {
            value = Formatter.formatPressure(stamp.getPressureHPa());
        } else if (row == 3) {
            value = Formatter.formatHumidity(stamp.getHumidityPercents());
        } else if (row == 4) {
            value = Formatter.formatWindSpeed(stamp.getWindSpeedMetersSec());
        }

        return value;
    }

    /*
        DOWN PANE SECTION
     */

    @FXML
    private Label day1Date, day2Date, day3Date, day4Date, day5Date;
    @FXML
    private Label day1Temp, day2Temp, day3Temp, day4Temp, day5Temp;
    @FXML
    private Label day1Desc, day2Desc, day3Desc, day4Desc, day5Desc;

    /**
     *
     * @param field - Temp | Desc | Date
     * @param day - [1, 5]
     */
    private Label getDownPaneLabel(String field, int day)
            throws NoSuchFieldException, IllegalAccessException {
        return (Label) this.getClass().getDeclaredField("day" + day + field).get(this);
    }

    private void showDownPane(final ThreeHourForecast threeHourForecast) {
        for (int i = 1; i <= 5; i++) {
            try {
                getDownPaneLabel("Date", i).setText(
                        Formatter.getDateOfThreeHour(threeHourForecast.getTimeStamp(0, i - 1)));
                getDownPaneLabel("Temp", i).setText(
                        Formatter.formatTemp(getAvgTempForThreeHour(i - 1, threeHourForecast)));
                getDownPaneLabel("Desc", i).setText(
                        threeHourForecast.getTimeStamp(0, i - 1).getWeatherDescription()
                );
            } catch (NoSuchFieldException | IllegalAccessException ignored) { }
        }
    }
}