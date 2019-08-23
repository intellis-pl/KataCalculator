package service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static service.CalcDelimiter.COMMA;

public class KataCalculatorServiceImpl implements KataCalculatorService {

    private static final Integer LIMIT = 1000;
    private static final Integer ZERO = 0;
    private KataCalculatorParserService parserService;

    KataCalculatorServiceImpl(KataCalculatorParserService parserService) {
        this.parserService = parserService;
    }

    @Override
    public Integer add(String inputNumbers) {
        if(inputNumbers == null) {
            throw new IllegalArgumentException("Input parameter cannot be null");
        }
        if(isEmpty(inputNumbers)) {
            return 0;
        }
        List<Integer> numbers = parserService.getNumbers(inputNumbers);
        validWithNegatives(numbers);
        return numbers.stream().filter(isNumberBelowOrEqualThousand()).reduce(0, Integer::sum);
    }

    private void validWithNegatives(List<Integer> numbers) {
        String negatives = numbers.stream().filter(isNumberBelowZero()).map(String::valueOf).collect(Collectors.joining(COMMA.get()));
        if(isNotEmpty(negatives)) {
            throw new IllegalArgumentException(format("Negatives are not allowed: %s", negatives));
        }
    }

    private Predicate<Integer> isNumberBelowOrEqualThousand() {
        return number -> number <= LIMIT;
    }

    private Predicate<Integer> isNumberBelowZero() {
        return number -> number < ZERO;
    }
}
