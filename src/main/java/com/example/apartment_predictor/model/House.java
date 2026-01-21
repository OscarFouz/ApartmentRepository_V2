package com.example.apartment_predictor.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;

@Entity
@DiscriminatorValue("HOUSE")
public class House extends Apartment {

    private int garageQty;
    private String roofType;
    private String garden;

    public House() {
        this.id = UUID.randomUUID().toString();
    }

    public House(int garageQty, String roofType, String garden) {
        this.id = UUID.randomUUID().toString();
        this.garageQty = garageQty;
        this.roofType = roofType;
        this.garden = garden;
    }

    public int getGarageQty() {
        return garageQty;
    }

    public void setGarageQty(int garageQty) {
        this.garageQty = garageQty;
    }

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", garageQty=" + garageQty +
                ", roofType='" + roofType + '\'' +
                ", garden='" + garden + '\'' +
                '}';
    }
}
