## Diagrama UML de ÀpartmentPredictor Property → Apartment → Subclases


-------------------------------------------------------------

                     ┌──────────────────────────────┐
                     │      Property (abstract)      │
                     │──────────────────────────────│
                     │ - id : String                │
                     │ - area : int                 │
                     │ - locationRating : int       │
                     │──────────────────────────────│
                     │ + getArea()                  │
                     │ + getLocationRating()        │
                     │ + setArea()                  │
                     │ + setLocationRating()        │
                     │ + calculatePrice() : double  │ (abstract)
                     └───────────────▲──────────────┘
                                     │
                                     │  @Inheritance(JOINED)
                                     │
                     ┌──────────────────────────────────────────┐
                     │               Apartment                   │
                     │──────────────────────────────────────────│
                     │ - id : String                            │
                     │ - price : Long                           │
                     │ - bedrooms : Integer                     │
                     │ - bathrooms : Integer                    │
                     │ - stories : Integer                      │
                     │ - mainroad : String                      │
                     │ - guestroom : String                     │
                     │ - basement : String                      │
                     │ - hotwaterheating : String               │
                     │ - airconditioning : String               │
                     │ - parking : Integer                      │
                     │ - prefarea : String                      │
                     │ - furnishingstatus : String              │
                     │──────────────────────────────────────────│
                     │ + calculatePrice() : double              │
                     │ + addReview()                            │
                     │ + removeReview()                         │
                     └───────────────┬───────────────┬─────────┘
                                     │               │
                                     │               │
                                     │               │
         ┌───────────────────────────┘               └───────────────────────────┐
         │                                                                       │
         ▼                                                                       ▼
┌──────────────────────────────┐                                ┌──────────────────────────────┐
│            House             │                                │            Duplex            │
│──────────────────────────────│                                │──────────────────────────────│
│ - garageQty : int            │                                │ - balcony : String           │
│ - roofType : String          │                                │ - elevator : boolean         │
│ - garden : String            │                                │ - hasSeparateUtilities : bool│
│──────────────────────────────│                                │──────────────────────────────│
│ + calculatePrice() : double  │                                │ + calculatePrice() : double  │
└──────────────────────────────┘                                └──────────────────────────────┘

         ▼
┌──────────────────────────────┐
│          TownHouse           │
│──────────────────────────────│
│ - hasHomeownersAssociation : boolean │
│ - hoaMonthlyFee : double            │
│──────────────────────────────│
│ + calculatePrice() : double         │
└──────────────────────────────┘

-------------------------------------------------------------


## Qué representa este diagrama

### ✔ Property
- Clase abstracta  
- Tabla raíz en la estrategia JOINED  
- Contiene campos comunes  

### ✔ Apartment
- Clase concreta  
- Extiende Property  
- Tiene su propia tabla  
- Tiene reviews  

### ✔ Subclases (House, Duplex, TownHouse)
- Cada una tiene su propia tabla  
- Cada tabla se une con APARTMENT por ID  
- Hibernate las reconoce gracias a @Entity + @DiscriminatorValue  

---

## ¿Qué te permite este diseño?

- Herencia real en la base de datos  
- Consultas polimórficas (findAll() devuelve todos los tipos)  
- Cada tipo tiene sus propios atributos  
- Hibernate gestiona las uniones automáticamente  
