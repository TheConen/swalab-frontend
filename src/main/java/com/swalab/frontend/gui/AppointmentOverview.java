package com.swalab.frontend.gui;

import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.converter.ProgressStatusConverter;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.composites.NamedArtefactBasedListCellFactory;
import com.swalab.frontend.gui.composites.PartsAndServiceEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.gui.object.builder.AppointmentEditingSettings;
import com.swalab.frontend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

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
    private Label _customerLabel;
    private Label _productLabel;
    private ListView<PartWithQuantity> _plannedPartsAndServicesList;
    private ListView<PartWithQuantity> _usedPartsAndServicesList;
    private InlineEditor<Appointment> _editor;

    public AppointmentOverview(SynchController synchController) {
        super(synchController);
    }

    @Override
    protected Consumer<Technician> getUpdateConsumer() {
        return technician ->
                _listView.setItems(technician.getObservableAppointments());

    }

    @Override
    public Parent createMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        pane.setCenter(_listView);
        _listView.getItems().add(new Appointment(new Customer(), "Description", new Product("Product name", "Product description", 0l, new Date(), "", new ArrayList<>()), new Date(), Status.OPEN, new ArrayList<>(), new Date(), new Date(), new ArrayList(), new Date(), new Date()));

        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(3, 3, 3, 3));
        Button creationButton = new Button("+ Appointment");
        creationButton.setOnAction(ae -> {
            _listView.getSelectionModel().select(null);
            _editor.setEditorMode(true);
        });

        buttonBox.getChildren().add(creationButton);
        pane.setBottom(buttonBox);
        return pane;
    }

    @Override
    public Parent createDescriptionWindowContent() {
        Label descriptionLabel = new Label("Description");
        Label creationDateLabel = new Label("Creation Date");
        Label statusLabel = new Label("Status");
        Label plannedStartLabel = new Label("Planned begin");
        Label plannedEndLabel = new Label("Planned end");
        Label customerLabel = new Label("Customer");
        Label productLabel = new Label("Product");
        Label plannedPartsAndServicesLabel = new Label("Planned parts and services");
        Label usedPartsAndServicesLabel = new Label("Used parts and services");

        _statusStringConverter = new ProgressStatusConverter();


        _descriptionLabel = new Label();
        _creationDateLabel = new Label();
        _statusLabel = new Label();
        _plannedStartLabel = new Label();
        _plannedEndLabel = new Label();
        _customerLabel = new Label();
        _productLabel = new Label();
        _plannedPartsAndServicesList = new ListView<>();
        _plannedPartsAndServicesList.setPlaceholder(new Label("No appointment selected or no data available"));
        _plannedPartsAndServicesList.setCellFactory(new NamedArtefactBasedListCellFactory<>());
        _usedPartsAndServicesList = new ListView<>();
        _usedPartsAndServicesList.setPlaceholder(new Label("No appointment selected or no data available"));
        _usedPartsAndServicesList.setCellFactory(new NamedArtefactBasedListCellFactory<>());

        _descriptionField = new TextField();
        _creationDateField = new TextField();
        _plannedStartField = new TextField();
        _plannedEndField = new TextField();
        _statusComboBox = new StatusCombobox(_statusStringConverter);
        ComboBox<Customer> customerComboBox = new ComboBox<>();
        ComboBox<Product> productComboBox = new ComboBox<>();
        PartsAndServiceEditor partsAndServiceEditor = new PartsAndServiceEditor();

        TextField idField = new TextField();

        _creationDateField.setDisable(true);
        AppointmentEditingSettings settings=new AppointmentEditingSettings(_descriptionField, _creationDateField, _statusComboBox, _plannedStartField, _plannedEndField, customerComboBox, productComboBox, _plannedPartsAndServicesList, _usedPartsAndServicesList, partsAndServiceEditor,idField);
        _editor = new InlineEditor<>(_listView, settings);
        _editor.addPermanentVisible(descriptionLabel, creationDateLabel, statusLabel, plannedStartLabel, plannedEndLabel, customerLabel, productLabel, plannedPartsAndServicesLabel, usedPartsAndServicesLabel);
        _editor.addViewerColumnNode(_descriptionLabel, _creationDateLabel, _statusLabel, _plannedStartLabel, _plannedEndLabel, _customerLabel, _productLabel, _plannedPartsAndServicesList, _usedPartsAndServicesList);
        _editor.addEditorColumnNode(_descriptionField, _creationDateField, _statusComboBox, _plannedStartField, _plannedEndField, customerComboBox, productComboBox, partsAndServiceEditor);
        _editor.createAndAddDefaultButton();
        _editor.addIDField(idField);
        return _editor;
    }

    @Override
    protected void updateDescriptionContent(Appointment item, Class clazz) {
        if (item == null) {
            _descriptionLabel.setText(null);
            _creationDateLabel.setText(null);
            _statusLabel.setText(null);
            _plannedStartLabel.setText(null);
            _plannedEndLabel.setText(null);
            _customerLabel.setText(null);
            _productLabel.setText(null);
            _plannedPartsAndServicesList.setItems(null);
            _usedPartsAndServicesList.setItems(null);
        } else {
            _descriptionLabel.setText(item.getDescription());
            _creationDateLabel.setText(item.getCreationDate().toGMTString());
            _statusLabel.setText(_statusStringConverter.toString(item.getStatus()));
            _plannedStartLabel.setText(item.getPlannedDateTimeFrom().toGMTString());
            _plannedEndLabel.setText(item.getPlannedDateTimeTo().toGMTString());
            _customerLabel.setText(item.getCustomer().getName());
            _productLabel.setText(item.getProduct().getName());
            _plannedPartsAndServicesList.setItems(item.getObservablePlannedPartsAndServices());
            _usedPartsAndServicesList.setItems(item.getObservableUsedPartsAndServices());
        }
    }

    @Override
    public void requestFocus() {
        _listView.requestFocus();
    }
}
