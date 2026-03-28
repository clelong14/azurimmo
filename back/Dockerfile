# Change la version 17 par 21 ici
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# On copie le jar (celui que tu as compilé en Java 21)
COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
