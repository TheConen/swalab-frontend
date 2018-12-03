package com.swalab.frontend.gui.composites;

import com.swalab.frontend.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class StatusCombobox extends ComboBox<Status> {

    public StatusCombobox(StringConverter<Status> statusConverter) {
        super();
        ObservableList items = FXCollections.observableArrayList();
        items.addAll(Status.OPEN, Status.IN_PROGRESS, Status.FINISHED);
        setItems(items);
        setConverter(statusConverter);

    }
}
