# Documentación del Proyecto: Apartment Predictor

## 1. Arquitectura general del proyecto

El proyecto sigue una estructura clásica de Spring Boot:

\`\`\`
controller/
model/
repository/
service/
ApartmentPredictorApplication.java
resources/
\`\`\`

### Función de cada capa

| Capa | Responsabilidad |
|------|------------------|
| **Model** | Entidades JPA, herencia, relaciones, lógica de dominio |
| **Repository** | Acceso a datos con Spring Data |
| **Service** | Lógica de negocio, validaciones, importación CSV |
| **Controller** | Endpoints REST para exponer la API |
| **Utils / Application** | Arranque, carga inicial de datos |

---

## 2. Sistema de herencia JPA

El proyecto utiliza herencia con:

\`\`\`java
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "property_type")
\`\`\`

### Características

- Tabla padre: **Property**
- Tabla hija: **Apartment**
- Subtipos: **House**, **Duplex**, **TownHouse**
- Columna discriminadora: **property_type**

Esto permite extender el sistema fácilmente y mantener un modelo de datos limpio.

---

## 3. Entidad Apartment

### Características principales

- Representa un apartamento genérico.
- Genera su propio \`id\` con UUID.
- Contiene atributos físicos y booleanos.
- Relación **OneToMany** con \`Review\`.
- Implementa lógica de negocio en \`calculatePrice()\`.

### Relación con Review

\`\`\`java
@OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private List<Review> reviews;
\`\`\`

---

## 4. Subtipos: House, Duplex, TownHouse

Cada subtipo extiende \`Apartment\` y añade atributos propios.

| Tipo | Atributos extra |
|------|------------------|
| **House** | garageQty, roofType, garden |
| **Duplex** | balcony, elevator, hasSeparateUtilities |
| **TownHouse** | hasHomeownersAssociation, hoaMonthlyFee |

Cada uno sobrescribe \`calculatePrice()\` con su propia fórmula.

---

## 5. Entidad Review

### Características

- Tiene su propio UUID.
- Relación \`@ManyToOne\` con Apartment.
- Campos: \`title\`, \`content\`, \`rating\`, \`reviewDate\`.

### Relación con Apartment

\`\`\`java
@JoinColumn(name = "apartment_fk")
@ManyToOne
private Apartment apartment;
\`\`\`

---

## 6. Entidad Owner

Entidad independiente que representa un propietario.

- Campos: \`name\`, \`email\`, \`age\`, \`isActive\`, \`isBusiness\`, \`idLegalOwner\`, \`registrationDate\`, \`qtyDaysAsOwner\`.
- No está relacionada con Apartment actualmente.

---

## 7. Entidad abstracta Property

\`Property\` es la clase base abstracta:

- Campos: \`id\`, \`area\`, \`locationRating\`.
- Estrategia de herencia \`JOINED\`.
- Método abstracto \`calculatePrice()\`.

---

## 8. Repositorios

Repositorios simples basados en Spring Data:

\`\`\`java
public interface ApartmentRepository extends CrudRepository<Apartment, String> {}
public interface ReviewRepository extends CrudRepository<Review, String> {}
\`\`\`

---

## 9. Servicios

### 9.1 ApartmentService

Incluye:

- \`findAll()\`: devuelve todos los apartamentos.
- \`createApartment()\`: guarda un nuevo apartamento.
- \`updateApartment()\`: actualiza un apartamento existente.
- \`updateApartmentById()\`: actualiza campo a campo un apartamento concreto.
- \`deleteApartment()\`: elimina por id.
- \`findApartmentById()\`: devuelve un apartamento o null.

### 9.2 LoadInitialDataService

Carga apartamentos desde CSV:

- Solo si la BD está vacía.
- Crea un tipo aleatorio: Apartment, House, Duplex, TownHouse.
- Rellena campos comunes.
- Rellena campos específicos según subtipo.
- Guarda en BD.

### 9.3 LoadReviewDataService

Carga reviews desde CSV:

- Solo si la BD está vacía.
- Asigna cada review a un apartamento aleatorio.
- Guarda en BD.

### 9.4 ReviewService

Servicio vacío preparado para ampliación.

---

## 10. Controlador REST: ApartmentRestController

Controlador principal para exponer la API de apartamentos.

### Endpoints

| Método | Endpoint | Acción |
|--------|----------|--------|
| GET | \`/api/apartment/getAll\` | Lista todos los apartamentos |
| GET | \`/api/apartment/getById?id=\` | Busca por ID |
| POST | \`/api/apartment/create\` | Crea un apartamento |
| POST | \`/api/apartment/update\` | Actualiza sin ID |
| PUT | \`/api/apartment/updateById?id=\` | Actualiza por ID |
| DELETE | \`/api/apartment/deleteById?id=\` | Elimina por ID |

---

## 11. Clase principal: ApartmentPredictorApplication

Responsabilidades:

- Punto de entrada de la aplicación.
- Carga automática de datos desde CSV.
- Inicialización de servicios.

### Flujo de arranque

1. Si no hay apartamentos → importa CSV.
2. Si no hay reviews → importa CSV.
3. Muestra logs informativos.

---

## 12. Configuración: application.properties

Configuración principal:

\`\`\`
spring.datasource.url=jdbc:h2:file:./db/apartmentpredictordb
spring.datasource.username=oscar
spring.datasource.password=1234

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

app.csv.path=db/Housing.csv
app.reviews.csv.path=db/Reviews.csv

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
\`\`\`

---

# Documentación de las Nuevas APIs REST

## 1. Introducción

Este documento describe los nuevos endpoints REST añadidos al proyecto **Apartment Predictor**, siguiendo un diseño RESTful más profesional y organizado.  
Incluye:

- Nuevos endpoints para gestionar apartamentos.
- Nuevos endpoints para gestionar reviews.
- Explicación detallada de cada API.
- Estructura de rutas y comportamiento esperado.

---

## 2. Endpoints REST para Apartamentos

Los endpoints siguen la convención REST estándar:

| Método | URL                        | Descripción |
|--------|----------------------------|-------------|
| GET    | `/api/apartments`         | Listar todos los apartamentos |
| GET    | `/api/apartments/{id}`    | Obtener un apartamento por ID |
| POST   | `/api/apartments`         | Crear un nuevo apartamento |
| PUT    | `/api/apartments/{id}`    | Actualizar un apartamento por ID |
| DELETE | `/api/apartments/{id}`    | Eliminar un apartamento por ID |

---

## 3. Detalle de cada API (Apartments)

### 3.1 GET `/api/apartments`

**Descripción:**  
Devuelve una lista completa de todos los apartamentos almacenados en la base de datos.

**Respuesta:**
- `200 OK` → Lista de apartamentos.

---

### 3.2 GET `/api/apartments/{id}`

**Descripción:**  
Obtiene un apartamento concreto mediante su ID.

**Respuestas:**
- `200 OK` → Apartamento encontrado.
- `404 Not Found` → No existe un apartamento con ese ID.

---

### 3.3 POST `/api/apartments`

**Descripción:**  
Crea un nuevo apartamento a partir del JSON enviado en el cuerpo de la petición.

**Respuestas:**
- `201 Created` → Apartamento creado correctamente.
- Cabecera `Location: /api/apartments/{id}` indicando la URL del recurso creado.

---

### 3.4 PUT `/api/apartments/{id}`

**Descripción:**  
Actualiza un apartamento existente.  
Se reemplazan los valores del apartamento con los del JSON recibido.

**Respuestas:**
- `200 OK` → Apartamento actualizado.
- `404 Not Found` → No existe un apartamento con ese ID.

---

### 3.5 DELETE `/api/apartments/{id}`

**Descripción:**  
Elimina un apartamento por su ID.

**Respuestas:**
- `204 No Content` → Eliminado correctamente.
- `404 Not Found` → No existe un apartamento con ese ID.

---

## 4. Endpoints REST para Reviews

Los endpoints permiten gestionar reviews asociadas a un apartamento.

| Método | URL                                   | Descripción |
|--------|---------------------------------------|-------------|
| GET    | `/api/apartments/{id}/reviews`        | Listar reviews de un apartamento |
| POST   | `/api/apartments/{id}/reviews`        | Crear una review para un apartamento |
| DELETE | `/api/reviews/{reviewId}`             | Eliminar una review por ID |

---

## 5. Detalle de cada API (Reviews)

### 5.1 GET `/api/apartments/{id}/reviews`

**Descripción:**  
Devuelve todas las reviews asociadas a un apartamento concreto.

**Respuestas:**
- `200 OK` → Lista de reviews.
- `404 Not Found` → El apartamento no existe.

---

### 5.2 POST `/api/apartments/{id}/reviews`

**Descripción:**  
Crea una nueva review y la asocia al apartamento indicado.

**Respuestas:**
- `201 Created` → Review creada correctamente.
- `404 Not Found` → El apartamento no existe.

---

### 5.3 DELETE `/api/reviews/{reviewId}`

**Descripción:**  
Elimina una review por su ID.

**Respuestas:**
- `204 No Content` → Review eliminada.
- `404 Not Found` → No existe una review con ese ID.

---
# 1. APARTMENTS

--------------------------------------------------------------------
1. APARTMENTS
--------------------------------------------------------------------

1.1 GET – Obtener todos los apartamentos
URL:
GET http://localhost:8080/api/apartments

Respuesta esperada (200 OK):
[
{
"id": "1234-5678",
"price": 120000,
"area": 80,
"bedrooms": 3,
"bathrooms": 2,
"stories": 1,
"mainroad": "yes",
"guestroom": "no",
"basement": "no",
"hotwaterheating": "yes",
"airconditioning": "yes",
"parking": 1,
"prefarea": "yes",
"furnishingstatus": "semi-furnished",
"locationRating": 4,
"reviews": []
}
]

--------------------------------------------------------------------

1.2 GET – Obtener un apartamento por ID
URL:
GET http://localhost:8080/api/apartments/{id}

Ejemplo:
GET http://localhost:8080/api/apartments/1234-5678

Respuesta esperada (200 OK):
{
"id": "1234-5678",
"price": 120000,
"area": 80,
"bedrooms": 3,
"bathrooms": 2,
"stories": 1,
"mainroad": "yes",
"guestroom": "no",
"basement": "no",
"hotwaterheating": "yes",
"airconditioning": "yes",
"parking": 1,
"prefarea": "yes",
"furnishingstatus": "semi-furnished",
"locationRating": 4,
"reviews": []
}

Respuesta si no existe (404):
{
"error": "Apartment not found"
}

--------------------------------------------------------------------

1.3 POST – Crear un apartamento
URL:
POST http://localhost:8080/api/apartments

Body (JSON):
{
"price": 150000,
"area": 95,
"bedrooms": 3,
"bathrooms": 2,
"stories": 1,
"mainroad": "yes",
"guestroom": "no",
"basement": "no",
"hotwaterheating": "yes",
"airconditioning": "yes",
"parking": 1,
"prefarea": "yes",
"furnishingstatus": "furnished",
"locationRating": 5
}

Respuesta esperada (201 Created):
{
"id": "generated-uuid",
"price": 150000,
"area": 95,
"bedrooms": 3,
"bathrooms": 2,
"stories": 1,
"mainroad": "yes",
"guestroom": "no",
"basement": "no",
"hotwaterheating": "yes",
"airconditioning": "yes",
"parking": 1,
"prefarea": "yes",
"furnishingstatus": "furnished",
"locationRating": 5,
"reviews": []
}

--------------------------------------------------------------------

1.4 PUT – Actualizar un apartamento por ID
URL:
PUT http://localhost:8080/api/apartments/{id}

Body (JSON):
{
"price": 180000,
"area": 100,
"bedrooms": 4,
"bathrooms": 2,
"stories": 2,
"mainroad": "yes",
"guestroom": "yes",
"basement": "no",
"hotwaterheating": "yes",
"airconditioning": "yes",
"parking": 2,
"prefarea": "no",
"furnishingstatus": "semi-furnished",
"locationRating": 3
}

Respuesta esperada (200 OK):
{
"id": "1234-5678",
"price": 180000,
"area": 100,
"bedrooms": 4,
"bathrooms": 2,
"stories": 2,
"mainroad": "yes",
"guestroom": "yes",
"basement": "no",
"hotwaterheating": "yes",
"airconditioning": "yes",
"parking": 2,
"prefarea": "no",
"furnishingstatus": "semi-furnished",
"locationRating": 3,
"reviews": []
}

--------------------------------------------------------------------

1.5 DELETE – Eliminar un apartamento por ID
URL:
DELETE http://localhost:8080/api/apartments/{id}

Respuesta esperada (204 No Content):
Sin body.

--------------------------------------------------------------------
2. REVIEWS
--------------------------------------------------------------------

2.1 GET – Obtener reviews de un apartamento
URL:
GET http://localhost:8080/api/apartments/{id}/reviews

Respuesta esperada (200 OK):
[
{
"id": "rev-001",
"title": "Great place",
"content": "Very comfortable and well located.",
"rating": 5,
"reviewDate": "2024-01-10"
}
]

--------------------------------------------------------------------

2.2 POST – Crear una review para un apartamento
URL:
POST http://localhost:8080/api/apartments/{id}/reviews

Body (JSON):
{
"title": "Amazing apartment",
"content": "Everything was perfect, highly recommended.",
"rating": 5,
"reviewDate": "2024-02-01"
}

Respuesta esperada (201 Created):
{
"id": "generated-review-id",
"title": "Amazing apartment",
"content": "Everything was perfect, highly recommended.",
"rating": 5,
"reviewDate": "2024-02-01",
"apartment": {
"id": "1234-5678"
}
}

--------------------------------------------------------------------

2.3 DELETE – Eliminar una review por ID
URL:
DELETE http://localhost:8080/api/reviews/{reviewId}

Respuesta esperada (204 No Content):
Sin body.

--------------------------------------------------------------------
3. ESTRUCTURA RECOMENDADA DE COLECCIÓN POSTMAN
--------------------------------------------------------------------

Apartment Predictor API
├── Apartments
│    ├── GET All Apartments
│    ├── GET Apartment by ID
│    ├── POST Create Apartment
│    ├── PUT Update Apartment
│    └── DELETE Delete Apartment
│
└── Reviews
├── GET Reviews by Apartment
├── POST Create Review
└── DELETE Delete Review

--------------------------------------------------------------------
4. HEADERS RECOMENDADOS
--------------------------------------------------------------------

Content-Type: application/json
Accept: application/json

--------------------------------------------------------------------
FIN DEL DOCUMENTO
--------------------------------------------------------------------
