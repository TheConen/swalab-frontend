package com.swalab.frontend.gui;

import com.swalab.frontend.model.AbstractTaskAndNote;
import com.swalab.frontend.model.Note;
import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TaskPaneContent extends AbstractPaneContent<AbstractTaskAndNote> {

    private Label _nameLabel;
    private Label _descriptionLabel;
    private Label _statusLabel;
    private Label _creationLabel;
    private TextField _nameField;
    private TextField _descriptionField;
    private TextField _statusField;
    private TextField _creationField;
    private Button _creationButton;
    private ListView<AbstractTaskAndNote> _listView;
    private Button _aboardButton;
    private Label _statusDescriptionLabel;

    @Override
    public Parent getMainWindowContent() {
        ObservableList<AbstractTaskAndNote> list = FXCollections.observableArrayList();
        list.add(new Task("title", "description", Status.OPEN, new Date(System.currentTimeMillis())));
        list.add(new Task("title2", "description", Status.IN_PROGRESS, new Date(System.currentTimeMillis())));
        list.add(new Note("Note", "Note description", new Date(System.currentTimeMillis())));

        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        _listView.setItems(list);
        _listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AbstractTaskAndNote>() {
            @Override
            public void changed(ObservableValue<? extends AbstractTaskAndNote> observableValue, AbstractTaskAndNote oldValue, AbstractTaskAndNote newValue) {
                if (oldValue == null && newValue != null) {
                    setEditorMode(false, newValue.getClass());
                }
            }
        });

        pane.setCenter(_listView);

        HBox modificationBox = new HBox();
        modificationBox.setSpacing(5);
        modificationBox.setPadding(new Insets(3, 3, 3, 3));
        Button taskCreationButton = new Button("+ Task");
        taskCreationButton.setOnAction(ae -> openEditorView(Task.class));
        Button noteCreationButton = new Button("+ Note");
        noteCreationButton.setOnAction(ae -> openEditorView(Note.class));

        modificationBox.getChildren().add(taskCreationButton);
        modificationBox.getChildren().add(noteCreationButton);

        pane.setBottom(modificationBox);

        return pane;
    }

    private void openEditorView(Class forClass) {
        setEditorMode(true, forClass);
        _listView.getSelectionModel().select(null);
        _nameField.requestFocus();
    }

    @Override
    public Parent getDescriptionWindowContent() {
        GridPane descriptionPane = new GridPane();
        descriptionPane.setPadding(new Insets(5, 5, 5, 5));
        descriptionPane.setVgap(3);
        descriptionPane.setHgap(3);
        Label nameLabel = new Label("Name");
        Label descriptionLabel = new Label("Description");

        _statusDescriptionLabel = new Label("Status");
        Label creationLabel = new Label("Creation date");

        _nameLabel = new Label();
        _descriptionLabel = new Label();
        _statusLabel = new Label();
        _creationLabel = new Label();

        _nameField = new TextField();
        _descriptionField = new TextField();
        _statusField = new TextField();
        _creationField = new TextField();

        _creationField.setDisable(true);
        _creationField.setText(getCurrentTime());

        _creationButton = new Button("Create");
        _creationButton.setOnAction(ae -> {

            AbstractTaskAndNote element = null;
            if (_statusField.isVisible()) {
                element = createTask();
            } else {
                element = createNode();
            }
            Class clazz = _statusField.isVisible() ? Task.class : Note.class;
            setEditorMode(false, clazz);
            // temporary adding since ui doesn't have a connection to the backend
            _listView.getItems().add(element);
            _listView.getSelectionModel().select(element);
        });

        _aboardButton = new Button("Abort");
        _aboardButton.setOnAction(ae -> setEditorMode(false, Note.class));

        HBox creationButtonBox = new HBox();
        creationButtonBox.getChildren().addAll(_creationButton, _aboardButton);
        creationButtonBox.setPadding(new Insets(5, 5, 5, 5));
        creationButtonBox.setSpacing(3);

        setEditorMode(false, Note.class);

        descriptionPane.addColumn(0, nameLabel, descriptionLabel, creationLabel, _statusDescriptionLabel);
        descriptionPane.addColumn(1, _nameLabel, _descriptionLabel, _creationLabel, _statusLabel);
        descriptionPane.addColumn(2, _nameField, _descriptionField, _creationField, _statusField, creationButtonBox);

        return descriptionPane;
    }

    private AbstractTaskAndNote createNode() {
        return new Note(_nameField.getText(), _descriptionField.getText(), null);
    }

    private AbstractTaskAndNote createTask() {
        // TODO nullchecks
        return new Task(_nameField.getText(), _descriptionField.getText(), null, null);
    }

    private void setEditorMode(boolean isEditorActive, Class clazz) {
        _nameField.setVisible(isEditorActive);
        _descriptionField.setVisible(isEditorActive);
        _creationField.setVisible(isEditorActive);
        _creationButton.setVisible(isEditorActive);
        _aboardButton.setVisible(isEditorActive);

        _nameLabel.setVisible(!isEditorActive);

        _descriptionLabel.setVisible(!isEditorActive);
        _creationLabel.setVisible(!isEditorActive);

        if (isEditorActive) {
            _descriptionLabel.setText("");
            _nameLabel.setText("");
            _creationLabel.setText("");
            _statusLabel.setText("");

        }

        if (clazz.equals(Task.class)) {
            _statusDescriptionLabel.setVisible(true);
            _statusField.setVisible(isEditorActive);
            _statusLabel.setVisible(!isEditorActive);
        } else {
            _statusField.setVisible(false);
            _statusLabel.setVisible(false);
            _statusDescriptionLabel.setVisible(false);
        }
    }

    private String getCurrentTime() {
        StringBuilder builder = new StringBuilder();
        Calendar calendar = GregorianCalendar.getInstance();
        builder.append(calendar.get(Calendar.DATE));
        builder.append('.');
        builder.append(calendar.get(Calendar.MONTH) + 1);
        builder.append('.');
        builder.append(calendar.get(Calendar.YEAR));
        return builder.toString();
    }

    @Override
    protected void updateDescriptionContent(AbstractTaskAndNote item, Class clazz) {
        if (item == null) {
            _nameLabel.setText(null);
            _descriptionLabel.setText(null);
            _statusLabel.setText(null);
            _creationLabel.setText(null);
        } else {
            _nameLabel.setVisible(true);
            _nameLabel.setText(item.getName());
            _descriptionLabel.setVisible(true);
            _descriptionLabel.setText(item.getDescription());
            Date creationDate = item.getCreationDate();
            _creationLabel.setVisible(true);
            _creationLabel.setText(creationDate == null ? null : creationDate.toGMTString());

            if (clazz.equals(Task.class)) {
                Task task = (Task) item;
                Status status = task.getStatus();
                _statusLabel.setVisible(true);
                _statusLabel.setText(status == null ? null : status.name());
                _statusDescriptionLabel.setVisible(true);
            } else {
                _statusLabel.setVisible(false);
                _statusDescriptionLabel.setVisible(false);
                _statusField.setVisible(false);
            }
        }

    }


    @Override
    public void requestFocus() {
        _listView.requestFocus();
    }


}
