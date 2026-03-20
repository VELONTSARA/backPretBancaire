# Étape 1 : On utilise Maven pour compiler le projet
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : On utilise Java 17 pour lancer le JAR
FROM openjdk:17-jdk-slim
COPY --from=build /target/BackendPret-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]