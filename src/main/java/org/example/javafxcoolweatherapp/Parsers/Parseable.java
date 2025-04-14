package org.example.javafxcoolweatherapp.Parsers;

@FunctionalInterface
public interface Parseable<R> {
    R parse(String data) throws Exception;
}
