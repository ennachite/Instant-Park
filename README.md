# Parking API Project

## Introduction
Ce projet expose un ensemble d’API qui interagissent avec un système de parking. Les API fournissent diverses fonctionnalités ( jatni fonctionnalité hsen la?) à savoir :
- Obtenir toutes les informations de stationnement
- Récupérer les détails de stationnement par ID
- Rechercher les stationnemens par nom
- Obtenir un stationnement à proximité en fonction des coordonnées géographiques.

Le projet est construit en utilisant Java 11 et Spring Boot 2.7.10 et utilise les annotations de cache de Spring Boot pour optimiser les temps de réponse des API en stockant les résultats des appels de méthode coûteux et en les réutilisant lorsque les mêmes appels sont effectués.

## Technologies utilisées
- Java 11
- Spring Boot 2.7.10
- Spring Boot Caching Annotations
- RestTemplate
- Maven
- Docker

J'ai choisi d’utiliser RestTemplate parce que c’est un client HTTP synchrone qui offre un moyen pratique de consommer des services web. Il simplifie la communication avec les serveurs HTTP et applique les principes RESTful. Il fournit des fonctionnalités puissantes telles que la gestion automatique des erreurs, les convertisseurs de messages pour convertir les messages HTTP, binding to typed beans, etc.

## Endpoints
Voici les endpoints fournis par l’API Parking:
1. `GET /api/v1/parkings` : Récupérer tous les parkings
2. `GET /api/v1/parkings/{id}` : Récupérer un parking par ID
3. `GET /api/v1/parkings/nearby?latitude={latitude}&longitude={longitude}` : Récupérer un parking à proximité en fonction des coordonnées géographiques
4. `GET /api/v1/parkings/search?nom={nom}` : Rechercher un parking par nom

## Domain Models
1. `ParkingInfo` : Ce modèle représente l’information sur le stationnement. Il comprend des détails comme l’identité, le nom du stationnement, le nombre total de places, les coordonnées géographiques, l’adresse, l’information, le tarif horaire et la distance.

2. `ParkingAvailability` : Ce modèle représente la disponibilité du stationnement. Il comprend des détails comme le nom du stationnement, les places disponibles, le taux d’occupation, la capacité, les coordonnées géographiques et l’heure de la dernière mise à jour.

## Calcul de distance
Pour l’extrémité "nearby", la distance entre l’emplacement de l’utilisateur et les parkings est calculée à l’aide de la formule Haversine. Cette formule calcule la plus courte distance entre deux points sur la surface d’une sphère. Cependant, pour les applications du monde réel, des résultats plus précis peuvent être obtenus en utilisant des API comme l’API Google Maps Distance Matrix.

Voici la formule Haversine utilisée :

```math
a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
c = 2 ⋅ atan2( √a, √(1−a) )
d = R ⋅ c
```

Où:
- φ est latitude
- λ est la longitude
- R est le rayon de la Terre (rayon moyen = 6371 km)

[//]: # (- Remarque: Les angles doivent être en radians pour passer aux fonctions trig)

## Dockerization
Ce projet peut être conteneurisé en utilisant Docker. Le Dockerfile est fourni dans le répertoire racine du projet. Vous pouvez créer une image de l’application et l’exécuter dans un conteneur Docker pour faciliter le déploiement et l’évolutivité.

## Temps passé
L’ensemble du projet, de la conception aux essais et à la documentation, a pris environ 5 heures.

Veuillez consulter le code pour plus de détails. N'hésitez pas à me faire part de vos commentaires ou suggestions d'amélioration.
