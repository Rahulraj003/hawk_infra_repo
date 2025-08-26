# Use OpenJDK 17 slim image for smaller size
FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY build/libs/java-hello-world-1.0.0.jar app.jar

# Expose port (optional, for future use)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
