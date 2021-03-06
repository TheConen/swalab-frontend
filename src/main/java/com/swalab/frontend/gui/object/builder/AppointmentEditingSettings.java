package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.converter.DateConverter;
import com.swalab.frontend.gui.composites.PartsAndServiceEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.model.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Calendar;
import java.util.Date;
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
    private final PartsAndServiceEditor _usedPartsAndServiceEditor;
    private final DateConverter _dateConverter;
    private final PartsAndServiceEditor _plannedPartsAndServiceEditor;
    private final TextField _realEndField;
    private final TextField _realStartField;

    public AppointmentEditingSettings(TextField descriptionField, TextField creationDateField, StatusCombobox statusComboBox, TextField plannedStartField, TextField plannedEndField,TextField realStartField,TextField realEndField, ComboBox<Customer> customerComboBox, ComboBox<Product> productComboBox, ListView<PartWithQuantity> plannedPartsAndServicesList, ListView<PartWithQuantity> usedPartsAndServicesList, PartsAndServiceEditor usedPartsAndServiceEditor, TextField idField, PartsAndServiceEditor plannedPartsAndServiceEditor) {
        _descriptionField = descriptionField;
        _creationDateField = creationDateField;
        _plannedStartField = plannedStartField;
        _plannedEndField = plannedEndField;
        _realStartField=realStartField;
        _realEndField=realEndField;
        _idField = idField;
        _statusBox = statusComboBox;
        _customerComboBox = customerComboBox;
        _productComboBox = productComboBox;
        _plannedPartsAndServicesList = plannedPartsAndServicesList;
        _usedPartsAndServicesList = usedPartsAndServicesList;
        _usedPartsAndServiceEditor = usedPartsAndServiceEditor;
        _plannedPartsAndServiceEditor = plannedPartsAndServiceEditor;

        _dateConverter = new DateConverter();
    }

    @Override
    public Appointment createObject() {
        List<PartWithQuantity> usedPartsWithQuantity = _usedPartsAndServiceEditor.getPartsWithQuantity();
        List<PartWithQuantity> plannedPartsWithQuantity = _plannedPartsAndServiceEditor.getPartsWithQuantity();
        Customer customer = _customerComboBox.getSelectionModel().getSelectedItem();
        String description = _descriptionField.getText();
        Product product = _productComboBox.getSelectionModel().getSelectedItem();
        Date creationDate = _dateConverter.fromString(_creationDateField.getText());
        Status status = _statusBox.getSelectionModel().getSelectedItem();
        Date plannedStart = _dateConverter.fromString(_plannedStartField.getText());
        Date plannedEnd = _dateConverter.fromString(_plannedEndField.getText());
        Date realStart=_dateConverter.fromString(_plannedStartField.getText());
        Date realEnd=_dateConverter.fromString(_plannedEndField.getText());
        return new Appointment(customer, description, product, creationDate, status, plannedPartsWithQuantity, plannedStart, plannedEnd, usedPartsWithQuantity, realStart, realEnd);

    }

    @Override
    public void setDefaultValues(Appointment content) {
        _descriptionField.setText(content == null ? null : content.getDescription());
        _creationDateField.setText(_dateConverter.toString(content == null ? Calendar.getInstance().getTime() : content.getCreationDate()));
        _plannedStartField.setText(_dateConverter.toString(content == null ? Calendar.getInstance().getTime() : content.getPlannedDateTimeFrom()));
        _plannedEndField.setText(_dateConverter.toString(content == null ? Calendar.getInstance().getTime() : content.getPlannedDateTimeTo()));
        _realStartField.setText(_dateConverter.toString(content==null?Calendar.getInstance().getTime():content.getRealDateFrom()));
        _realEndField.setText(_dateConverter.toString(content==null?Calendar.getInstance().getTime():content.getRealDateFrom()));

        _idField.setText(content == null ? null : (content.getID() == null) ? "" : content.getID() + "");
        _statusBox.getSelectionModel().select(content == null ? null : content.getStatus());
        _customerComboBox.getSelectionModel().select(content == null ? null : content.getCustomer());
        _productComboBox.getSelectionModel().select(content == null ? null : content.getProduct());
        _plannedPartsAndServicesList.setItems(content == null ? null : content.getObservablePlannedPartsAndServices());
        _usedPartsAndServicesList.setItems(content == null ? null : content.getObservableUsedPartsAndServices());

        _plannedPartsAndServiceEditor.initialWithContent(content == null ? null : content.getPlannedPartsAndServices());
        _usedPartsAndServiceEditor.initialWithContent(content == null ? null : content.getUsedPartsAndServices());
    }

    @Override
    public boolean canObjectBeCreated() {
        // TODO write correct check
        return true;
    }

    @Override
    public void updateContent(Appointment appointment) {
        List<PartWithQuantity> usedPartsWithQuantity = _usedPartsAndServiceEditor.getPartsWithQuantity();
        List<PartWithQuantity> plannedPartsWithQuantity = _plannedPartsAndServiceEditor.getPartsWithQuantity();
        Customer customer = _customerComboBox.getSelectionModel().getSelectedItem();
        String description = _descriptionField.getText();
        Product product = _productComboBox.getSelectionModel().getSelectedItem();
        Date creationDate = _dateConverter.fromString(_creationDateField.getText());
        Status status = _statusBox.getSelectionModel().getSelectedItem();
        Date plannedStart = _dateConverter.fromString(_plannedStartField.getText());
        Date plannedEnd = _dateConverter.fromString(_plannedEndField.getText());
        Date realStart=_dateConverter.fromString(_realStartField.getText());
        Date realEnd=_dateConverter.fromString(_realEndField.getText());

        appointment.setCreationDate(creationDate);
        appointment.setCustomer(customer);
        appointment.setDescription(description);
        appointment.setStatus(status);
        appointment.setRealDateFrom(realStart);
        appointment.setRealDateTo(realEnd);
        appointment.setPlannedDateTimeFrom(plannedStart);
        appointment.setPlannedDateTimeTo(plannedEnd);
        appointment.setPlannedPartsAndServices(plannedPartsWithQuantity);
        appointment.setUsedPartsAndServices(usedPartsWithQuantity);
        appointment.setProduct(product);

    }
}
