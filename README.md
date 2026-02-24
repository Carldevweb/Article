# Article API (Spring Boot)

Backend du projet **Article** : JWT, users/admin, articles, catégories, médias (upload), favoris, reset mot de passe.

## Stack
- Java 17
- Spring Boot 3.3.x
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

## Prérequis
- Java 17
- PostgreSQL (base `article`)
- Maven

## Configuration

### Variables d’environnement
Le projet utilise des variables d’environnement (aucun secret en dur) :

- `DB_PASSWORD`
- `JWT_SECRET`
- `GMAIL_USERNAME`
- `GMAIL_APP_PASSWORD`

Exemple (PowerShell) :
```powershell
$env:DB_PASSWORD="..."
$env:JWT_SECRET="..."
$env:GMAIL_USERNAME="..."
$env:GMAIL_APP_PASSWORD="..."
