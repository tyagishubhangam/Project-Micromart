# Use official OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file to the working directory
COPY target/ServiceRegistry-0.0.1-SNAPSHOT.jar app.jar



# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]