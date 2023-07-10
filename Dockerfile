# Build stage
FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR /app
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package

# Fetching latest version of Java
FROM openjdk:18

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
# COPY ./target/WebEditor-0.0.1-SNAPSHOT.jar /app
COPY --from=build /app/target/WebEditor-0.0.1-SNAPSHOT.jar WebEditor.jar
RUN mkdir -p /app/files
# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "WebEditor.jar"]
