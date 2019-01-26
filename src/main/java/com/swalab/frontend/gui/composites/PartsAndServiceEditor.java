package com.swalab.frontend.gui.composites;

import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.converter.AvailablePartConverter;
import com.swalab.frontend.model.AvailablePart;
import com.swalab.frontend.model.PartWithQuantity;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class PartsAndServiceEditor extends GridPane {

    public SynchController _syncController;
    private List<Node> _currentAdditionalElements;

    public PartsAndServiceEditor() {
        setPadding(new Insets(5, 5, 5, 5));
        setVgap(3);
        setHgap(3);
        Label typeLabel = new Label("Type");
        Label quantityLabel = new Label("Quantity");
        Label utilLabel = new Label("Util");
        Button addButton = new Button("Add");
        _syncController = null;
        addButton.setOnAction(ae -> {
            insertNewEntry(null);
        });

        addRow(0, typeLabel, quantityLabel, utilLabel, addButton);
        _currentAdditionalElements = new ArrayList<>();
    }

    private void insertNewEntry(PartWithQuantity part) {
        ComboBox<AvailablePart> typeCombo = new ComboBox<>();

        Callback<ListView<AvailablePart>, ListCell<AvailablePart>> cellFactory = new Callback<ListView<AvailablePart>, ListCell<AvailablePart>>() {
            @Override
            public ListCell<AvailablePart> call(ListView<AvailablePart> l) {
                return new ListCell<AvailablePart>() {
                    @Override
                    protected void updateItem(AvailablePart item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                           setText("");
                        } else {
                            setText(item.getName());
                        }
                    }
                } ;
            }
        };

// Just set the button cell here:
        typeCombo.setButtonCell(cellFactory.call(null));
        typeCombo.setCellFactory(cellFactory);
        typeCombo.setConverter(new AvailablePartConverter(typeCombo));


        typeCombo.setItems(_syncController.getAvailableParts());
        TextField quantityField = new TextField();
        TextField unitField = new TextField();
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> getChildren().removeAll(typeCombo, quantityField, unitField, removeButton));
        addRow(getRowCount(), typeCombo, quantityField, unitField, removeButton);

        if (part != null) {
            typeCombo.getSelectionModel().select(part.getAvailablePart());
            quantityField.setText(part.getQuantity() + "");
            unitField.setText(part.getUnit());
        }

        _currentAdditionalElements.add(typeCombo);
        _currentAdditionalElements.add(quantityField);
        _currentAdditionalElements.add(unitField);
        _currentAdditionalElements.add(removeButton);
    }

    public List<PartWithQuantity> getPartsWithQuantity() {
        List<PartWithQuantity> list = new ArrayList<>(getRowCount());
        ObservableList<Node> nodes = getChildrenUnmodifiable();
        for (int i = 1; i < getRowCount(); i++) {
            PartWithQuantity partWithQuantity = new PartWithQuantity();
            ComboBox<AvailablePart> partComboBox = (ComboBox<AvailablePart>) (nodes.get(i * getColumnCount()));
            partWithQuantity.setAvailablePart(partComboBox.getValue());
            TextField quantityField = (TextField) nodes.get(i * getColumnCount() + 1);
            partWithQuantity.setQuantity(Integer.parseInt(quantityField.getText()));
            TextField unitField = (TextField) nodes.get(i * getColumnCount() + 2);
            partWithQuantity.setUnit(unitField.getText());
            list.add(partWithQuantity);
        }
        return list;
    }

    public void setSyncController(SynchController syncController) {
        _syncController = syncController;
    }

    public void initialWithContent(List<PartWithQuantity> partsAndServicesList) {
        if (partsAndServicesList == null) return;
        for (PartWithQuantity partWithQuantity : partsAndServicesList) {
            insertNewEntry(partWithQuantity);
        }
    }

    public void clear() {
        ObservableList<Node> children = getChildren();
        children.removeAll(_currentAdditionalElements);
        _currentAdditionalElements.clear();
    }
}
