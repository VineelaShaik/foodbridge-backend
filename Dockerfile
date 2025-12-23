# ---------- STAGE 1: BUILD ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (faster builds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests


# ---------- STAGE 2: RUN ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built jar from stage 1
COPY --from=build /app/target/foodbridge-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
