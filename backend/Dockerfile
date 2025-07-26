# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set work directory
WORKDIR /app

# Copy Gradle wrapper files
COPY backend/gradlew backend/build.gradle backend/settings.gradle backend/gradle /app/
COPY backend/src /app/src
COPY backend/src/main/resources /app/src/main/resources

# Give execute permissions to Gradle wrapper
RUN chmod +x ./gradlew

# Build the Spring Boot application
RUN ./gradlew build -x test

# Run the app
EXPOSE 8080
CMD ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
