package com.swalab.frontend.model;

import com.swalab.frontend.api.INamedArtefact;
import com.swalab.frontend.util.IdGenerator;

/**
 * Build of material: Item on lists containing a part, the quantity and an unit
 */
public class PartWithQuantity implements INamedArtefact {

    private Long id = IdGenerator.getNewId();
    private AvailablePart availablePart;
    private int quantity;
    private String unit;

    public PartWithQuantity() {
    }

    public PartWithQuantity(AvailablePart availablePart, int quantity, String unit) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return new StringBuilder(getAvailablePart().getName()).append(" ( ").append(getQuantity()).append(' ').append(getUnit()).append(" )").toString();
    }
}
