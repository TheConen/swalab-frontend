package com.swalab.frontend.gui;

import com.swalab.frontend.converter.ProgressStatusConverter;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.gui.object.builder.AppointmentEditingSettings;
import com.swalab.frontend.model.Appointment;
import com.swalab.frontend.model.Customer;
import com.swalab.frontend.model.Product;
import com.swalab.frontend.model.Status;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Date;

public class AppointmentOverview extends AbstractPaneContent<Appointment> {

    private ListView<Appointment> _listView;
    private Label _descriptionLabel;
    private Label _creationDateLabel;
    private Label _statusLabel;
    private Label _plannedStartLabel;
    private Label _plannedEndLabel;
    private TextField _descriptionField;
    private TextField _creationDateField;
    private TextField _plannedStartField;
    private TextField _plannedEndField;
    private StatusCombobox _statusComboBox;
    private ProgressStatusConverter _statusStringConverter;

    @Override
    public Parent getMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        pane.setCenter(_listView);
        _listView.getItems().add(new Appointment(new Customer(), "Description", new Product("Product name", "Product description", 0l, new Date(), "", new ArrayList<>()), new Date(), Status.OPEN, new ArrayList<>(), new Date(), new Date(), new ArrayList(), new Date(), new Date()));
        return pane;
    }

    @Override
    public Parent getDescriptionWindowContent() {

        Label descriptionLabel = new Label("Description");
        Label creationDateLabel = new Label("Creation Date");
        Label statusLabel = new Label("Status");
        Label plannedStartLabel = new Label("Planned begin");
        Label plannedEndLabel = new Label("Planned end");

        _statusStringConverter = new ProgressStatusConverter();


        _descriptionLabel = new Label();
        _creationDateLabel = new Label();
        _statusLabel = new Label();
        _plannedStartLabel = new Label();
        _plannedEndLabel = new Label();

        _descriptionField = new TextField();
        _creationDateField = new TextField();
        _plannedStartField = new TextField();
        _plannedEndField = new TextField();
        _statusComboBox = new StatusCombobox(_statusStringConverter);

        _creationDateField.setDisable(true);

        InlineEditor<Appointment> editor = new InlineEditor<>(_listView, new AppointmentEditingSettings());
        editor.addPermanentVisible(descriptionLabel, creationDateLabel, statusLabel, plannedStartLabel, plannedEndLabel);
        editor.addViewerColumnNode(_descriptionLabel, _creationDateLabel, _statusLabel, _plannedStartLabel, _plannedEndLabel);
        editor.addEditorColumnNode(_descriptionField, _creationDateField, _statusComboBox, _plannedStartField, _plannedEndField);
        editor.createAndAddDefaultButton();
        return editor;
    }

    @Override
    protected void updateDescriptionContent(Appointment item, Class clazz) {
        if (item == null) {
            _descriptionLabel.setText(null);
            _creationDateLabel.setText(null);
            _statusLabel.setText(null);
            _plannedStartLabel.setText(null);
            _plannedEndLabel.setText(null);
        } else {
            _descriptionLabel.setText(item.getDescription());
            _creationDateLabel.setText(item.getCreationDate().toGMTString());
            _statusLabel.setText(_statusStringConverter.toString(item.getStatus()));
            _plannedStartLabel.setText(item.getPlannedDateTimeFrom().toGMTString());
            _plannedEndLabel.setText(item.getPlannedDateTimeTo().toGMTString());
        }
    }

    @Override
    public void requestFocus() {
        _listView.requestFocus();
    }
}
