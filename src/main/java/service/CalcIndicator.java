package service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CalcIndicator {
    DELIMITER_INDICATOR("//"),
    START_DELIMITER_SEPARATOR("["),
    DELIMITER_SEPARATOR_SPLITTER("]\\["),
    JOIN_DELIMITER("|");

    private String indicator;

    CalcIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String get() {
        return indicator;
    }
}
