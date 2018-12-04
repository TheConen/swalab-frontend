package com.swalab.frontend.gui;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.converter.DateConverter;
import com.swalab.frontend.converter.ProgressStatusConverter;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.gui.object.builder.TaskAndNoteSettings;
import com.swalab.frontend.model.AbstractTaskAndNote;
import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.Task;
import com.swalab.frontend.model.Technician;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Consumer;
import java.util.function.Function;

public class TaskPaneContent extends AbstractPaneContent<AbstractTaskAndNote> {

    private final DateConverter _dateConverter;
    private Label _nameLabel;
    private Label _descriptionLabel;
    private Label _statusLabel;
    private Label _creationLabel;
    private TextField _nameField;
    private TextField _descriptionField;
    private ComboBox<Status> _statusBox;
    private TextField _creationField;
    private ListView<AbstractTaskAndNote> _listView;
    private Label _statusDescriptionLabel;
    private ProgressStatusConverter _statusConverter;
    private InlineEditor<AbstractTaskAndNote> _editor;

    public TaskPaneContent(SynchController synchController) {
        super(synchController);
        _dateConverter=new DateConverter();
    }

    @Override
    public Parent createMainWindowContent() {
        ObservableList<AbstractTaskAndNote> list = FXCollections.observableArrayList();
        list.add(new Task("title", "description", Status.OPEN, new Date(System.currentTimeMillis())));
        list.add(new Task("title2", "description", Status.IN_PROGRESS, new Date(System.currentTimeMillis())));
        //list.add(new Note("Note", "Note description", new Date(System.currentTimeMillis())));

        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        _listView.setItems(list);
        _listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AbstractTaskAndNote>() {
            @Override
            public void changed(ObservableValue<? extends AbstractTaskAndNote> observableValue, AbstractTaskAndNote oldValue, AbstractTaskAndNote newValue) {
                if (oldValue == null || newValue != null && oldValue.getClass().equals(newValue.getClass())) {
                    boolean nodeMode = newValue instanceof Task;
                    _statusDescriptionLabel.setVisible(nodeMode);
                    _statusDescriptionLabel.setManaged(nodeMode);
                }
            }
        });

        pane.setCenter(_listView);

        HBox modificationBox = new HBox();
        modificationBox.setSpacing(5);
        modificationBox.setPadding(new Insets(3, 3, 3, 3));
        Button taskCreationButton = new Button("+ Task");
        taskCreationButton.setOnAction(ae -> {
            _listView.getSelectionModel().select(null);
            _editor.setEditorMode(true);
        });
        //Button noteCreationButton = new Button("+ Note");
        //noteCreationButton.setOnAction(ae -> {
        //    _listView.getSelectionModel().select(null);
        //    _editor.setEditorMode(true);
        //});

        modificationBox.getChildren().add(taskCreationButton);
        //modificationBox.getChildren().add(noteCreationButton);

        pane.setBottom(modificationBox);

        return pane;
    }

    @Override
    public Parent createDescriptionWindowContent() {
        GridPane descriptionPane = new GridPane();
        descriptionPane.setPadding(new Insets(5, 5, 5, 5));
        descriptionPane.setVgap(3);
        descriptionPane.setHgap(3);

        _statusConverter = new ProgressStatusConverter();

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
        _statusBox = new StatusCombobox(_statusConverter);
        _creationField = new TextField();

        _creationField.setDisable(true);

        TextField idField=new TextField();

        IEditorSettings editorSettings = new TaskAndNoteSettings(_nameField, _descriptionField, _creationField, _statusBox,idField);
        _editor = new InlineEditor<>(_listView, editorSettings,this);
        _editor.addPermanentVisible(nameLabel, descriptionLabel, creationLabel, _statusDescriptionLabel);
        _editor.addViewerColumnNode(_nameLabel, _descriptionLabel, _creationLabel, _statusLabel);
        _editor.addEditorColumnNode(_nameField, _descriptionField, _creationField, _statusBox);
        _editor.createAndAddDefaultButton();
        _editor.addIDField(idField);

        Function<Boolean, Boolean> f = isEditorMode -> {
            boolean isStatusRowRequired = _statusDescriptionLabel.isVisible();
            if (isEditorMode) {
                _statusLabel.setVisible(false);
                _statusLabel.setManaged(false);
                _statusBox.setVisible(isStatusRowRequired);
                _statusBox.setManaged(isStatusRowRequired);
            } else {
                _statusLabel.setVisible(isStatusRowRequired);
                _statusLabel.setManaged(isStatusRowRequired);
                _statusBox.setVisible(false);
                _statusBox.setManaged(false);
            }
            return isStatusRowRequired;
        };

        _editor.setPostShowingFunction(f);

        return _editor;
    }


    @Override
    protected void updateDescriptionContentInternal(AbstractTaskAndNote item, Class clazz) {
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
            _creationLabel.setText(creationDate == null ? null : _dateConverter.toString(creationDate));

            boolean isTask = clazz.equals(Task.class);
            _statusDescriptionLabel.setManaged(isTask);
            _statusLabel.setManaged(isTask);
            _statusBox.setManaged(isTask);

            if (isTask) {
                Task task = (Task) item;
                Status status = task.getStatus();
                _statusLabel.setVisible(true);
                _statusLabel.setText(status == null ? null : _statusConverter.toString(status));
                _statusDescriptionLabel.setVisible(true);
            } else {
                _statusLabel.setVisible(false);
                _statusDescriptionLabel.setVisible(false);
                _statusBox.setVisible(false);
            }
        }

    }

    @Override
    protected Consumer<Technician> getUpdateConsumer() {
        return technician -> _listView.setItems(technician.getObservableTaskAndNotes());
    }


    @Override
    public void requestFocus() {
        defaultListElementSelection(_listView);
        _editor.setEditorMode(false);
        _listView.requestFocus();
    }


}
