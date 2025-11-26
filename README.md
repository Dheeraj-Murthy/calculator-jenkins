# Simple Calculator CLI Application

A command-line calculator application that supports basic arithmetic operations with automated CI/CD pipeline using Jenkins and Docker.

## Features

- Basic arithmetic operations: addition, subtraction, multiplication, division
- Command-line interface with argument parsing
- Error handling for invalid inputs and division by zero
- Comprehensive unit tests
- Docker containerization
- Automated CI/CD pipeline with Jenkins

## Project Structure

```
calculator/
├── src/
│   └── calculator.py          # Main calculator application
├── tests/
│   └── test_calculator.py     # Unit tests
├── Dockerfile                 # Docker configuration
├── Jenkinsfile               # Jenkins CI/CD pipeline
├── requirements.txt          # Python dependencies
└── README.md                 # This file
```

## Local Usage

### Prerequisites

- Python 3.12 or higher
- pip (Python package manager)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd calculator
```

2. Install dependencies:
```bash
pip install -r requirements.txt
```

### Running the Calculator

The calculator supports four operations: `add`, `subtract`, `multiply`, `divide`

#### Examples:

```bash
# Addition
python src/calculator.py add 5 3
# Output: 8

# Subtraction
python src/calculator.py subtract 10 4
# Output: 6

# Multiplication
python src/calculator.py multiply 3 4
# Output: 12

# Division
python src/calculator.py divide 15 3
# Output: 5.0

# Get help
python src/calculator.py --help
```

### Running Tests

```bash
# Run all tests
python -m pytest tests/ -v

# Run tests with coverage
python -m pytest tests/ -v --cov=src
```

## Docker Usage

### Build Docker Image

```bash
docker build -t calculator .
```

### Run with Docker

```bash
# Addition
docker run calculator add 5 3

# Division
docker run calculator divide 15 3

# Get help
docker run calculator --help
```

## Jenkins CI/CD Pipeline

This project includes a complete Jenkins pipeline that automates:

1. **Checkout**: Pulls code from GitHub repository
2. **Build**: Installs Python dependencies
3. **Test**: Runs unit tests with pytest
4. **Docker Build**: Creates Docker image if tests pass
5. **Docker Push**: Pushes image to DockerHub

### Jenkins Setup

1. Install Jenkins with Docker plugin
2. Configure DockerHub credentials in Jenkins (ID: `dockerhub-credentials`)
3. Create a new Pipeline job
4. Point it to your repository's `Jenkinsfile`
5. Update the `DOCKER_IMAGE` environment variable in Jenkinsfile with your DockerHub username

### Environment Variables

- `DOCKER_IMAGE`: Your DockerHub repository name (e.g., `your-username/calculator`)
- `DOCKER_TAG`: Image tag (default: `latest`)

## Error Handling

The calculator handles various error cases:

- Invalid operation names
- Non-numeric inputs
- Division by zero
- Missing arguments

## Testing

The test suite covers:

- All arithmetic operations
- Edge cases (division by zero, negative numbers)
- Input validation
- Number parsing (integers and floats)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is part of the CI/CD Pipeline Lab for educational purposes.