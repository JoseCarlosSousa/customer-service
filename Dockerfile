# Step 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 🌟 Expose port 8084 to match customer-service configuration specifications
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
