# ApartmentPredictor — Backend Spring Boot
Sistema de gestión inmobiliaria con soporte para apartamentos, casas, dúplex, adosados, contratos, reseñas, reviewers, propietarios y escuelas cercanas. Incluye generación automática de datos con DataFaker, relaciones JPA y API REST lista para conectar con un frontend.

------------------------------------------------------------
# Tecnologías

- Java 21
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (modo archivo)
- DataFaker
- Jackson
- Maven

------------------------------------------------------------
# Estructura del Proyecto

```txt
src/main/java/com/example/apartment_predictor
│
├── controller
│   ├── ApartmentController.java
│   ├── HouseController.java
│   ├── DuplexController.java
│   ├── TownHouseController.java
│   ├── OwnerController.java
│   ├── ReviewerController.java
│   ├── ReviewController.java
│   └── PropertyContractController.java
│
├── model
│   ├── Apartment.java
│   ├── House.java
│   ├── Duplex.java
│   ├── TownHouse.java
│   ├── Owner.java
│   ├── Reviewer.java
│   ├── Review.java
│   ├── School.java
│   ├── PropertyContract.java
│   └── Person.java
│
├── repository
│   ├── ApartmentRepository.java
│   ├── HouseRepository.java
│   ├── DuplexRepository.java
│   ├── TownHouseRepository.java
│   ├── OwnerRepository.java
│   ├── ReviewerRepository.java
│   ├── ReviewRepository.java
│   ├── SchoolRepository.java
│   └── PropertyContractRepository.java
│
├── service
│   ├── ApartmentService.java
│   ├── HouseService.java
│   ├── DuplexService.java
│   ├── TownHouseService.java
│   ├── OwnerService.java
│   ├── ReviewerService.java
│   ├── ReviewService.java
│   ├── PropertyContractService.java
│   └── PopulateMasterService.java
│
└── utils
    ├── ApartmentJsonWriter.java
    └── PrintingUtils.java
```

------------------------------------------------------------

# Modelo de Datos

## Apartment
Atributos:
- price, area, bedrooms, bathrooms, stories
- mainroad, guestroom, basement, hotwaterheating, airconditioning
- parking, prefarea, furnishingstatus

Relaciones:
- OneToMany → Review
- OneToMany → PropertyContract
- ManyToMany → School

## House / Duplex / TownHouse
Relaciones:
- OneToOne → Apartment
- ManyToOne → Owner
- ManyToMany → School

## Owner (hereda de Person)
Atributos:
- name, email, phone

Relaciones:
- OneToMany → House
- OneToMany → Duplex
- OneToMany → TownHouse
- OneToMany → PropertyContract

## Reviewer (hereda de Person)
Atributos:
- name, email, reputation

Relaciones:
- OneToMany → Review

## Review
Atributos:
- title, content, rating, reviewDate

Relaciones:
- ManyToOne → Apartment
- ManyToOne → Reviewer

## School
Atributos:
- name, address, type, educationLevel
- latitude, longitude, rating, studentCount

Relaciones:
- ManyToMany → Apartment, House, Duplex, TownHouse

## PropertyContract
Atributos:
- agreedPrice, startDate, endDate, active

Relaciones:
- ManyToOne → Owner
- ManyToOne → Apartment / House / Duplex / TownHouse

------------------------------------------------------------
# Población Automática de Datos

PopulateMasterService genera:
- 15 Schools
- 20 Owners
- Cada Owner tiene:
    - 3 Apartments
    - 1 House
    - 1 Duplex
    - 1 TownHouse
    - Contratos asociados
- 15 Reviewers
- 3 Reviews por Apartment

Todo generado con DataFaker al arrancar la aplicación.

------------------------------------------------------------
# API REST

## Apartments
GET /api/apartments  
GET /api/apartments/{id}  
POST /api/apartments  
PUT /api/apartments/{id}  
DELETE /api/apartments/{id}  
GET /api/apartments/export

## Houses
GET /api/houses  
GET /api/houses/{id}  
POST /api/houses  
PUT /api/houses/{id}  
DELETE /api/houses/{id}

## Duplexes
GET /api/duplexes  
GET /api/duplexes/{id}  
POST /api/duplexes  
PUT /api/duplexes/{id}  
DELETE /api/duplexes/{id}

## TownHouses
GET /api/townhouses  
GET /api/townhouses/{id}  
POST /api/townhouses  
PUT /api/townhouses/{id}  
DELETE /api/townhouses/{id}

## Owners
GET /api/owners  
GET /api/owners/{id}  
POST /api/owners  
PUT /api/owners/{id}  
DELETE /api/owners/{id}  
GET /api/owners/{id}/houses  
GET /api/owners/{id}/duplexes  
GET /api/owners/{id}/townhouses

## Reviewers
GET /api/reviewers  
GET /api/reviewers/{id}  
POST /api/reviewers  
PUT /api/reviewers/{id}  
DELETE /api/reviewers/{id}  
GET /api/reviewers/{id}/reviews

## Reviews
GET /api/apartments/{id}/reviews  
POST /api/apartments/{id}/reviews  
DELETE /api/reviews/{reviewId}

## Property Contracts
GET /api/contracts  
GET /api/contracts/{id}  
POST /api/contracts  
PUT /api/contracts/{id}/close  
DELETE /api/contracts/{id}
