package com.swalab.frontend.model;

/**
 * Build of material: Item on lists containing a part, the quantity and an unit
 */
public class Bom {

    private Long id;
    private AvailablePart availablePart;
    private int quantity;
    private String unit;

    public Bom() {
    }

    public Bom(AvailablePart availablePart, int quantity, String unit) {
        this.availablePart = availablePart;
        this.quantity = quantity;
        this.unit = unit;
    }

    public AvailablePart getAvailablePart() {
        return availablePart;
    }

    public void setAvailablePart(AvailablePart availablePart) {
        this.availablePart = availablePart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
