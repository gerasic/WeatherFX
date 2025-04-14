package org.example.javafxcoolweatherapp.URL;

import java.io.IOException;

public interface URLManager {
    String getData(String urlAddress) throws IOException;
}
