# Use OpenJDK 17 slim image for smaller size
FROM eclipse-temurin:17-jre

# Set timezone to match your local time
ENV TZ=Europe/Berlin
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY build/libs/java-hello-world-1.0.0.jar app.jar

# Expose port (optional, for future use)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
