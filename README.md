# 🏢 Azurimmo - Application de Gestion Immobilière

Azurimmo est une API RESTful centralisée pour la gestion complète des biens immobiliers (bâtiments, appartements), des contrats, des locataires, des loyers et des interventions (réparations, maintenance) pour la société civile immobilière AzurImmo.

Elle est développée à l'aide du framework **Spring Boot** en Java.

### Architecture Applicative

L'application est divisée en trois couches:
1.  **Couche de présentation (Frontend)** : Interface web et/ou application mobile (Android/iOS).
2.  **Couche métier (Backend)** : Une API Rest centralisée (cette application).
3.  **Couche de données (Database)** : Base de données **MariaDB**.

### Pré-requis

-   **JDK (Java Development Kit)** : Version 21 ou supérieure.
-   **Maven** ou **Gradle** (Maven est utilisé pour l'importation dans Eclipse).
-   **MariaDB/MySQL** : Pour la base de données (avec un outil comme WampServer/phpmyadmin).
-   **IDE** : Eclipse (version pour le développement web).
-   **Outil de test API** : Postman.

## Installation

Le projet a été initialisé via Spring Initializr (`https://start.spring.io/`).

### Configuration de la Base de Données

1.  Créez la base de données nommée `azurimmo` (ou autre nom si modifié dans la configuration) sous MariaDB (via phpmyadmin).
2.  Dans le fichier `src/main/resources/application.properties`, adaptez les lignes de configuration de la base de données et du serveur Tomcat si nécessaire (par défaut, port Tomcat: 9008, port MariaDB: 3307).

```properties
# Tomcat configuration
server.port = 9008

# Mariadb Database configuration
spring.datasource.url=jdbc:mariadb://localhost:3307/azurimmo
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```
### Note : Le port 9008 est configuré pour le serveur Spring Boot.

## Importation du projet dans Eclipse
1. Dézippez le fichier du projet (e.g., azurimmo.zip).

2. Lancez Eclipse.

3. Utilisez File | Import | Maven | Existing Maven Projects et sélectionnez le dossier azurimmo.

## ▶️ Démarrage
<ol>
  <li>Assurez-vous que votre serveur de base de données (MariaDB) est démarré.</li>
  <li>Exécutez l'application via Eclipse:</li>
</ol>

<ul>
  <li>Cliquez sur le petit triangle vert, ou</li>
  <li>Faites un clic droit sur la classe principale AzurimmoApplication.java, puis Run as Java Application.</li>
</ul>
L'application démarrera le serveur Tomcat embarqué sur le port configuré (par défaut 9008).

## 🌐 Endpoints de l'API (Ressource Batiment)
La Base URL est http://localhost:9008. Les endpoints de base pour l'entité Batiment sont automatiquement exposés par Spring Data REST.
| Action  | Méthode HTTP | Endpoint (Exemple) | Description |
| :--------------- | :--------------- | :---------------| :--------------- |
| Récupérer la liste des bâtiments  | GET |  /batiments | Retourne tous les bâtiments.
| Récupérer un bâtiment précis  | GET | /batiments/{id} |Retourne le bâtiment avec l'ID spécifié.
| Créer un nouveau bâtiment | POST | /batiments | Ajoute un nouvel bâtiment.
| Mettre à jour un bâtiment | PUT | /batiments/{id} | Met à jour le bâtiment spécifié.
| Supprimer un bâtiment | DELETE | /batiments/{id} | Supprime le bâtiment spécifié.

## 🗃️ Documentation de l'API (Swagger/OpenAPI)
L'API est documentée et testable via Swagger (OpenAPI).

<ul>URL de la Documentation Swagger: `http://localhost:9008/swagger-ui/index.html` </ul>

## 🛠️ Commandes et Outils Utiles
| Commande/Outil  | Description |
| :--------------- | :--------------- |
| ``mvn install`` | Pour construire le projet et télécharger toutes les dépendances.
| Clic droit sur pom.xml | Maven
| Postman | Outil essentiel pour tester les requêtes ``POST``, ``PUT``, ``DELETE``.

## 💻 Fabriqué avec
| Technologie | Rôle dans Azurimmo |
| :--------------- | :--------------- |
| Java (JDK 21) | Langage de développement principal.
| Spring Boot (Framework Java) | Simplifie la création d'applications robustes.
| Spring Data JPA | Persistance des données (ORM).
| Spring Data REST | Exposition automatique des Repositories.
| MariaDB | Base de données.
| Lombok | Réduction du code répétitif (boilerplate code).

## 📌 Versions
Dernière version : 1.0 (Basé sur le document de spécifications fourni).
