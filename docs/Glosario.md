# Explicación de anotaciones

## @Inheritance(strategy = InheritanceType.JOINED)
Define la estrategia de herencia en JPA.  
`JOINED` crea una tabla por cada clase hija y una tabla padre con los atributos comunes.

## @DiscriminatorColumn(name = "property_type")
Crea una columna en la tabla padre para identificar el tipo de entidad hija.

## @DiscriminatorValue("APARTMENT")
Valor que se almacenará en `property_type` cuando la entidad sea de tipo Apartment.

## @Id
Indica que el campo es la clave primaria de la entidad.

## @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
Define una relación uno-a-muchos:
- `mappedBy`: el dueño de la relación está en la entidad hija.
- `cascade`: operaciones propagadas.
- `fetch`: carga inmediata de los elementos relacionados.

## @SpringBootApplication
Anotación principal de Spring Boot que habilita:
- Configuración
- Auto-configuración
- Escaneo de componentes

## @Value("${app.csv.path}")
Inyecta un valor desde el archivo de configuración (`application.properties` o `.yml`).

private String csvPath;