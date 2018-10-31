package com.swalab.backend.model;

public class Bom {

    private Part part;
    private int quantity;
    private String unit;

    public Bom(Part part, int quantity, String unit) {
        this.part = part;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
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
