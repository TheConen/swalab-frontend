package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.gui.composites.PartsAndServiceEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.model.Appointment;
import com.swalab.frontend.model.Customer;
import com.swalab.frontend.model.PartWithQuantity;
import com.swalab.frontend.model.Product;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class AppointmentEditingSettings implements IEditorSettings<Appointment> {

    private final TextField _descriptionField;
    private final TextField _creationDateField;
    private final TextField _plannedStartField;
    private final TextField _plannedEndField;
    private final TextField _idField;
    private final StatusCombobox _statusBox;
    private final ComboBox<Customer> _customerComboBox;
    private final ComboBox<Product> _productComboBox;
    private final ListView<PartWithQuantity> _plannedPartsAndServicesList;
    private final ListView<PartWithQuantity> _usedPartsAndServicesList;
    private final PartsAndServiceEditor _partsAndServiceEditor;

    public AppointmentEditingSettings(TextField descriptionField, TextField creationDateField, StatusCombobox statusComboBox, TextField plannedStartField, TextField plannedEndField, ComboBox<Customer> customerComboBox, ComboBox<Product> productComboBox, ListView<PartWithQuantity> plannedPartsAndServicesList, ListView<PartWithQuantity> usedPartsAndServicesList, PartsAndServiceEditor partsAndServiceEditor, TextField idField) {
        _descriptionField = descriptionField;
        _creationDateField = creationDateField;
        _plannedStartField = plannedStartField;
        _plannedEndField = plannedEndField;
        _idField=idField;
        _statusBox=statusComboBox;
        _customerComboBox =customerComboBox;
        _productComboBox=productComboBox;
        _plannedPartsAndServicesList=plannedPartsAndServicesList;
        _usedPartsAndServicesList=usedPartsAndServicesList;
        _partsAndServiceEditor=partsAndServiceEditor;
    }

    @Override
    public Appointment createObject() {
        List<PartWithQuantity> partsWithQuantity = _partsAndServiceEditor.getPartsWithQuantity();
        // TODO create correct object
        return new Appointment();

    }

    @Override
    public void setDefaultValues(Appointment content) {
        _descriptionField.setText(content == null ? null : content.getDescription());
        _creationDateField.setText(content == null ? null : content.getCreationDate().toGMTString());
        _plannedStartField.setText(content == null ? null : content.getPlannedDateTimeFrom().toGMTString());
        _plannedEndField.setText(content == null ? null : content.getPlannedDateTimeTo().toGMTString());
        _idField.setText(content==null?null:content.getID()+"");
        _statusBox.getSelectionModel().select(content==null?null:content.getStatus());
        _customerComboBox.getSelectionModel().select(null); // TODO take customer list from technician
        _productComboBox.getSelectionModel().select(null); // TODO take product list from technician
        _plannedPartsAndServicesList.setItems(content==null?null:content.getObservablePlannedPartsAndServices());
        _usedPartsAndServicesList.setItems(content==null?null:content.getObservableUsedPartsAndServices());
    }

    @Override
    public boolean canObjectBeCreated() {
        // TODO write correct check
        return true;
    }
}
