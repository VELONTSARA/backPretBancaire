# Étape 1 : Compilation avec Maven
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Exécution avec Eclipse Temurin (Plus stable et disponible)
FROM eclipse-temurin:17-jdk-focal
# Utilisation de l'astérisque pour être sûr de trouver le JAR
COPY --from=build target/BackendPret-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]