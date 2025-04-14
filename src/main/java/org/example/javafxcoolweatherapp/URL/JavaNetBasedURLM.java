package org.example.javafxcoolweatherapp.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JavaNetBasedURLM implements URLManager {
    public String getData(String urlAddress) throws IOException {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append('\n');
                }
            }
        } catch (Exception e) {
            throw new IOException("URL Manager can not get data from: " + urlAddress, e);
        }

        return content.toString();
    }
}
