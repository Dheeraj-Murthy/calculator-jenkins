"""
Unit tests for the Simple Calculator CLI Application
"""

import pytest
import sys
import os
import argparse

# Add the src directory to the path so we can import calculator
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))

from calculator import add, subtract, multiply, divide, parse_number


class TestCalculatorOperations:
    """Test basic arithmetic operations"""
    
    def test_add(self):
        """Test addition operation"""
        assert add(2, 3) == 5
        assert add(-1, 1) == 0
        assert add(0, 0) == 0
        assert add(2.5, 3.5) == 6.0
    
    def test_subtract(self):
        """Test subtraction operation"""
        assert subtract(5, 3) == 2
        assert subtract(1, 1) == 0
        assert subtract(0, 5) == -5
        assert subtract(5.5, 2.5) == 3.0
    
    def test_multiply(self):
        """Test multiplication operation"""
        assert multiply(2, 3) == 6
        assert multiply(0, 5) == 0
        assert multiply(-2, 3) == -6
        assert multiply(2.5, 4) == 10.0
    
    def test_divide(self):
        """Test division operation"""
        assert divide(6, 2) == 3.0
        assert divide(5, 2) == 2.5
        assert divide(-4, 2) == -2.0
        assert divide(0, 5) == 0.0
    
    def test_divide_by_zero(self):
        """Test division by zero raises ValueError"""
        with pytest.raises(ValueError, match="Cannot divide by zero"):
            divide(5, 0)


class TestParseNumber:
    """Test number parsing function"""
    
    def test_parse_integer(self):
        """Test parsing integer strings"""
        assert parse_number("5") == 5
        assert parse_number("-10") == -10
        assert parse_number("0") == 0
    
    def test_parse_float(self):
        """Test parsing float strings"""
        assert parse_number("5.5") == 5.5
        assert parse_number("-3.14") == -3.14
        assert parse_number("0.0") == 0.0
    
    def test_parse_invalid_number(self):
        """Test parsing invalid strings raises error"""
        with pytest.raises(argparse.ArgumentTypeError):
            parse_number("abc")
        with pytest.raises(argparse.ArgumentTypeError):
            parse_number("12.34.56")
        with pytest.raises(argparse.ArgumentTypeError):
            parse_number("")


