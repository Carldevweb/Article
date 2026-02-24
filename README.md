# Article API (Spring Boot)

API backend du projet **Article** : authentification JWT, gestion des utilisateurs (admin), articles, catégories, médias (upload), favoris, reset mot de passe.

## Stack
- Java 17+ (ou 21)
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

## Prérequis
- Java installé (`java -version`)
- Maven (`mvn -v`) ou Maven Wrapper
- PostgreSQL (base + user)
- Variables d’environnement (voir ci-dessous)

## Configuration

### Base de données
Dans `src/main/resources/application.properties` :

- `spring.datasource.url=jdbc:postgresql://localhost:5432/article`
- `spring.datasource.username=postgres`
- `spring.datasource.password=${DB_PASSWORD}`

Crée une base `article` (ou adapte l’URL).

### Variables d’environnement requises
L’application utilise des variables d’environnement, **aucun secret n’est commit**.

À définir dans ton OS / IDE :

- `DB_PASSWORD` : mot de passe PostgreSQL
- `JWT_SECRET` : clé secrète JWT (longue, aléatoire)
- `GMAIL_USERNAME` : email Gmail pour l’envoi
- `GMAIL_APP_PASSWORD` : mot de passe d’application Gmail (pas le mot de passe du compte)

Exemple (PowerShell) :
```powershell
$env:DB_PASSWORD="..."
$env:JWT_SECRET="..."
$env:GMAIL_USERNAME="..."
$env:GMAIL_APP_PASSWORD="..."
