package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Calculator class
 */
class CalculatorTest {
    
    private final Calculator calculator = new Calculator();
    
    @Test
    @DisplayName("Test addition operation")
    void testAdd() {
        assertEquals(5.0, calculator.add(2, 3), 0.001);
        assertEquals(0.0, calculator.add(-1, 1), 0.001);
        assertEquals(0.0, calculator.add(0, 0), 0.001);
        assertEquals(6.0, calculator.add(2.5, 3.5), 0.001);
    }
    
    @Test
    @DisplayName("Test subtraction operation")
    void testSubtract() {
        assertEquals(2.0, calculator.subtract(5, 3), 0.001);
        assertEquals(0.0, calculator.subtract(1, 1), 0.001);
        assertEquals(-5.0, calculator.subtract(0, 5), 0.001);
        assertEquals(3.0, calculator.subtract(5.5, 2.5), 0.001);
    }
    
    @Test
    @DisplayName("Test multiplication operation")
    void testMultiply() {
        assertEquals(6.0, calculator.multiply(2, 3), 0.001);
        assertEquals(0.0, calculator.multiply(0, 5), 0.001);
        assertEquals(-6.0, calculator.multiply(-2, 3), 0.001);
        assertEquals(10.0, calculator.multiply(2.5, 4), 0.001);
    }
    
    @Test
    @DisplayName("Test division operation")
    void testDivide() {
        assertEquals(3.0, calculator.divide(6, 2), 0.001);
        assertEquals(2.5, calculator.divide(5, 2), 0.001);
        assertEquals(-2.0, calculator.divide(-4, 2), 0.001);
        assertEquals(0.0, calculator.divide(0, 5), 0.001);
    }
    
    @Test
    @DisplayName("Test division by zero raises IllegalArgumentException")
    void testDivideByZero() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.divide(5, 0)
        );
        assertEquals("Cannot divide by zero", exception.getMessage());
    }
    
    @Test
    @DisplayName("Test parsing integer strings")
    void testParseInteger() {
        assertEquals(5.0, Calculator.parseNumber("5"), 0.001);
        assertEquals(-10.0, Calculator.parseNumber("-10"), 0.001);
        assertEquals(0.0, Calculator.parseNumber("0"), 0.001);
    }
    
    @Test
    @DisplayName("Test parsing float strings")
    void testParseFloat() {
        assertEquals(5.5, Calculator.parseNumber("5.5"), 0.001);
        assertEquals(-3.14, Calculator.parseNumber("-3.14"), 0.001);
        assertEquals(0.0, Calculator.parseNumber("0.0"), 0.001);
    }
    
    @Test
    @DisplayName("Test parsing invalid strings throws NumberFormatException")
    void testParseInvalidNumber() {
        assertThrows(NumberFormatException.class, () -> Calculator.parseNumber("abc"));
        assertThrows(NumberFormatException.class, () -> Calculator.parseNumber("12.34.56"));
        assertThrows(NumberFormatException.class, () -> Calculator.parseNumber(""));
    }
}