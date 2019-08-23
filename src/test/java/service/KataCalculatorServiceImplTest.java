package service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class KataCalculatorServiceImplTest {

    private KataCalculatorService kataCalculatorService;

    @BeforeEach
    void init() {
        KataCalculatorParserService parserService = new KataCalculatorParserServiceImpl();
        kataCalculatorService = new KataCalculatorServiceImpl(parserService);
    }

    @Test
    void shouldInitializeKataCalculatorService() {
        assertThat(kataCalculatorService).isNotNull();
    }

    @Test
    void shouldReturnZeroWhenStringIsEmpty() {
        //given
        String inputs = "";
        Integer expected = 0;

        //when
        Integer result = kataCalculatorService.add(inputs);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionWhenInputIsNull() {
        //given
        String inputNumbers = null;

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> kataCalculatorService.add(inputNumbers));
        assertEquals("Input parameter cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInputsAreNegative() {
        //given
        String inputNumbers = "1,-2,-3";
        String expectedNegatives = "-2,-3";

        //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> kataCalculatorService.add(inputNumbers));
        assertEquals(format("Negatives are not allowed: %s", expectedNegatives), exception.getMessage());
    }

    @Test
    void shouldIgnoreValuesBiggerThanThousand() {
        //given
        String inputNumbers = "1,2,3,1001,1000";
        Integer expected = 1006;

        //when
        Integer result = kataCalculatorService.add(inputNumbers);

        //then
        assertThat(result).isEqualTo(expected);
    }
}