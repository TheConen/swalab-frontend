package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.WarehousePartAndOrder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WarehousePartAndOrderEditingSettings implements IEditorSettings<WarehousePartAndOrder> {

    private final TextField _descriptionField;
    private final Label _orderDateField;
    private final ComboBox<Status> _statusComboBox;
    private final TextField _idField;

    public WarehousePartAndOrderEditingSettings(TextField descriptionField, Label orderDateField, ComboBox<Status> statusComboBox, TextField idField) {
        _descriptionField = descriptionField;
        _orderDateField = orderDateField;
        _statusComboBox = statusComboBox;
        _idField=idField;
    }

    @Override
    public WarehousePartAndOrder createObject() {
        // TODO create the object correctly
        return new WarehousePartAndOrder();
    }

    @Override
    public void setDefaultValues(WarehousePartAndOrder content) {
        _descriptionField.setText(content == null ? null : content.getDescription());
        _orderDateField.setText(content == null ? null : content.getOrderDate().toGMTString());
        _statusComboBox.getSelectionModel().select(content == null ? null : content.getStatus());
    }

    @Override
    public boolean canObjectBeCreated() {
        return true;
    }
}
