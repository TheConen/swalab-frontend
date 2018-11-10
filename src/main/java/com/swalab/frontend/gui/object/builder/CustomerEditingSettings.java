package com.swalab.frontend.gui.object.builder;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.model.Customer;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CustomerEditingSettings implements IEditorSettings<Customer> {


    private final TextField _nameField;
    private final TextField _geolocationField;
    private final TextField _phoneField;
    private final TextField _mailField;
    private final TextField _webField;
    private final TextField _addressField;
    private final TextField _idField;

    public CustomerEditingSettings(TextField nameField, TextField geolocationField, TextField phoneField, TextField mailField, TextField webField, TextField addressField, TextField idField) {
        _nameField = nameField;
        _geolocationField = geolocationField;
        _phoneField = phoneField;
        _mailField = mailField;
        _webField = webField;
        _addressField = addressField;
        _idField=idField;
    }

    @Override
    public Customer createObject() {
        return new Customer(_nameField.getText(), _geolocationField.getText(), _phoneField.getText(), _mailField.getText(), _webField.getText(), _addressField.getText(), new ArrayList<>());
    }

    @Override
    public void setDefaultValues(Customer content) {
        _nameField.setText(content==null?null:content.getName());
        _geolocationField.setText(content==null?null:content.getGeolocation());
        _phoneField.setText(content==null?null:content.getPhone());
        _mailField.setText(content==null?null:content.getMail());
        _webField.setText(content==null?null:content.getWeb());
        _addressField.setText(content==null?null:content.getAddress());
        _idField.setText(content==null?null:content.getID()+"");
    }

    @Override
    public boolean canObjectBeCreated() {
        // TODO correct safty checks
        return true;
    }
}
