package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KataCalculatorParserServiceImplTest {

    private KataCalculatorParserService parserService;

    @BeforeEach
    void init() {
        parserService = new KataCalculatorParserServiceImpl();
    }

    @Test
    void shouldInitializeKataCalculatorParserService() {
        assertThat(parserService).isNotNull();
    }

    @Test
    void shouldPareStringNumbersWithoutDelimiter() {
        //given
        String inputNumbers = "1";

        //when
        List<Integer> result = parserService.getNumbers(inputNumbers);

        //then
        assertThat(result).containsExactly(1);
    }

    @Test
    void shouldPareStringNumbersDelimitedWithCommas() {
        //given
        String inputNumbers = "1,2,3";

        //when
        List<Integer> result = parserService.getNumbers(inputNumbers);

        //then
        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    void shouldPareStringNumbersDelimitedWithCommasAndNewLine() {
        //given
        String inputNumbers = "1\n2,3";

        //when
        List<Integer> result = parserService.getNumbers(inputNumbers);

        //then
        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    void shouldParsStringNumbersWithDefinedDelimiter() {
        //given
        String inputNumbers = "//;\n1;2";

        //when
        List<Integer> result = parserService.getNumbers(inputNumbers);

        //then
        assertThat(result).containsExactly(1, 2);
    }

    @Test
    void shouldParsStringNumbersWithDefinedDelimiterInSquareBracket() {
        //given
        String inputNumbers = "//[***]\n1***2***3";

        //when
        List<Integer> result = parserService.getNumbers(inputNumbers);

        //then
        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    void shouldParseStringNumbersWithDefinedSeveralDelimitersWithDifferentLength() {
        //given
        String inputNumbers = "//[@][###]\n1@2###3";

        //when
        List<Integer> result = parserService.getNumbers(inputNumbers);

        //then
        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    void shouldParseNegativeStringNumbersWithDefinedSeveralDelimitersInSquareBracket() {
        //given
        String inputNumbers = "//[@][###]\n-1@-2###-3";

        //when
        List<Integer> result = parserService.getNumbers(inputNumbers);

        //then
        assertThat(result).containsExactly(-1, -2, -3);
    }
}