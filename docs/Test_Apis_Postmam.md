APIS_TEST.MD
DOCUMENTO DE PRUEBAS DE LA API APARTMENT PREDICTOR

--------------------------------------------------------------------
1. INTRODUCCIÓN
--------------------------------------------------------------------

Este documento describe las pruebas realizadas sobre la API Apartment Predictor utilizando Postman. Incluye:

- Pruebas de éxito (OK)
- Pruebas de error (ERROR)
- Estructura de colección recomendada
- Tests automáticos en Postman
- Resultados esperados

Todas las pruebas se ejecutan desde Postman en su nueva interfaz, usando Scripts → Post-response.

--------------------------------------------------------------------
2. ESTRUCTURA DE LA COLECCIÓN EN POSTMAN
--------------------------------------------------------------------

Apartment Predictor API
├── Apartments - Tests OK
│     ├── GET All Apartments - OK
│     ├── GET Apartment by ID - OK
│     ├── POST Create Apartment - OK
│     ├── PUT Update Apartment - OK
│     └── DELETE Apartment - OK
│
├── Apartments - Tests ERROR
│     ├── GET Apartment by ID - Not Found
│     ├── POST Create Apartment - Bad Request
│     └── DELETE Apartment - Not Found
│
├── Reviews - Tests OK
│     ├── GET Reviews - OK
│     ├── POST Create Review - OK
│     └── DELETE Review - OK
│
└── Reviews - Tests ERROR
├── GET Reviews - Not Found
└── POST Review - Invalid Rating

--------------------------------------------------------------------
3. TESTS PARA APARTMENTS
--------------------------------------------------------------------

3.1 GET /api/apartments (OK)

Nombre del test:
GET All Apartments - Status 200

Código:
pm.test("GET All Apartments - Status 200", function () {
pm.response.to.have.status(200);
});

Nombre del test:
GET All Apartments - Response is JSON

Código:
pm.test("GET All Apartments - Response is JSON", function () {
pm.response.to.be.json;
});

Nombre del test:
GET All Apartments - Response is an array

Código:
pm.test("GET All Apartments - Response is an array", function () {
const json = pm.response.json();
pm.expect(json).to.be.an("array");
});

Nombre del test:
GET All Apartments - At least one apartment exists

Código:
pm.test("GET All Apartments - At least one apartment exists", function () {
const json = pm.response.json();
pm.expect(json.length).to.be.above(0);
});

Nombre del test:
GET All Apartments - Apartment object has required fields

Código:
pm.test("GET All Apartments - Apartment object has required fields", function () {
const json = pm.response.json();
const apt = json[0];
pm.expect(apt).to.have.property("id");
pm.expect(apt).to.have.property("price");
pm.expect(apt).to.have.property("area");
});

--------------------------------------------------------------------
3.2 GET /api/apartments/{id} (OK)

Nombre del test:
GET Apartment by ID - Status 200

Código:
pm.test("GET Apartment by ID - Status 200", function () {
pm.response.to.have.status(200);
});

Nombre del test:
GET Apartment by ID - Has required fields

Código:
pm.test("GET Apartment by ID - Has required fields", function () {
const apt = pm.response.json();
pm.expect(apt).to.have.property("id");
pm.expect(apt).to.have.property("price");
});

--------------------------------------------------------------------
3.3 GET /api/apartments/{id} (ERROR)

Nombre del test:
GET Apartment by ID - Not Found

Código:
pm.test("GET Apartment by ID - Not Found", function () {
pm.response.to.have.status(404);
});

--------------------------------------------------------------------
3.4 POST /api/apartments (OK)

Nombre del test:
POST Create Apartment - Status 201

Código:
pm.test("POST Create Apartment - Status 201", function () {
pm.response.to.have.status(201);
});

Nombre del test:
POST Create Apartment - Has ID

Código:
pm.test("POST Create Apartment - Has ID", function () {
const apt = pm.response.json();
pm.expect(apt).to.have.property("id");
});

Nombre del test:
POST Create Apartment - Save ID

Código:
pm.test("POST Create Apartment - Save ID", function () {
const apt = pm.response.json();
pm.collectionVariables.set("createdApartmentId", apt.id);
});

--------------------------------------------------------------------
3.5 POST /api/apartments (ERROR)

Nombre del test:
POST Create Apartment - Bad Request

Código:
pm.test("POST Create Apartment - Bad Request", function () {
pm.response.to.have.status(400);
});

--------------------------------------------------------------------
3.6 PUT /api/apartments/{id} (OK)

Nombre del test:
PUT Update Apartment - Status 200

Código:
pm.test("PUT Update Apartment - Status 200", function () {
pm.response.to.have.status(200);
});

--------------------------------------------------------------------
3.7 DELETE /api/apartments/{id} (OK)

Nombre del test:
DELETE Apartment - Status 204

Código:
pm.test("DELETE Apartment - Status 204", function () {
pm.response.to.have.status(204);
});

--------------------------------------------------------------------
3.8 DELETE /api/apartments/{id} (ERROR)

Nombre del test:
DELETE Apartment - Not Found

Código:
pm.test("DELETE Apartment - Not Found", function () {
pm.response.to.have.status(404);
});

--------------------------------------------------------------------
4. TESTS PARA REVIEWS
--------------------------------------------------------------------

4.1 GET /api/apartments/{id}/reviews (OK)

Nombre del test:
GET Reviews - Status 200

Código:
pm.test("GET Reviews - Status 200", function () {
pm.response.to.have.status(200);
});

4.2 GET /api/apartments/{id}/reviews (ERROR)

Nombre del test:
GET Reviews - Not Found

Código:
pm.test("GET Reviews - Not Found", function () {
pm.response.to.have.status(404);
});

--------------------------------------------------------------------
4.3 POST /api/apartments/{id}/reviews (OK)

Nombre del test:
POST Create Review - Status 201

Código:
pm.test("POST Create Review - Status 201", function () {
pm.response.to.have.status(201);
});

Nombre del test:
POST Create Review - Save Review ID

Código:
pm.test("POST Create Review - Save Review ID", function () {
const review = pm.response.json();
pm.collectionVariables.set("createdReviewId", review.id);
});

--------------------------------------------------------------------
4.4 POST /api/apartments/{id}/reviews (ERROR)

Nombre del test:
POST Review - Invalid Rating

Código:
pm.test("POST Review - Invalid Rating", function () {
pm.response.to.have.status(400);
});

--------------------------------------------------------------------
4.5 DELETE /api/reviews/{reviewId} (OK)

Nombre del test:
DELETE Review - Status 204

Código:
pm.test("DELETE Review - Status 204", function () {
pm.response.to.have.status(204);
});

--------------------------------------------------------------------
5. CÓMO EJECUTAR LOS TESTS EN POSTMAN
--------------------------------------------------------------------

1. Abrir la colección Apartment Predictor API.
2. Seleccionar la carpeta que quieras ejecutar.
3. Pulsar Run.
4. En el panel Collection Runner, pulsar Run.
5. Ver los resultados:
    - Tests pasados
    - Tests fallados
    - Tiempos de respuesta

--------------------------------------------------------------------
6. INTERPRETACIÓN DE RESULTADOS
--------------------------------------------------------------------

✔ Verde: el test ha pasado correctamente  
❌ Rojo: el test ha fallado  
⚠ Puede indicar:
- Error en la API
- Error en el test
- Datos incorrectos
- ID inexistente

--------------------------------------------------------------------
7. CONCLUSIÓN
--------------------------------------------------------------------

Este documento recoge todos los tests necesarios para validar la API Apartment Predictor. Los tests están organizados por endpoint, con versiones OK y ERROR, y pueden ejecutarse individualmente o en conjunto desde Postman.

FIN DEL DOCUMENTO
