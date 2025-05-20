# Stage 1: Build the React Frontend
FROM node:16-alpine as frontend-builder

WORKDIR /app

COPY pom.xml .
COPY src/main/js/package.json src/main/js/package.json
COPY src/main/js/package-lock.json src/main/js/package-lock.json

RUN npm --prefix src/main/js install

COPY src/main/js/ src/main/js/

RUN npm --prefix src/main/js run webpack

# Stage 2: Build the Spring Boot Backend
FROM maven:3.8.7-openjdk-17 as backend-builder

WORKDIR /app

COPY . .

COPY --from=frontend-builder /app/src/main/js/target/classes/static/built/ src/main/resources/static/built/

RUN mvn clean package -DskipTests

# Stage 3: Create the final production image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=backend-builder /app/target/react-and-spring-data-rest-0.0.1-SNAPSHOT.jar react-and-spring-data-rest.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "react-and-spring-data-rest.jar"]