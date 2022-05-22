package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void testAdd() {
        assertEquals(11, new Calculator().add(5, 6));
    }

    @Test
    void testSubtract() {
        assertEquals(11, new Calculator().subtract(22, 11));
    }
}