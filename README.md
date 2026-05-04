# Azurimmo

Application de gestion immobilière developpee avec Spring Boot. Elle expose une API RESTful permettant de gerer des batiments, des appartements, des contrats de location, des locataires, des loyers et des interventions de maintenance.

---

## Architecture

L'application suit une architecture trois tiers :

- **Frontend** : Interface web statique (HTML/CSS/JS) ou application Vue.js
- **Backend** : API REST Spring Boot
- **Base de donnees** : MariaDB

---

## Prerequis

| Outil | Version recommandee |
|---|---|
| JDK | 21 ou superieure |
| Maven | 3.x (import Eclipse) |
| MariaDB | Compatible avec le dialecte `MariaDBDialect` |
| Eclipse IDE | Version Java EE |
| Postman | Pour tester les endpoints |

---

## Installation

### 1. Base de donnees

Creer une base de donnees nommee `azurimmo` dans MariaDB via phpMyAdmin ou la ligne de commande :

```sql
CREATE DATABASE azurimmo;
```

### 2. Configuration

Adapter le fichier `src/main/resources/application.properties` selon votre environnement :

```properties
server.port=9008

spring.datasource.url=jdbc:mariadb://localhost:3307/azurimmo
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Import dans Eclipse

1. Dezippez le projet.
2. Ouvrez Eclipse.
3. Allez dans **File > Import > Maven > Existing Maven Projects**.
4. Selectionnez le dossier du projet.

---

## Demarrage

1. Assurez-vous que MariaDB est demarre.
2. Lancez l'application depuis Eclipse :
   - Clic droit sur `AzurimmoApplication.java` > **Run As > Java Application**
   - Ou utilisez le bouton d'execution directement.

Le serveur Tomcat embarque demarre sur le port `9008` par defaut.

---

## Endpoints de l'API

La base URL est `http://localhost:9008/api`.

### Batiments

| Methode | Endpoint | Description |
|---|---|---|
| GET | `/batiments/all` | Retourne tous les batiments |
| GET | `/batiments/{id}` | Retourne un batiment par son ID |
| GET | `/batiments/ville/{ville}` | Recherche par ville |
| POST | `/batiments/` | Cree un nouveau batiment |

### Appartements

| Methode | Endpoint | Description |
|---|---|---|
| GET | `/appartements/all` | Retourne tous les appartements |
| GET | `/appartements/{id}` | Retourne un appartement par son ID |
| GET | `/appartements/batiment/{id}` | Appartements d'un batiment |
| GET | `/appartements/ville/{ville}` | Appartements par ville |
| GET | `/appartements/surface/{surface}` | Appartements avec surface minimale |
| POST | `/appartements/` | Cree un nouvel appartement |

### Contrats

| Methode | Endpoint | Description |
|---|---|---|
| GET | `/contrats/all` | Retourne tous les contrats |
| GET | `/contrats/{id}` | Retourne un contrat par son ID |
| GET | `/contrats/contrat/{id}` | Contrats d'un appartement |
| POST | `/contrats/` | Cree un nouveau contrat |

### Locataires

| Methode | Endpoint | Description |
|---|---|---|
| GET | `/locataires/all` | Retourne tous les locataires |
| GET | `/locataires/{id}` | Retourne un locataire par son ID |
| GET | `/locataires/locataire/{id}` | Locataires d'un contrat |
| POST | `/locataires/` | Cree un nouveau locataire |

### Loyers

| Methode | Endpoint | Description |
|---|---|---|
| GET | `/loyers/all` | Retourne tous les loyers |
| GET | `/loyers/{id}` | Retourne un loyer par son ID |
| GET | `/loyers/loyer/{id}` | Loyers d'un contrat |
| POST | `/loyers/` | Cree un nouveau loyer |

### Interventions

| Methode | Endpoint | Description |
|---|---|---|
| GET | `/interventions/all` | Retourne toutes les interventions |
| GET | `/interventions/{id}` | Retourne une intervention par son ID |
| GET | `/interventions/intervention/{id}` | Interventions d'un appartement |
| POST | `/interventions/` | Cree une nouvelle intervention |

---

## Documentation Swagger

L'API est documentee via Swagger UI, accessible une fois l'application demarree :

```
http://localhost:9008/swagger-ui/index.html
```

La specification OpenAPI brute est disponible a :

```
http://localhost:9008/api-docs
```

---

## Structure du projet

```
azurimmo/
├── back/
│   └── src/main/java/bts/sio/azurimmo/
│       ├── config/          # Configuration Swagger
│       ├── controller/      # Controleurs REST
│       ├── model/
│       │   ├── dto/         # Objets de transfert de donnees
│       │   └── mapper/      # Conversions entite <-> DTO
│       ├── repository/      # Interfaces Spring Data JPA
│       └── service/         # Logique metier
└── front/                   # Application Vue.js (Vite)
```

---

## Technologies

| Technologie | Role |
|---|---|
| Java 21 | Langage principal |
| Spring Boot | Framework applicatif |
| Spring Data JPA | Persistence des donnees (ORM) |
| MariaDB | Base de donnees relationnelle |
| Lombok | Reduction du code repetitif |
| Springdoc OpenAPI | Documentation Swagger |
| Vue 3 + Vite | Interface frontend |
| Axios | Requetes HTTP cote frontend |

---

## Mise en production interne

Cette section decrit le deploiement de l'application sur un serveur interne (reseau local ou serveur d'entreprise), sans passer par un hebergeur cloud.

### Prerequis sur le serveur

- JDK 21 installe et accessible via `java -version`
- MariaDB installe et demarre en tant que service
- Acces reseau ouvert sur le port choisi (par defaut `9008`)

### 1. Creer la base de donnees de production

Se connecter a MariaDB sur le serveur et creer la base :

```sql
CREATE DATABASE azurimmo;
CREATE USER 'azurimmo_user'@'localhost' IDENTIFIED BY 'mot_de_passe_securise';
GRANT ALL PRIVILEGES ON azurimmo.* TO 'azurimmo_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Configurer le profil de production

Creer un fichier `src/main/resources/application-prod.properties` avec les parametres du serveur cible :

```properties
server.port=9008

spring.datasource.url=jdbc:mariadb://localhost:3306/azurimmo
spring.datasource.username=azurimmo_user
spring.datasource.password=mot_de_passe_securise
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

> Ne pas versionner ce fichier s'il contient des identifiants. L'ajouter au `.gitignore`.

### 3. Generer le JAR executable

Depuis la racine du projet `back/`, executer :

```bash
mvn clean package -DskipTests
```

Le JAR est genere dans `back/target/azurimmo-*.jar`.

### 4. Transferer le JAR sur le serveur

Copier le fichier via SCP, partage reseau ou tout autre moyen disponible :

```bash
scp target/azurimmo-0.0.1-SNAPSHOT.jar utilisateur@adresse-serveur:/opt/azurimmo/
```

### 5. Demarrer l'application

Sur le serveur, lancer le JAR en activant le profil de production :

```bash
java -jar -Dspring.profiles.active=prod /opt/azurimmo/azurimmo-0.0.1-SNAPSHOT.jar
```

Pour un demarrage en arriere-plan avec journalisation :

```bash
nohup java -jar -Dspring.profiles.active=prod /opt/azurimmo/azurimmo-0.0.1-SNAPSHOT.jar > /opt/azurimmo/azurimmo.log 2>&1 &
```

### 6. Demarrage automatique au boot (Linux avec systemd)

Creer le fichier de service `/etc/systemd/system/azurimmo.service` :

```ini
[Unit]
Description=Azurimmo Spring Boot Application
After=network.target mariadb.service

[Service]
User=www-data
ExecStart=java -jar -Dspring.profiles.active=prod /opt/azurimmo/azurimmo-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Activer et demarrer le service :

```bash
sudo systemctl daemon-reload
sudo systemctl enable azurimmo
sudo systemctl start azurimmo
sudo systemctl status azurimmo
```

### 7. Deploiement du frontend Vue.js

Depuis le dossier `front/`, generer les fichiers statiques :

```bash
npm install
npm run build
```

Les fichiers sont produits dans `front/dist/`. Les copier dans le dossier servi par le backend Spring Boot (`back/src/main/resources/static/`) avant de regenerer le JAR, ou les servir via un serveur web separé (Nginx, Apache).

### Verification

Une fois l'application demarree, verifier son bon fonctionnement :

```
http://adresse-serveur:9008/swagger-ui/index.html
http://adresse-serveur:9008/api/batiments/all
```

---

## Version

Version actuelle : **1.0.1**
