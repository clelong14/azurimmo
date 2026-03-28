# 1. On part d'une image Java légère
FROM eclipse-temurin:17-jdk-alpine

# 2. On crée un dossier pour l'application
WORKDIR /app

# 3. On copie le fichier .jar généré par Maven ou Gradle
# (Vérifie le nom du fichier dans ton dossier /target)
COPY target/*.jar app.jar

# 4. On lance l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
