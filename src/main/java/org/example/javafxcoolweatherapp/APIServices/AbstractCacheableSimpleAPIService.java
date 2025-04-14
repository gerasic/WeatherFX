package org.example.javafxcoolweatherapp.APIServices;

import org.example.javafxcoolweatherapp.APIServices.Exceptions.CacheException;
import org.example.javafxcoolweatherapp.APIServices.Exceptions.ParseException;
import org.example.javafxcoolweatherapp.APIServices.Exceptions.URLException;
import org.example.javafxcoolweatherapp.Files.FileManager;
import org.example.javafxcoolweatherapp.Files.JavaNIOBasedFM;
import org.example.javafxcoolweatherapp.URL.JavaNetBasedURLM;
import org.example.javafxcoolweatherapp.URL.URLManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public abstract class AbstractCacheableSimpleAPIService<DataObject>
        implements SimpleAPIService<DataObject, String> {

    protected static final long FORECAST_UPDATE_SECONDS_PERIOD = 3600L;

    protected final String APIKey;
    protected final URLManager urlManager;
    protected FileManager fileManager;
    protected boolean cacheAccess;

    public AbstractCacheableSimpleAPIService(String APIKey, String cacheDir) {
        this.APIKey = APIKey;
        urlManager = new JavaNetBasedURLM();
        try {
            fileManager = new JavaNIOBasedFM(cacheDir);
            cacheAccess = true;
        } catch (IOException e) {
            fileManager = null;
            cacheAccess = false;
        }
    }

    abstract protected String getResponseByURLImpl(String parameter) throws Exception;

    abstract protected DataObject parseJSONResponseImpl(String data) throws Exception;

    protected String getResponseByURL(String parameter) throws URLException {
        try {
            return getResponseByURLImpl(parameter);

        } catch (Exception e) {
            throw new URLException("URL did not response.", e);
        }
    }

    public DataObject parseJSONResponse(String data) throws ParseException {
        try {
            return parseJSONResponseImpl(data);
        } catch (Exception e) {
            throw new ParseException("Exception by parsing data.", e);
        }
    }

    @Override
    public DataObject getData(String city) throws IOException {
        try {
            if (hasCachedData(city)) {
                long lastUpdate = getLastModified(city);
                var now = LocalDateTime.now();
                long currentTime = now.toEpochSecond(ZoneId
                        .systemDefault()
                        .getRules()
                        .getOffset(now)
                );

                if (currentTime - lastUpdate < FORECAST_UPDATE_SECONDS_PERIOD) {
                    return getCachedData(city);

                } else {
                    return getDataByURL(city);
                }
            }
            return getDataByURL(city);

        } catch (CacheException e) {
            return getDataByURL(city);

        } catch (IOException e) {
            throw new IOException("API Service did not get data from cache and url.", e);
        }
    }

    public DataObject getDataByURL(String parameter) throws URLException {
        try {
            String response = getResponseByURL(parameter);
            DataObject newData;
            try {
                newData = parseJSONResponse(response);
            } catch (ParseException e) {
                throw new URLException("Data was got, but it can not be parsed.", e);
            }

            if (cacheAccess) {
                try {
                    fileManager.saveDataToFile(parameter, response);
                } catch (IOException ignore) { }
            }

            return newData;

        } catch (IOException ioe) {
            throw new URLException("API Service did not get data from URL Manager", ioe);
        }
    }

    public DataObject getCachedData(String city) throws CacheException {
        if (!hasCachedData(city)) {
            throw new CacheException("Can not get data from cache.");
        }
        try {
            return parseJSONResponse(fileManager.readData(city));
        } catch (IOException e) {
            throw new CacheException("Cache data parsing error", e);
        }
    }

    public boolean hasCachedData(String city) {
        return cacheAccess && fileManager.getFilesList().contains(city);
    }

    public boolean isCacheAccess() {
        return cacheAccess;
    }

    public List<String> getRecentList() {
        return fileManager.getFilesList();
    }

    public boolean deleteRecent(String city) {
        return fileManager.deleteFile(city);
    }

    public long getLastModified(String city) {
        return fileManager.getLastModified(city);
    }
}
