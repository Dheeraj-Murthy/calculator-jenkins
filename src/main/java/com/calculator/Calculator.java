package com.calculator;

/**
 * Simple Calculator class supporting basic arithmetic operations
 */
public class Calculator {
    
    /**
     * Add two numbers
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public double add(double a, double b) {
        return a + b;
    }
    
    /**
     * Subtract two numbers
     * @param a first number
     * @param b second number
     * @return difference of a and b
     */
    public double subtract(double a, double b) {
        return a - b;
    }
    
    /**
     * Multiply two numbers
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public double multiply(double a, double b) {
        return a * b;
    }
    
    /**
     * Divide two numbers
     * @param a first number
     * @param b second number
     * @return quotient of a and b
     * @throws IllegalArgumentException if b is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
    
    /**
     * Parse string to double
     * @param value string to parse
     * @return parsed double value
     * @throws NumberFormatException if value is not a valid number
     */
    public static double parseNumber(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("'" + value + "' is not a valid number");
        }
    }
}