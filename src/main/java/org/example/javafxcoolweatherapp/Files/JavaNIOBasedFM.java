package org.example.javafxcoolweatherapp.Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaNIOBasedFM implements FileManager {
    private static final String BASE_WORKING_DIR = ".";
    private final Path workingDir;

    public JavaNIOBasedFM(String workingDirName) throws IOException {
        if (workingDirName == null) {
            workingDir = Paths.get(BASE_WORKING_DIR);
        } else {
            workingDir = Paths.get(BASE_WORKING_DIR, workingDirName);
        }

        if (!Files.exists(workingDir)) {
            Files.createDirectory(workingDir);
        }
    }

    public void saveDataToFile(String fileName, String data) throws IOException {
        try {
            Files.writeString(getFile(fileName), data);
        } catch (IOException e) {
            throw new IOException("File manager can not save to file.", e);
        }
    }

    public String readData(String fileName) throws IOException {
        try {
            return Files.readString(getFile(fileName));
        } catch (Exception e) {
            throw new IOException("File manager can not read the file.", e);
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            return Files.deleteIfExists(getFile(fileName));
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<String> getFilesList() {
        try (Stream<Path> stream = Files.list(workingDir)) {
            return stream
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public long getLastModified(String fileName) {
        return getFile(fileName).toFile().lastModified() / 1000;
    }

    private Path getFile(String fileName) {
        return Paths.get(workingDir.getFileName().toString(), fileName);
    }
}
