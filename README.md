# Booking API

API de réservation développée avec Spring Boot 3.5 et MongoDB.

## Fonctionnalités

- Authentification JWT
- Gestion des salles (CRUD)
- Gestion des créneaux de réservation
- API REST avec Swagger
- Sécurité Spring Security

## Technologies

- Java 21
- Spring Boot 3.5
- MongoDB
- JWT (jjwt 0.12.6)
- SpringDoc OpenAPI (Swagger)
- Maven

## Prérequis

- JDK 21
- Docker & Docker Compose
- MongoDB (ou utiliser Docker)

## Configuration

Créer un fichier `.env` à la racine :

```env
MONGODB_URI=mongodb://localhost:27017/booking_db
JWT_SECRET=votre_secret_jwt_minimum_256_bits
```

## Installation

```bash
# Compiler le projet
./mvnw package

# Lancer les tests
./mvnw test
```

## Docker

```bash
# Construire l'image
docker build -t booking-api .

# Lancer avec Docker Compose
docker-compose up -d
```

## API

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`

## Endpoints

### Auth
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion

### Rooms
- `GET /api/rooms` - Liste des salles
- `POST /api/rooms` - Créer une salle
- `PUT /api/rooms/{id}` - Modifier une salle
- `DELETE /api/rooms/{id}` - Supprimer une salle

### Slots
- `GET /api/slots` - Liste des créneaux
- `POST /api/slots` - Créer un créneau
- `PUT /api/slots/{id}` - Modifier un créneau
- `DELETE /api/slots/{id}` - Supprimer un créneau

## Licence

MIT