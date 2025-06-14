# Stage 1: Build the Spring Boot application using Maven
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run the application with a lightweight JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar shopNest.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "shopNest.jar"]
