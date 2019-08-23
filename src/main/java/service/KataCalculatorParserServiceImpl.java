package service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class KataCalculatorParserServiceImpl implements KataCalculatorParserService {
    private static final Integer EXPRESSION_INDEX = 1;
    private static final Integer SPLIT_LIMIT = 2;

    @Override
    public List<Integer> getNumbers(String inputExpression) {
        String delimiters = findDelimiters(inputExpression);
        String validExpression = getValidExpression(inputExpression);
        return Arrays.stream(validExpression.split(delimiters))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private String findDelimiters(String inputExpression) {
        if(hasDefinedDelimiters(inputExpression)) {
            return getDefinedDelimiter(inputExpression);
        }
        return String.join(CalcDelimiter.JOIN_DELIMITER.get(), CalcDelimiter.getNumberSeparators());
    }

    private String getValidExpression(String inputExpression) {
        if(hasDefinedDelimiters(inputExpression)) {
            return inputExpression.split(CalcDelimiter.NEWLINE.get(), SPLIT_LIMIT)[EXPRESSION_INDEX];
        }
        return inputExpression;
    }

    private boolean hasDefinedDelimiters(String inputExpression) {
        return inputExpression.contains(CalcDelimiter.DELIMITER_INDICATOR.get());
    }

    private String getDefinedDelimiter(String inputExpression) {
        String delimiters = substringBetween(inputExpression, CalcDelimiter.DELIMITER_INDICATOR.get(), CalcDelimiter.NEWLINE.get());
        if(delimiters.contains(CalcDelimiter.START_DELIMITER_SEPARATOR.get())) {
            String fetchedDelimiters = fetchDelimitersFromSeparators(delimiters);
            return of(fetchedDelimiters.split(CalcDelimiter.DELIMITER_SEPARATOR_SPLITTER.get()))
                    .map(Pattern::quote)
                    .collect(Collectors.joining(CalcDelimiter.JOIN_DELIMITER.get()));
        }
        return delimiters;
    }

    private String fetchDelimitersFromSeparators(String delimiter) {
        return delimiter.substring(delimiter.indexOf(CalcDelimiter.START_DELIMITER_SEPARATOR.get()) + 1, delimiter.length()-1);
    }

}
