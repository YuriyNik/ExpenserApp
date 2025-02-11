FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/*.jar /app/app.jar
RUN chmod +x /app/app.jar
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]