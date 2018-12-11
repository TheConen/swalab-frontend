package com.swalab.frontend.gui;

import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.converter.DateConverter;
import com.swalab.frontend.converter.ProgressStatusConverter;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.composites.NamedArtefactBasedListCellFactory;
import com.swalab.frontend.gui.composites.PartsAndServiceEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.gui.object.builder.AppointmentEditingSettings;
import com.swalab.frontend.model.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

public class AppointmentOverview extends AbstractPaneContent<Appointment> {

    private final DateConverter _dateConverter;
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
    private ComboBox<Customer> _customerComboBox;
    private PartsAndServiceEditor _usedPartsAndServiceEditor;
    private PartsAndServiceEditor _plannedPartsAndServiceEditor;

    public AppointmentOverview(SynchController synchController) {
        super(synchController);
        _dateConverter = new DateConverter();
        _usedPartsAndServiceEditor.setSyncController(synchController);
        _plannedPartsAndServiceEditor.setSyncController(synchController);
    }

    @Override
    protected Consumer<Technician> getUpdateConsumer() {
        return technician -> {
            _listView.setItems(technician.getObservableAppointments());
            _customerComboBox.setItems(technician.getObservableCustomers());
        };

    }

    @Override
    public Parent createMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        pane.setCenter(_listView);

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
        _customerComboBox = new ComboBox<>();
        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.setConverter(new StringConverter<>() {

            @Override
            public String toString(Product product) {
                return product == null ? "" : product.getName();
            }

            @Override
            public Product fromString(String s) {
                if (s.isEmpty()) {
                    return null;
                }
                Optional<Product> productOptional = productComboBox.getItems().stream().filter(c -> c.getName().equals(s)).findFirst();
                if (productOptional.isPresent()) {
                    return productOptional.get();
                } else {
                    throw new IllegalArgumentException("Customer not found");
                }
            }
        });

        _usedPartsAndServiceEditor = new PartsAndServiceEditor();
        _plannedPartsAndServiceEditor = new PartsAndServiceEditor();

        _customerComboBox.getSelectionModel().selectedItemProperty().addListener(ae -> {
            Customer customer = _customerComboBox.getSelectionModel().getSelectedItem();
            productComboBox.setItems(customer == null ? FXCollections.observableArrayList() : customer.getObservableProducts());
        });
        _customerComboBox.getSelectionModel().selectedItemProperty().addListener(ae -> productComboBox.setDisable(_customerComboBox.getSelectionModel().getSelectedItem() == null));
        _customerComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Customer customer) {
                return customer == null ? "" : customer.getName();
            }

            @Override
            public Customer fromString(String s) {
                if (s.isEmpty()) {
                    return null;
                }
                Optional<Customer> customerOptional = _customerComboBox.getItems().stream().filter(c -> c.getName().equals(s)).findFirst();
                if (customerOptional.isPresent()) {
                    return customerOptional.get();
                } else {
                    throw new IllegalArgumentException("Customer not found");
                }
            }
        });
        productComboBox.setDisable(true);

        TextField idField = new TextField();

        _creationDateField.setDisable(true);
        AppointmentEditingSettings settings = new AppointmentEditingSettings(_descriptionField, _creationDateField, _statusComboBox, _plannedStartField, _plannedEndField, _customerComboBox, productComboBox, _plannedPartsAndServicesList, _usedPartsAndServicesList, _usedPartsAndServiceEditor, idField, _plannedPartsAndServiceEditor);
        _editor = new InlineEditor<>(_listView, settings, this);
        _editor.addPermanentVisible(descriptionLabel, creationDateLabel, statusLabel, plannedStartLabel, plannedEndLabel, customerLabel, productLabel, plannedPartsAndServicesLabel, usedPartsAndServicesLabel);
        _editor.addViewerColumnNode(_descriptionLabel, _creationDateLabel, _statusLabel, _plannedStartLabel, _plannedEndLabel, _customerLabel, _productLabel, _plannedPartsAndServicesList, _usedPartsAndServicesList);
        _editor.addEditorColumnNode(_descriptionField, _creationDateField, _statusComboBox, _plannedStartField, _plannedEndField, _customerComboBox, productComboBox);
        _editor.addEditorColumnNode(_plannedPartsAndServiceEditor,3,1);
        _editor.addEditorColumnNode(_usedPartsAndServiceEditor,3,1);
        _editor.createAndAddDefaultButton();
        _editor.addIDField(idField);
        return _editor;
    }

    @Override
    protected void updateDescriptionContentInternal(Appointment item, Class clazz) {
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
            _creationDateLabel.setText(_dateConverter.toString(item.getCreationDate()));
            _statusLabel.setText(_statusStringConverter.toString(item.getStatus()));
            _plannedStartLabel.setText(_dateConverter.toString(item.getPlannedDateTimeFrom()));
            _plannedEndLabel.setText(_dateConverter.toString(item.getPlannedDateTimeTo()));
            _customerLabel.setText(item.getCustomer().getName());
            _productLabel.setText(item.getProduct().getName());
            _plannedPartsAndServicesList.setItems(item.getObservablePlannedPartsAndServices());
            _usedPartsAndServicesList.setItems(item.getObservableUsedPartsAndServices());
        }
    }

    @Override
    public void requestFocus() {
        defaultListElementSelection(_listView);
        _editor.setEditorMode(false);
        _listView.requestFocus();
    }

    @Override
    public Comparator<Appointment> getComparator() {
        return (Comparator<Appointment>)(o1,o2)->o1.getCreationDate().compareTo(o2.getCreationDate());
    }

    @Override
    public void clearEditorEnvironment() {
        _plannedPartsAndServiceEditor.clear();
        _usedPartsAndServiceEditor.clear();
    }
}
