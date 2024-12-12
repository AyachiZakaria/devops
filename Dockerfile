FROM openjdk:17-jdk-alpine
EXPOSE 8082
ADD target/events_project-1.0.jar events_project-1.0.jar
ENTRYPOINT ["java", "-jar", "/events_project-1.0.jar"]