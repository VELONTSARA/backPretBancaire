# Étape 1 : Compilation
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Exécution
FROM openjdk:17-jdk-slim
# On retire le "/" devant target et on utilise l'astérisque
COPY --from=build target/BackendPret-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]