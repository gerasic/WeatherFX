package org.example.javafxcoolweatherapp.APIServices.Exceptions;

import java.io.IOException;

public class URLException extends IOException {
    public URLException() {
        super();
    }

    public URLException(String message) {
        super(message);
    }

    public URLException(String message, Throwable cause) {
        super(message, cause);
    }

    public URLException(Throwable cause) {
        super(cause);
    }
}
