Article API – Backend Spring Boot

Backend d’une application full stack de gestion d’articles, conçu pour démontrer la mise en place d’une architecture REST sécurisée, structurée et prête pour une intégration avec un frontend Angular.

Objectif du projet

Concevoir une API REST sécurisée permettant :

L’authentification via JWT

La gestion des utilisateurs avec rôles (USER / ADMIN)

Le CRUD complet d’articles

La gestion des catégories

L’upload et la gestion de médias

La gestion des favoris

La réinitialisation de mot de passe par email

Le projet met l’accent sur la sécurité, la structuration backend et la séparation des responsabilités.

Stack technique

Java 17

Spring Boot 3.3.x

Spring Security (JWT)

Spring Data JPA / Hibernate

PostgreSQL

Maven

Architecture

Architecture en couches :

Controller
→ Service
→ Repository

Principes appliqués :

Séparation DTO / Entités

Validation des données côté backend

Sécurisation des endpoints via rôles

Gestion centralisée de la sécurité

Variables d’environnement pour tous les secrets

Cette architecture permet une meilleure maintenabilité, une séparation claire des responsabilités et une évolutivité du projet.

Sécurité

Authentification stateless basée sur JWT

Gestion des rôles (USER / ADMIN)

Sécurisation des endpoints via Spring Security

Aucun secret stocké en dur

Reset de mot de passe via envoi d’email sécurisé

Choix de JWT

JWT a été choisi pour :

Permettre une architecture frontend / backend séparée

Maintenir une authentification stateless

Faciliter l’intégration avec une application SPA Angular

🗄 Base de données

PostgreSQL

Relations principales :

User ↔ Articles

Article ↔ Category

Article ↔ Media

User ↔ Favoris

Mapping via JPA / Hibernate avec gestion des relations et contraintes.

Fonctionnalités principales
Authentification

Inscription

Connexion

Génération et validation de JWT

Reset de mot de passe

Articles

Création

Modification

Suppression

Consultation

Association à une catégorie

Association à des médias

Catégories

CRUD complet

Relation avec articles

Médias

Upload d’images

Stockage côté serveur

Liaison aux articles

Favoris

Ajout / suppression d’articles en favoris

Configuration
Prérequis

Java 17

PostgreSQL (base nommée article)

Maven

Variables d’environnement

Le projet utilise exclusivement des variables d’environnement (aucun secret en dur) :

DB_PASSWORD

JWT_SECRET

GMAIL_USERNAME

GMAIL_APP_PASSWORD
