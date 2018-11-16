package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.model.AbstractTaskAndNote;
import com.swalab.frontend.model.Note;
import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Date;

public class TaskAndNoteSettings implements IEditorSettings<AbstractTaskAndNote> {
    private final TextField _nameField;
    private final TextField _descriptionField;
    private final TextField _creationField;
    private final ComboBox<Status> _statusBox;

    public TaskAndNoteSettings(TextField nameField, TextField descriptionField, TextField creationField, ComboBox<Status> statusBox) {
        _nameField = nameField;
        _descriptionField = descriptionField;
        _creationField = creationField;
        _statusBox = statusBox;
    }

    @Override
    public AbstractTaskAndNote createObject() {
        return _statusBox.isVisible()?createTask():createNote();
    }

    private Note createNote() {
        return new Note(_nameField.getText(),_descriptionField.getText(),new Date());
    }

    private Task createTask() {
        return new Task(_nameField.getText(),_descriptionField.getText(),_statusBox.getSelectionModel().getSelectedItem(),new Date());
    }

    @Override
    public void setDefaultValues(AbstractTaskAndNote content) {
        if (content == null) {
            _nameField.setText(null);
            _descriptionField.setText(null);
            _creationField.setText(null);
            _statusBox.getSelectionModel().select(null);
        } else {
            _nameField.setText(content.getName());
            _descriptionField.setText(content.getDescription());
            _creationField.setText(content.getCreationDate().toGMTString());
            if (content instanceof Task) {
                Status status = ((Task) content).getStatus();
                _statusBox.getSelectionModel().select(status);
            }
        }
    }

    @Override
    public boolean canObjectBeCreated() {
        // TODO creation security checks
        return true;
    }
}
