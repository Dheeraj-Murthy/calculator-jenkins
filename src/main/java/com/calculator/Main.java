package com.calculator;

/**
 * Simple Calculator CLI Application
 * Supports basic arithmetic operations: addition, subtraction, multiplication, division
 */
public class Main {
    
    private static final Calculator calculator = new Calculator();
    
    public static void main(String[] args) {
        if (args.length != 3) {
            printUsage();
            System.exit(1);
        }
        
        String operation = args[0];
        String firstArg = args[1];
        String secondArg = args[2];
        
        try {
            double first = Calculator.parseNumber(firstArg);
            double second = Calculator.parseNumber(secondArg);
            
            double result;
            switch (operation.toLowerCase()) {
                case "add":
                    result = calculator.add(first, second);
                    break;
                case "subtract":
                    result = calculator.subtract(first, second);
                    break;
                case "multiply":
                    result = calculator.multiply(first, second);
                    break;
                case "divide":
                    result = calculator.divide(first, second);
                    break;
                default:
                    System.err.println("Error: Invalid operation '" + operation + "'");
                    printUsage();
                    System.exit(1);
                    return;
            }
            
            // Print result without decimal point if it's a whole number
            if (result == (long) result) {
                System.out.println((long) result);
            } else {
                System.out.println(result);
            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static void printUsage() {
        System.err.println("Usage: java -jar calculator-cli.jar <operation> <first> <second>");
        System.err.println("Operations: add, subtract, multiply, divide");
        System.err.println("");
        System.err.println("Examples:");
        System.err.println("  java -jar calculator-cli.jar add 5 3           # Output: 8");
        System.err.println("  java -jar calculator-cli.jar subtract 10 4     # Output: 6");
        System.err.println("  java -jar calculator-cli.jar multiply 3 4      # Output: 12");
        System.err.println("  java -jar calculator-cli.jar divide 15 3        # Output: 5");
    }
}