# -------------------------------
# Build stage
# -------------------------------
FROM maven:3.9.6-openjdk-17 AS build

WORKDIR /app

# Copy Maven wrapper and project files first (for dependency caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Ensure mvnw is executable
RUN chmod +x mvnw

# Download dependencies (cache them)
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# -------------------------------
# Runtime stage
# -------------------------------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "ecommerce.jar"]
