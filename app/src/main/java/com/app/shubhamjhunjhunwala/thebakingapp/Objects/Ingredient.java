package com.app.shubhamjhunjhunwala.thebakingapp.Objects;

import org.parceler.Parcel;

/**
 * Created by shubham on 14/03/18.
 */

@Parcel
public class Ingredient {
    public String quantity;
    public String measurement;
    public String ingredient;

    public Ingredient() {}

    public Ingredient(String quantity, String measurement, String ingredient) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
