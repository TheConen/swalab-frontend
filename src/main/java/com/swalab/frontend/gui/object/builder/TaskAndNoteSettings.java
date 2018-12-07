package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.converter.DateConverter;
import com.swalab.frontend.model.AbstractTaskAndNote;
import com.swalab.frontend.model.Note;
import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Calendar;

public class TaskAndNoteSettings implements IEditorSettings<AbstractTaskAndNote> {
    private final TextField _nameField;
    private final TextField _descriptionField;
    private final TextField _creationField;
    private final ComboBox<Status> _statusBox;
    private final TextField _idField;
    private final DateConverter _dateConverter;

    public TaskAndNoteSettings(TextField nameField, TextField descriptionField, TextField creationField, ComboBox<Status> statusBox, TextField idField) {
        _nameField = nameField;
        _descriptionField = descriptionField;
        _creationField = creationField;
        _statusBox = statusBox;
        _idField = idField;
        _dateConverter = new DateConverter();
    }

    @Override
    public AbstractTaskAndNote createObject() {
        return _statusBox.isVisible() ? createTask() : createNote();
    }

    private Note createNote() {
        return new Note(_nameField.getText(), _descriptionField.getText(), _dateConverter.fromString(_creationField.getText()));
    }

    private Task createTask() {
        return new Task(_nameField.getText(), _descriptionField.getText(), _statusBox.getSelectionModel().getSelectedItem(), _dateConverter.fromString(_creationField.getText()));
    }

    @Override
    public void setDefaultValues(AbstractTaskAndNote content) {
        if (content == null) {
            _nameField.setText(null);
            _descriptionField.setText(null);
            _creationField.setText(_dateConverter.toString(Calendar.getInstance().getTime()));
            _statusBox.getSelectionModel().select(null);
            _idField.setText(null);
        } else {
            _nameField.setText(content.getName());
            _descriptionField.setText(content.getDescription());
            _creationField.setText(_dateConverter.toString(content.getCreationDate()));
            _idField.setText(content.getID() + "");
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

    @Override
    public void updateContent(AbstractTaskAndNote taskOrNote) {
        taskOrNote.setTitle(_nameField.getText());
        taskOrNote.setDescription(_nameField.getText());
        if (taskOrNote instanceof Task) {
            Task task = (Task) taskOrNote;
            task.setStatus(_statusBox.getSelectionModel().getSelectedItem());
        }

    }
}
