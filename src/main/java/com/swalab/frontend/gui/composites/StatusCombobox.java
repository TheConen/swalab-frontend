package com.swalab.frontend.gui.composites;

import com.swalab.frontend.api.IStatusConverterConstants;
import com.swalab.frontend.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class StatusCombobox extends ComboBox<Status> {

    public StatusCombobox(StringConverter<Status> statusConverter) {
        super();
        ObservableList items = FXCollections.observableArrayList();
        items.addAll(Status.OPEN, Status.IN_PROGRESS, Status.FINISHED);
        setItems(items);
        Callback<ListView<Status>, ListCell<Status>> callback = new Callback<ListView<Status>, ListCell<Status>>() {
            @Override
            public ListCell<Status> call(ListView<Status> taskListView) {
                return new ListCell<Status>() {
                    @Override
                    public void updateItem(Status status, boolean isEmpty) {
                        super.updateItem(status, isEmpty);
                        if (status == null || isEmpty) {
                            setText(null);
                        } else {
                            setText(statusConverter.toString(status));
                        }
                    }
                };
            }
        };
        setCellFactory(callback);
        setConverter(statusConverter);

    }
}
