package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.model.AvailablePart;
import com.swalab.frontend.model.PartWithQuantity;
import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.WarehousePartAndOrder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Date;

public class WarehousePartAndOrderEditingSettings implements IEditorSettings<WarehousePartAndOrder> {

    private final TextField _descriptionField;
    private final TextField _orderDateField;
    private final ComboBox<Status> _statusComboBox;
    private final TextField _idField;
    private final TextField _orderNumberField;
    private final ComboBox<AvailablePart> _partComboBox;
    private final TextField _quantityField;
    private final TextField _unitField;

    public WarehousePartAndOrderEditingSettings(ComboBox<AvailablePart> partComboBox, TextField quantityField, TextField unitField, TextField descriptionField, TextField orderDateField, ComboBox<Status> statusComboBox, TextField orderNumberField, TextField idField) {
        _descriptionField = descriptionField;
        _orderDateField = orderDateField;
        _statusComboBox = statusComboBox;
        _idField = idField;
        _orderNumberField = orderNumberField;
        _partComboBox = partComboBox;
        _quantityField = quantityField;
        _unitField = unitField;
    }

    @Override
    public WarehousePartAndOrder createObject() {
        PartWithQuantity partWithQuantity = new PartWithQuantity();
        partWithQuantity.setQuantity(Integer.parseInt(_quantityField.getText()));
        partWithQuantity.setUnit(_unitField.getText());
        AvailablePart part = _partComboBox.getValue();
        partWithQuantity.setAvailablePart(part);

        WarehousePartAndOrder partAndOrder = new WarehousePartAndOrder();
        partAndOrder.setPart(partWithQuantity);
        partAndOrder.setDescription(_descriptionField.getText());
        partAndOrder.setStatus(_statusComboBox.getValue());
        partAndOrder.setOrderDate(new Date());// TODO parse value out of the text field
        return partAndOrder;
    }

    @Override
    public void setDefaultValues(WarehousePartAndOrder content) {
        _descriptionField.setText(content == null ? null : content.getDescription());
        _orderDateField.setText(content == null ? new Date().toGMTString() : content.getOrderDate().toGMTString());
        _statusComboBox.getSelectionModel().select(content == null ? null : content.getStatus());
        _idField.setText(content == null ? null : content.getID() + "");
        _orderNumberField.setText(content == null ? null : content.getOrderNumber() + "");
        PartWithQuantity part = content == null ? null : content.getPart();
        if (part == null) {
            _partComboBox.getSelectionModel().select(null);
            _quantityField.setText(null);
            _unitField.setText(null);
        } else {
            _partComboBox.getSelectionModel().select(part.getAvailablePart());
            _quantityField.setText(part.getQuantity() + "");
            _unitField.setText(part.getUnit());
        }

    }

    @Override
    public boolean canObjectBeCreated() {
        return true;
    }

    @Override
    public void updateContent(WarehousePartAndOrder partAndOrder) {
        partAndOrder.setDescription(_descriptionField.getText());
        partAndOrder.setStatus(_statusComboBox.getSelectionModel().getSelectedItem());
    }
}
