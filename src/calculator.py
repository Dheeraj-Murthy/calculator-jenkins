#!/usr/bin/env python3
"""
Simple Calculator CLI Application
Supports basic arithmetic operations: addition, subtraction, multiplication, division
"""

import argparse
import sys


def add(a, b):
    """Add two numbers"""
    return a + b


def subtract(a, b):
    """Subtract two numbers"""
    return a - b


def multiply(a, b):
    """Multiply two numbers"""
    return a * b


def divide(a, b):
    """Divide two numbers"""
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b


def parse_number(value):
    """Parse string to float or int"""
    try:
        if '.' in value:
            return float(value)
        return int(value)
    except ValueError:
        raise argparse.ArgumentTypeError(f"'{value}' is not a valid number")


def main():
    """Main function to handle CLI arguments and perform calculations"""
    parser = argparse.ArgumentParser(
        description="Simple Calculator CLI Application",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s add 5 3           # Output: 8
  %(prog)s subtract 10 4     # Output: 6
  %(prog)s multiply 3 4      # Output: 12
  %(prog)s divide 15 3        # Output: 5.0
        """
    )
    
    parser.add_argument(
        'operation',
        choices=['add', 'subtract', 'multiply', 'divide'],
        help='Arithmetic operation to perform'
    )
    
    parser.add_argument(
        'first',
        type=parse_number,
        help='First number'
    )
    
    parser.add_argument(
        'second',
        type=parse_number,
        help='Second number'
    )
    
    args = parser.parse_args()
    
    try:
        result = None
        if args.operation == 'add':
            result = add(args.first, args.second)
        elif args.operation == 'subtract':
            result = subtract(args.first, args.second)
        elif args.operation == 'multiply':
            result = multiply(args.first, args.second)
        elif args.operation == 'divide':
            result = divide(args.first, args.second)
        
        print(result)
        return 0
        
    except ValueError as e:
        print(f"Error: {e}", file=sys.stderr)
        return 1
    except Exception as e:
        print(f"Unexpected error: {e}", file=sys.stderr)
        return 1


if __name__ == '__main__':
    sys.exit(main())