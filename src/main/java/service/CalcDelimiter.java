package service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CalcDelimiter {
    COMMA(","),
    NEWLINE("\n");

    private String delimiter;

    CalcDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public static List<String> getNumberSeparators() {
        return Stream.of(values()).map(CalcDelimiter::get).collect(Collectors.toList());
    }

    public String get() {
        return delimiter;
    }
}
