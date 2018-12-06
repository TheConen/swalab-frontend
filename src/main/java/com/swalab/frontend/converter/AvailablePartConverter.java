package com.swalab.frontend.converter;

import com.swalab.frontend.model.AvailablePart;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.Optional;

public class AvailablePartConverter extends StringConverter<AvailablePart> {

    private final ComboBox<AvailablePart> _partComboBox;

    public AvailablePartConverter(ComboBox<AvailablePart> comboBox) {
        _partComboBox = comboBox;
    }

    @Override
    public String toString(AvailablePart availablePart) {
        if (availablePart == null) {
            return "No part available";
        }
        return availablePart.getName();
    }

    @Override
    public AvailablePart fromString(String s) {
        Optional<AvailablePart> availablePartOptional = _partComboBox.getItems().stream().filter(p -> p.getName().equals(s)).findFirst();
        if (availablePartOptional.isPresent()) {
            return availablePartOptional.get();
        }
        throw new IllegalArgumentException("Element not available");
    }
}



