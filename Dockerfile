FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:8-jdk-alpine
WORKDIR /app

COPY target/*.jar /app/app.jar
RUN chmod +x /app/app.jar
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]