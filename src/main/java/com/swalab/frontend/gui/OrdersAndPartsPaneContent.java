package com.swalab.frontend.gui;

import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.object.builder.WarehousePartAndOrderEditingSettings;
import com.swalab.frontend.model.AvailablePart;
import com.swalab.frontend.model.PartWithQuantity;
import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.WarehousePartAndOrder;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Date;

public class OrdersAndPartsPaneContent extends AbstractPaneContent<WarehousePartAndOrder> {

    private ListView<WarehousePartAndOrder> _listView;
    private Label _descriptionLabel;
    private Label _orderDateLabel;
    private Label _statusLabel;
    private TextField _descriptionField;
    private Label _orderDateField;
    private Label _statusField;

    public Parent getMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        pane.setCenter(_listView);
        _listView.getItems().add(new WarehousePartAndOrder(0l, "Description", new Date(), new PartWithQuantity(new AvailablePart("Part name","Part description"),10,"units"), Status.OPEN));
        return pane;
    }

    public Parent getDescriptionWindowContent() {
        Label descriptionLabel = new Label("Description");
        Label orderDateLabel = new Label("Order date");
        Label statusLabel = new Label("Status");

        _descriptionLabel = new Label();
        _orderDateLabel = new Label();
        _statusLabel = new Label();

        _descriptionField = new TextField();
        _orderDateField = new Label();
        _statusField = new Label();

        InlineEditor<WarehousePartAndOrder> editor = new InlineEditor<>(_listView, new WarehousePartAndOrderEditingSettings());
        editor.addPermanentVisible(descriptionLabel, orderDateLabel, statusLabel);
        editor.addViewerColumnNode(_descriptionLabel, _orderDateLabel, _statusLabel);
        editor.addEditorColumnNode(_descriptionField, _orderDateField, _statusField);
        editor.createAndAddDefaultButton();
        return editor;
    }

    @Override
    protected void updateDescriptionContent(WarehousePartAndOrder item, Class clazz) {

    }

    @Override
    public void requestFocus() {

    }
}
