# Base image
FROM openjdk:17-jre-slim

# Add Spring Boot JAR file to the container
COPY target/your-app.jar /app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
