package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.model.Appointment;
import javafx.scene.control.TextField;

public class AppointmentEditingSettings implements IEditorSettings<Appointment> {

    private final TextField _descriptionField;
    private final TextField _creationDateField;
    private final TextField _plannedStartField;
    private final TextField _plannedEndField;

    public AppointmentEditingSettings(TextField descriptionField, TextField creationDateField, StatusCombobox statusComboBox, TextField plannedStartField, TextField plannedEndField) {
        _descriptionField = descriptionField;
        _creationDateField = creationDateField;
        _plannedStartField = plannedStartField;
        _plannedEndField = plannedEndField;
    }

    @Override
    public Appointment createObject() {
        // TODO create correct object
        return new Appointment();

    }

    @Override
    public void setDefaultValues(Appointment content) {
        _descriptionField.setText(content == null ? null : content.getDescription());
        _creationDateField.setText(content == null ? null : content.getCreationDate().toGMTString());
        _plannedStartField.setText(content == null ? null : content.getPlannedDateTimeFrom().toGMTString());
        _plannedEndField.setText(content == null ? null : content.getPlannedDateTimeTo().toGMTString());
    }

    @Override
    public boolean canObjectBeCreated() {
        return true;
    }
}
