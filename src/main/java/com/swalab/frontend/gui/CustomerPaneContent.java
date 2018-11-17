package com.swalab.frontend.gui;

import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.gui.composites.InlineEditor;
import com.swalab.frontend.gui.composites.NamedArtefactBasedListCellFactory;
import com.swalab.frontend.gui.object.builder.CustomerEditingSettings;
import com.swalab.frontend.model.Customer;
import com.swalab.frontend.model.Product;
import com.swalab.frontend.model.Technician;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CustomerPaneContent extends AbstractPaneContent<Customer> {

    private Label _nameLabel;
    private Label _geolocationLabel;
    private Label _mailLabel;
    private Label _phoneLabel;
    private Label _webLabel;
    private Label _addressLabel;
    private TextField _nameField;
    private TextField _geolocationField;
    private TextField _phoneField;
    private TextField _mailField;
    private TextField _webField;
    private TextField _addressField;
    private ListView<Customer> _listView;
    private InlineEditor<Customer> _editor;
    private ListView<Product> _productList;

    public CustomerPaneContent(SynchController synchController) {
        super(synchController);
    }

    @Override
    public Parent createMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        _listView = createListView();
        _listView.getItems().addAll(new Customer("name", "geolocation", "phone", "mail", "web", "address", new ArrayList<>()));
        pane.setCenter(_listView);

        Button creationButton = new Button("+ Customer");
        creationButton.setOnAction(ae -> {
            _listView.getSelectionModel().select(null);
            _editor.setEditorMode(true);
        });
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(5);
        buttonBox.setPadding(new Insets(3, 3, 3, 3));
        buttonBox.getChildren().add(creationButton);

        pane.setBottom(buttonBox);

        return pane;
    }

    @Override
    public Parent createDescriptionWindowContent() {

        Label nameLabel = new Label("Name");
        Label geolocationLabel = new Label("Geolocation");
        Label phoneLabel = new Label("Phone");
        Label mailLabel = new Label("Phone");
        Label webLabel = new Label("Web");
        Label addressLabel = new Label("Address");
        Label productlabel = new Label("Products");


        _nameLabel = new Label();
        _geolocationLabel = new Label();
        _phoneLabel = new Label();
        _mailLabel = new Label();
        _webLabel = new Label();
        _addressLabel = new Label();
        _productList=new ListView<>();
        _productList.setPlaceholder(new Label("No customer selected or the customer doesn't have any of our products"));
        _productList.setCellFactory(new NamedArtefactBasedListCellFactory<>());

        _nameField = new TextField();
        _geolocationField = new TextField();
        _phoneField = new TextField();
        _mailField = new TextField();
        _webField = new TextField();
        _addressField = new TextField();
        TextField idField = new TextField();


        CustomerEditingSettings customerBuilder = new CustomerEditingSettings(_nameField, _geolocationField, _phoneField, _mailField, _webField, _addressField, _productList, idField);
        _editor = new InlineEditor<>(_listView, customerBuilder);

        _editor.addPermanentVisible(nameLabel, geolocationLabel, phoneLabel, mailLabel, webLabel, addressLabel,productlabel);
        _editor.addViewerColumnNode(_nameLabel, _geolocationLabel, _phoneLabel, _mailLabel, _webLabel, _addressLabel,_productList);
        _editor.addEditorColumnNode(_nameField, _geolocationField, _phoneField, _mailField, _webField, _addressField);
        _editor.addIDField(idField);
        _editor.createAndAddDefaultButton();


        return _editor;
    }

    @Override
    protected void updateDescriptionContent(Customer item, Class clazz) {
        if (item == null) {
            _nameLabel.setText(null);
            _geolocationLabel.setText(null);
            _phoneLabel.setText(null);
            _mailLabel.setText(null);
            _webLabel.setText(null);
            _addressLabel.setText(null);
        } else {
            _nameLabel.setText(item.getName());
            _geolocationLabel.setText(item.getGeolocation());
            _phoneLabel.setText(item.getPhone());
            _mailLabel.setText(item.getMail());
            _webLabel.setText(item.getWeb());
            _addressLabel.setText(item.getAddress());
        }
    }

    @Override
    protected Consumer<Technician> getUpdateConsumer() {
        return technician -> _listView.setItems(technician.getObservableCustomers());
    }

    @Override
    public void requestFocus() {
        _listView.requestFocus();
    }
}
