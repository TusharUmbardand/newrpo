# Use official OpenJDK as base image
FROM openjdk:17-jdk-slim


# Copy Maven wrapper & pom.xml first (for dependency caching)
COPY pom.xml .
COPY src ./src
# Download dependencies (this makes future builds faster)
RUN ./mvnw dependency:go-offline


# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port 8080 for Spring Boot
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/ecommerce-0.0.1-SNAPSHOT.jar"]
