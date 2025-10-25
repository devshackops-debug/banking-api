# ------------------------------
# Stage 1: Build
# ------------------------------
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and source
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

# Build the app
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy the jar
COPY --from=build /app/target/*.jar app.jar

# Set Spring profile
ENV SPRING_PROFILES_ACTIVE=docker

# Expose Spring Boot port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
