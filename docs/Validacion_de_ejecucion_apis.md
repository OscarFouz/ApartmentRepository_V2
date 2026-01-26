VALIDACIÓN DE EJECUCIÓN DEL PROYECTO APARTMENT PREDICTOR

Este documento describe los pasos necesarios para ejecutar correctamente el proyecto Spring Boot y validar que las APIs funcionan según lo esperado.

--------------------------------------------------------------------
1. VERIFICAR QUE EL PROYECTO COMPILA CORRECTAMENTE
--------------------------------------------------------------------

Antes de ejecutar la aplicación, es necesario asegurarse de que el proyecto no contiene errores de compilación.

Pasos:

1. Abrir el proyecto en IntelliJ IDEA o Eclipse.
2. Ejecutar:
    - Maven → Reload Project
    - Maven → Lifecycle → clean
    - Maven → Lifecycle → install
3. Confirmar que no aparecen errores en la consola.

Si la compilación finaliza correctamente, el proyecto está listo para ejecutarse.

--------------------------------------------------------------------
2. CONFIRMAR LA CLASE PRINCIPAL DE SPRING BOOT
--------------------------------------------------------------------

El proyecto debe contener la clase:

ApartmentPredictorApplication.java

Con el siguiente contenido mínimo:

@SpringBootApplication
public class ApartmentPredictorApplication {
public static void main(String[] args) {
SpringApplication.run(ApartmentPredictorApplication.class, args);
}
}

Esta clase es el punto de entrada de la aplicación.

--------------------------------------------------------------------
3. EJECUTAR LA APLICACIÓN
--------------------------------------------------------------------

OPCIÓN 1: Desde IntelliJ IDEA
- Abrir ApartmentPredictorApplication.java
- Pulsar el botón verde ▶️ junto al método main

OPCIÓN 2: Desde terminal
Ejecutar:
mvn spring-boot:run

RESULTADO ESPERADO:
En la consola debe aparecer:

Tomcat started on port(s): 8080
Aplicación iniciada. Servidor activo.

Esto confirma que el servidor está funcionando.

--------------------------------------------------------------------
4. VALIDAR LA BASE DE DATOS H2
--------------------------------------------------------------------

Abrir en el navegador:
http://localhost:8080/h2-console

Configurar:

JDBC URL: jdbc:h2:file:./db/apartmentpredictordb
User: oscar
Password: 1234

Si la conexión es exitosa, la base de datos está funcionando correctamente.

--------------------------------------------------------------------
5. VALIDAR LA CARGA AUTOMÁTICA DE DATOS DESDE CSV
--------------------------------------------------------------------

Al iniciar la aplicación, deben aparecer mensajes como:

SI LA BASE DE DATOS ESTÁ VACÍA:
Base de datos vacía. Insertando apartamentos iniciales...
Importación de apartamentos completada.
Base de datos vacía. Insertando reviews iniciales...
Importación de reviews completada.

SI YA EXISTÍAN DATOS:
La base de datos ya tiene apartamentos. No se insertan.
La base de datos ya tiene reviews. No se insertan.

Esto confirma que los servicios de carga (LoadInitialDataService y LoadReviewDataService) funcionan correctamente.

--------------------------------------------------------------------
6. VALIDAR FUNCIONAMIENTO DE LAS APIS
--------------------------------------------------------------------

Una vez arrancado el servidor, las APIs deben responder correctamente.

6.1. Obtener todos los apartamentos:
GET http://localhost:8080/api/apartments

6.2. Obtener un apartamento por ID:
GET http://localhost:8080/api/apartments/{id}
GET http://localhost:8080/api/apartments/741d397f-2c7f-4809-91d5-337559d0a167

6.3. Crear un apartamento:
POST http://localhost:8080/api/apartments
Content-Type: application/json

6.4. Actualizar un apartamento:
PUT http://localhost:8080/api/apartments/{id}

6.5. Eliminar un apartamento:
DELETE http://localhost:8080/api/apartments/{id}

6.6. Reviews:
GET    /api/apartments/{id}/reviews
POST   /api/apartments/{id}/reviews
DELETE /api/reviews/{reviewId}

Si todas estas rutas responden correctamente, las APIs están funcionando.

--------------------------------------------------------------------
7. PROBLEMAS COMUNES Y SOLUCIONES
--------------------------------------------------------------------

7.1. Error: “class X is public, should be declared in a file named X.java”
Solución:
Asegurarse de que el archivo se llama exactamente igual que la clase pública.
Ejemplo:
Clase: ApartmentController
Archivo: ApartmentController.java

7.2. Error: puerto 8080 ocupado
Cambiar el puerto en application.properties:
server.port=9090

7.3. Error al cargar CSV
Verificar:
- Rutas configuradas en application.properties
- Archivos CSV presentes en /db/

--------------------------------------------------------------------
8. CONCLUSIÓN
--------------------------------------------------------------------

Si:
- El proyecto compila
- Spring Boot arranca sin errores
- H2 funciona
- Los CSV se cargan correctamente
- Las APIs responden

Entonces la ejecución del proyecto Apartment Predictor es correcta y está lista para pruebas o despliegue.

