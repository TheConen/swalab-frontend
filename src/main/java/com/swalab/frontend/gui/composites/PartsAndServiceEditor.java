package com.swalab.frontend.gui.composites;

import com.swalab.frontend.model.AvailablePart;
import com.swalab.frontend.model.PartWithQuantity;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class PartsAndServiceEditor extends GridPane {

    public PartsAndServiceEditor() {
        Label typeLabel = new Label("Type");
        Label quantityLabel = new Label("Quantity");
        Label utilLabel = new Label("Util");
        Button addButton = new Button("Add");
        addButton.setOnAction(ae -> {
            ComboBox<AvailablePart> typeCombo = new ComboBox<>();
            TextField quantityField = new TextField();
            TextField unitField = new TextField();
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(e -> getChildren().removeAll(typeCombo, quantityField, unitField, removeButton));
            addRow(getRowCount(), typeCombo, quantityField, unitField, removeButton);
        });

        addRow(0, typeLabel, quantityLabel, utilLabel, addButton);
    }

    public List<PartWithQuantity> getPartsWithQuantity(){
        List<PartWithQuantity> list=new ArrayList<>(getRowCount());
        ObservableList<Node> nodes = getChildrenUnmodifiable();
        for(int i=1;i<getRowCount();i++){
            PartWithQuantity partWithQuantity = new PartWithQuantity();
            ComboBox<AvailablePart> partComboBox=(ComboBox<AvailablePart>)(nodes.get(i*getColumnCount()));
            partWithQuantity.setAvailablePart(partComboBox.getValue());
            TextField quantityField= (TextField) nodes.get(i*getColumnCount()+1);
            partWithQuantity.setQuantity(Integer.parseInt(quantityField.getText()));
            TextField unitField= (TextField) nodes.get(i*getColumnCount()+2);
            partWithQuantity.setUnit(unitField.getText());
            list.add(partWithQuantity);
        }
        return list;
    }
}
