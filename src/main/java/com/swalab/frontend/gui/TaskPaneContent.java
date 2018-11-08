package com.swalab.frontend.gui;

import com.swalab.frontend.model.AbstractTaskAndNote;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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

    @Override
    public Parent getMainWindowContent() {
        ObservableList<AbstractTaskAndNote> list = FXCollections.observableArrayList();
        list.add(new Task("title", "description", Status.OPEN, new Date(System.currentTimeMillis())));
        list.add(new Task("title2", "description", Status.IN_PROGRESS, new Date(System.currentTimeMillis())));

        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        _listView.setItems(list);
        _listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AbstractTaskAndNote>() {
            @Override
            public void changed(ObservableValue<? extends AbstractTaskAndNote> observableValue, AbstractTaskAndNote oldValue, AbstractTaskAndNote newValue) {
                if (oldValue == null && newValue != null) {
                    setEditorMode(false);
                }
            }
        });

        pane.setCenter(_listView);

        HBox modificationBox = new HBox();
        modificationBox.setSpacing(5);
        modificationBox.setPadding(new Insets(3, 3, 3, 3));
        Button taskCreationButton = new Button("+ Task");
        taskCreationButton.setOnAction(ae -> openEditorView());
        Button nodeCreationButton = new Button("+ Node");

        modificationBox.getChildren().add(taskCreationButton);
        modificationBox.getChildren().add(nodeCreationButton);

        pane.setBottom(modificationBox);

        return pane;
    }

    private void openEditorView() {
        setEditorMode(true);
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
        Label statusLabel = new Label("Status");
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
        // TODO check: Is task = node?
        // TODO create object direct and send it to the server
        _creationButton.setOnAction(ae -> {
            Task task = new Task(_nameField.getText(), _descriptionField.getText(), null, null);
            setEditorMode(false);
            // temporary adding since ui doesn't have a connection to the backend
            _listView.getItems().add(task);
        });

        _aboardButton = new Button("Abort");
        _aboardButton.setOnAction(ae -> setEditorMode(false));

        HBox creationButtonBox = new HBox();
        creationButtonBox.getChildren().addAll(_creationButton, _aboardButton);
        creationButtonBox.setPadding(new Insets(5, 5, 5, 5));
        creationButtonBox.setSpacing(3);

        setEditorMode(false);

        descriptionPane.addColumn(0, nameLabel, descriptionLabel, creationLabel, statusLabel);
        descriptionPane.addColumn(1, _nameLabel, _descriptionLabel, _creationLabel, _statusLabel);
        descriptionPane.addColumn(2, _nameField, _descriptionField, _creationField, _statusField, creationButtonBox);

        return descriptionPane;
    }

    private void setEditorMode(boolean isEditorActive) {
        _nameField.setVisible(isEditorActive);
        _descriptionField.setVisible(isEditorActive);
        _statusField.setVisible(isEditorActive);
        _creationField.setVisible(isEditorActive);
        _creationButton.setVisible(isEditorActive);
        _aboardButton.setVisible(isEditorActive);

        _nameLabel.setVisible(!isEditorActive);
        _descriptionLabel.setVisible(!isEditorActive);
        _statusLabel.setVisible(!isEditorActive);
        _creationLabel.setVisible(!isEditorActive);
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
    protected void updateDescriptionContent(AbstractTaskAndNote item) {
        if (item == null) {
            _nameLabel.setText(null);
            _descriptionLabel.setText(null);
            _statusLabel.setText(null);
            _creationLabel.setText(null);
        } else {
            _nameLabel.setText(item.getName());
            _descriptionLabel.setText(item.getDescription());
            Status status = item.getStatus();
            _statusLabel.setText(status == null ? null : status.name());
            Date creationDate = item.getCreationDate();
            _creationLabel.setText(creationDate == null ? null : creationDate.toGMTString());
        }
    }

    @Override
    public void requestFocus() {
        _listView.requestFocus();
    }


}
