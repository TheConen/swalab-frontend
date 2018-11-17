package com.swalab.frontend.gui;

import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.converter.ProgressStatusConverter;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.composites.StatusCombobox;
import com.swalab.frontend.gui.object.builder.WarehousePartAndOrderEditingSettings;
import com.swalab.frontend.model.*;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.Date;
import java.util.function.Consumer;

public class OrdersAndPartsPaneContent extends AbstractPaneContent<WarehousePartAndOrder> {

    private ListView<WarehousePartAndOrder> _listView;
    private Label _descriptionLabel;
    private Label _orderDateLabel;
    private Label _statusLabel;
    private TextField _descriptionField;
    private Label _orderDateField;
    private ComboBox<Status> _statusComboBox;
    private ProgressStatusConverter _statusStringConverter;
    private Label _orderNumberLabel;
    private TextField _orderNumberField;

    public OrdersAndPartsPaneContent(SynchController synchController) {
        super(synchController);
    }

    public Parent createMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        pane.setCenter(_listView);
        _listView.getItems().add(new WarehousePartAndOrder(0l, "Description", new Date(), new PartWithQuantity(new AvailablePart("Part name", "Part description"), 10, "units"), Status.OPEN));
        return pane;
    }

    public Parent createDescriptionWindowContent() {
        Label descriptionLabel = new Label("Description");
        Label orderDateLabel = new Label("Order date");
        Label statusLabel = new Label("Status");
        Label orderNumberLabel = new Label("Order number");

        _statusStringConverter = new ProgressStatusConverter();

        _descriptionLabel = new Label();
        _orderDateLabel = new Label();
        _statusLabel = new Label();
        _orderNumberLabel = new Label();

        _descriptionField = new TextField();
        _orderDateField = new Label();
        _statusComboBox = new StatusCombobox(_statusStringConverter);
        _orderNumberField = new TextField();
        _orderNumberField.setDisable(true);

        TextField idField = new TextField();

        InlineEditor<WarehousePartAndOrder> editor = new InlineEditor<>(_listView, new WarehousePartAndOrderEditingSettings(_descriptionField, _orderDateField, _statusComboBox, _orderNumberField, idField));
        editor.addPermanentVisible(orderNumberLabel, descriptionLabel, orderDateLabel, statusLabel);
        editor.addViewerColumnNode(_orderNumberLabel, _descriptionLabel, _orderDateLabel, _statusLabel);
        editor.addEditorColumnNode(_orderNumberField, _descriptionField, _orderDateField, _statusComboBox);
        editor.createAndAddDefaultButton();
        editor.addIDField(idField);
        return editor;
    }

    @Override
    protected void updateDescriptionContent(WarehousePartAndOrder item, Class clazz) {
        if (item == null) {
            _descriptionLabel.setText(null);
            _orderDateLabel.setText(null);
            _statusLabel.setText(null);
            _orderNumberLabel.setText(null);
        } else {
            _descriptionLabel.setText(item.getDescription());
            _orderDateLabel.setText(item.getOrderDate().toGMTString());
            _orderNumberLabel.setText(item.getOrderNumber() + "");
            _statusLabel.setText(_statusStringConverter.toString(item.getStatus()));
        }
    }

    @Override
    protected Consumer<Technician> getUpdateConsumer() {
        return technician -> _listView.setItems(technician.getObservableParts());
    }

    @Override
    public void requestFocus() {
        _listView.requestFocus();
    }
}
