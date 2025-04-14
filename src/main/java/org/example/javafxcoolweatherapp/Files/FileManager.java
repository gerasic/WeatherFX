package org.example.javafxcoolweatherapp.Files;

import java.io.IOException;
import java.util.List;

public interface FileManager {
    void saveDataToFile(String fileName, String data) throws IOException;

    String readData(String fileName) throws IOException;

    boolean deleteFile(String fileName);

    List<String> getFilesList();

    long getLastModified(String fileName);
}
