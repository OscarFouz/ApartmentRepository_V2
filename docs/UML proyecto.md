# UML – Modelo de Entidades

## Diagrama de clases (ASCII UML)

                          ┌──────────────────────────┐
                          │       Property (abstract) │
                          ├──────────────────────────┤
                          │ - id : String            │
                          │ - area : int             │
                          │ - locationRating : int   │
                          ├──────────────────────────┤
                          │ + getArea()              │
                          │ + getLocationRating()    │
                          │ + calculatePrice() : dbl │
                          └───────────┬──────────────┘
                                      │
                ┌─────────────────────┼───────────────────────────┐
                │                     │                           │
                ▼                     ▼                           ▼

      ┌──────────────────────┐   ┌──────────────────────┐   ┌────────────────────────┐
      │      Apartment        │   │        House          │   │       Duplex           │
      ├──────────────────────┤   ├──────────────────────┤   ├────────────────────────┤
      │ - id : String        │   │ - garageQty : int     │   │ - balcony : String     │
      │ - price : Long       │   │ - roofType : String   │   │ - elevator : boolean   │
      │ - bedrooms : int     │   │ - garden : String     │   │ - hasSeparateUtilities │
      │ - bathrooms : int    │   ├──────────────────────┤   ├────────────────────────┤
      │ - stories : int      │   │ + getters/setters     │   │ + getters/setters      │
      │ - mainroad : String  │   │ + toString()          │   │ + toString()           │
      │ - guestroom : String │   └──────────────────────┘   └────────────────────────┘
      │ - basement : String  │
      │ - hotwaterheating    │
      │ - airconditioning    │
      │ - parking : int      │
      │ - prefarea : String  │
      │ - furnishingstatus   │
      ├──────────────────────┤
      │ + calculatePrice()   │
      │ + addReview()        │
      │ + removeReview()     │
      └───────────┬──────────┘
                  │
                  ▼

      ┌──────────────────────────────┐
      │          TownHouse           │
      ├──────────────────────────────┤
      │ - hasHomeownersAssociation   │
      │ - hoaMonthlyFee : double     │
      ├──────────────────────────────┤
      │ + getters/setters            │
      │ + calculatePrice()           │
      └──────────────────────────────┘


## Relación Apartment ↔ Review

Apartment "1" ──────────── "0..*" Review  
(OneToMany / ManyToOne)

ASCII UML:

      ┌──────────────┐        1        ┌──────────────┐
      │  Apartment    │----------------│    Review     │
      └──────────────┘     0..*        └──────────────┘
             ▲                                  
             │ has many                         
             │                                    
             └───────────────────────────────────────┐
                                                     │
                                                     ▼

## Entidad Owner (independiente)

      ┌──────────────────────────┐
      │          Owner           │
      ├──────────────────────────┤
      │ - id : String            │
      │ - name : String          │
      │ - email : String         │
      │ - age : int              │
      │ - isActive : boolean     │
      │ - isBusiness : boolean   │
      │ - idLegalOwner : String  │
      │ - registrationDate       │
      │ - qtyDaysAsOwner : int   │
      ├──────────────────────────┤
      │ + getters/setters        │
      │ + toString()             │
      └──────────────────────────┘

