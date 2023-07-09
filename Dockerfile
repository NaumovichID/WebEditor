# Fetching latest version of Java
FROM openjdk:18

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/WebEditor-0.0.1-SNAPSHOT.jar /app

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "WebEditor-0.0.1-SNAPSHOT.jar"]

#FROM maven:3.5-jdk-8-alpine AS builder
#COPY ./pom.xml ./pom.xml
#COPY ./src ./src
#RUN mvn clean package
#
#FROM openjdk:8-jdk-alpine
#COPY --from=builder target/WebEditor-0.0.1-SNAPSHOT.jar application.jar
#ENTRYPOINT ["java", "-jar", "application.jar"]