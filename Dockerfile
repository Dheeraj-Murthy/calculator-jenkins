# Multi-stage build for Simple Calculator CLI Application
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Production stage
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/calculator-cli-*.jar calculator-cli.jar

# Create a non-root user
RUN groupadd -g 1001 appgroup 2>/dev/null || true && useradd -u 1001 -g appgroup -s /bin/sh appuser
RUN chown -R appuser:appgroup /app
USER appuser

# Set entrypoint for the calculator CLI
ENTRYPOINT ["java", "-jar", "calculator-cli.jar"]

# Default command shows help
CMD []