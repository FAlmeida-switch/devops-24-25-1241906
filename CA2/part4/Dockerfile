# Stage 1: Build the React Frontend
FROM node:16-alpine AS frontend-builder
WORKDIR /app

COPY CA2/part4/src/main/js/package.json CA2/part4/src/main/js/package-lock.json ./
RUN npm install

COPY CA2/part4/src/main/js/ ./

RUN npm run build

# Stage 2: Build the Spring Boot Backend
FROM maven:3.9.3-eclipse-temurin-17 AS backend-builder
WORKDIR /app

COPY --from=frontend-builder /app/ ../src/main/resources/static/built/

COPY CA2/part4/pom.xml .
COPY CA2/part4/src/main/ ./src/main/

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=backend-builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]