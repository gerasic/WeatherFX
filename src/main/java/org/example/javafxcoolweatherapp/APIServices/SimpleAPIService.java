package org.example.javafxcoolweatherapp.APIServices;

import java.io.IOException;

public interface SimpleAPIService<DataObject, Parameter> {
    DataObject getData(Parameter parameter) throws IOException;
}
