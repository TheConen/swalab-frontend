package com.swalab.frontend.gui;

import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.converter.DateConverter;
import com.swalab.frontend.converter.ProgressStatusConverter;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.gui.object.builder.WarehousePartAndOrderEditingSettings;
import com.swalab.frontend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

public class OrdersAndPartsPaneContent extends AbstractPaneContent<WarehousePartAndOrder> {

    private final DateConverter _dateConverter;
    private ListView<WarehousePartAndOrder> _listView;
    private Label _descriptionLabel;
    private Label _orderDateLabel;
    private Label _statusLabel;
    private TextField _descriptionField;
    private TextField _orderDateField;
    private ComboBox<Status> _statusComboBox;
    private ProgressStatusConverter _statusStringConverter;
    private Label _orderNumberLabel;
    private TextField _orderNumberField;
    private Label _partLabel;
    private Label _unitLabel;
    private Label _quantityLabel;
    private TextField _quantityField;
    private ComboBox<AvailablePart> _partComboBox;
    private TextField _unitField;
    private InlineEditor<WarehousePartAndOrder> _editor;

    public OrdersAndPartsPaneContent(SynchController synchController) {
        super(synchController);
        _dateConverter = new DateConverter();
        _partComboBox.setItems(synchController.getAvailableParts());
    }

    public Parent createMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        pane.setCenter(_listView);
        _listView.getItems().add(new WarehousePartAndOrder(0l, "Description", new Date(), new PartWithQuantity(new AvailablePart("Part name", "Part description"), 10, "units"), Status.OPEN));

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(5);
        buttonBox.setPadding(new Insets(3, 3, 3, 3));
        pane.setBottom(buttonBox);

        Button creationButton = new Button("+ Order");
        buttonBox.getChildren().add(creationButton);
        creationButton.setOnAction(ae -> {
            _listView.getSelectionModel().select(null);
            _editor.setEditorMode(true);
        });

        return pane;
    }

    public Parent createDescriptionWindowContent() {
        Label descriptionLabel = new Label("Description");
        Label orderDateLabel = new Label("Order date");
        Label statusLabel = new Label("Status");
        Label orderNumberLabel = new Label("Order number");
        Label partLabel = new Label("Part");
        Label unitLabel = new Label("Unit");
        Label quantityLabel = new Label("Quantity");

        _statusStringConverter = new ProgressStatusConverter();

        _descriptionLabel = new Label();
        _orderDateLabel = new Label();
        _statusLabel = new Label();
        _orderNumberLabel = new Label();
        _partLabel = new Label();
        _unitLabel = new Label();
        _quantityLabel = new Label();

        _descriptionField = new TextField();
        _orderDateField = new TextField();
        _orderDateField.setDisable(true);
        _statusComboBox = new StatusCombobox(_statusStringConverter);
        _orderNumberField = new TextField();
        _orderNumberField.setDisable(true);
        _partComboBox = new ComboBox<>();
        _partComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(AvailablePart availablePart) {
                if (availablePart == null) {
                    return "No part available";
                }
                return availablePart.getName();
            }

            @Override
            public AvailablePart fromString(String s) {
                Optional<AvailablePart> availablePartOptional = _partComboBox.getItems().stream().filter(p -> p.getName().equals(s)).findFirst();
                if (availablePartOptional.isPresent()) {
                    return availablePartOptional.get();
                }
                throw new IllegalArgumentException("Element not available");
            }
        });
        _unitField = new TextField();
        _quantityField = new TextField();

        TextField idField = new TextField();
        WarehousePartAndOrderEditingSettings settings = new WarehousePartAndOrderEditingSettings(_partComboBox, _quantityField, _unitField, _descriptionField, _orderDateField, _statusComboBox, _orderNumberField, idField);
        _editor = new InlineEditor<>(_listView, settings, this);
        _editor.addPermanentVisible(partLabel, quantityLabel, unitLabel, orderNumberLabel, descriptionLabel, orderDateLabel, statusLabel);
        _editor.addViewerColumnNode(_partLabel, _quantityLabel, _unitLabel, _orderNumberLabel, _descriptionLabel, _orderDateLabel, _statusLabel);
        _editor.addEditorColumnNode(_partComboBox, _quantityField, _unitField, _orderNumberField, _descriptionField, _orderDateField, _statusComboBox);
        _editor.createAndAddDefaultButton();
        _editor.addIDField(idField);
        return _editor;
    }

    @Override
    protected void updateDescriptionContentInternal(WarehousePartAndOrder item, Class clazz) {
        if (item == null) {
            _descriptionLabel.setText(null);
            _orderDateLabel.setText(null);
            _statusLabel.setText(null);
            _orderNumberLabel.setText(null);
            _quantityLabel.setText(null);
            _partLabel.setText(null);
            _unitLabel.setText(null);
        } else {
            _descriptionLabel.setText(item.getDescription());
            _orderDateLabel.setText(_dateConverter.toString(item.getOrderDate()));
            _orderNumberLabel.setText(item.getOrderNumber() + "");
            _statusLabel.setText(_statusStringConverter.toString(item.getStatus()));
            PartWithQuantity partWithQuantity = item.getPart();
            if (partWithQuantity != null) {
                _partLabel.setText(partWithQuantity.getAvailablePart().getName());
                _unitLabel.setText(partWithQuantity.getUnit());
                _quantityLabel.setText(partWithQuantity.getQuantity() + "");
            }
            _quantityLabel.setText(null);
            _partLabel.setText(null);
            _unitLabel.setText(null);
        }
    }

    @Override
    protected Consumer<Technician> getUpdateConsumer() {
        return technician -> _listView.setItems(technician.getObservableParts());
    }

    @Override
    public void requestFocus() {
        defaultListElementSelection(_listView);
        _editor.setEditorMode(false);
        _listView.requestFocus();
    }
}
