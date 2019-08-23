package service;

import to.CalcExpression;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.substringBetween;
import static service.CalcDelimiter.*;
import static service.CalcIndicator.*;

public class KataCalculatorParserServiceImpl implements KataCalculatorParserService {
    private static final Integer SPLIT_LIMIT = 2;

    @Override
    public List<Integer> getNumbers(String inputExpression) {
        CalcExpression expression = getExpression(inputExpression);
        return Arrays.stream(expression.getNumbers().split(expression.getDelimiters()))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private CalcExpression getExpression(String inputExpression) {
        if(hasDefinedDelimiters(inputExpression)) {
            return getExpressionWithDefinedDelimiters(inputExpression);
        }
        return getDefaultExpression(inputExpression);
    }

    private CalcExpression getExpressionWithDefinedDelimiters(String inputExpression) {
        String[] split = inputExpression.split(NEWLINE.get(), SPLIT_LIMIT);
        String delimiters = getDefinedDelimiter(split[0]);
        return new CalcExpression(delimiters, split[1]);
    }

    private String getDefinedDelimiter(String inputExpression) {
        String delimiters = inputExpression.replace(DELIMITER_INDICATOR.get(), EMPTY);
        if(hasDefinedMultipleDelimiters(delimiters)) {
            String fetchedDelimiters = fetchDelimitersFromSeparators(delimiters);
            return of(fetchedDelimiters.split(DELIMITER_SEPARATOR_SPLITTER.get()))
                    .map(Pattern::quote)
                    .collect(Collectors.joining(JOIN_DELIMITER.get()));
        }
        return delimiters;
    }

    private boolean hasDefinedMultipleDelimiters(String delimiters) {
        return delimiters.contains(START_DELIMITER_SEPARATOR.get());
    }

    private String fetchDelimitersFromSeparators(String delimiter) {
        return delimiter.substring(delimiter.indexOf(START_DELIMITER_SEPARATOR.get()) + 1, delimiter.length()-1);
    }

    private boolean hasDefinedDelimiters(String inputExpression) {
        return inputExpression.contains(DELIMITER_INDICATOR.get());
    }

    private CalcExpression getDefaultExpression(String inputExpression) {
        return new CalcExpression(
                String.join(JOIN_DELIMITER.get(), getNumberSeparators()),
                inputExpression
        );
    }

}
