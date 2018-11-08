package com.swalab.frontend.gui;

import com.swalab.frontend.INamedArtefact;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public abstract class AbstractPaneContent<T extends INamedArtefact> {

    public abstract Parent getMainWindowContent();

    public abstract Parent getDescriptionWindowContent();

    protected Border createBorder() {
        BorderStroke stroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(1));
        return new Border(stroke);
    }

    protected ListView<T> createListView() {
        ListView<T> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setPlaceholder(new Label("No data available"));
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
                if (newValue != null) {
                    updateDescriptionContent(newValue, newValue.getClass());
                }
            }
        });
        listView.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
            @Override
            public ListCell<T> call(ListView<T> taskListView) {
                return new ListCell<T>() {
                    @Override
                    public void updateItem(T task, boolean isEmpty) {
                        super.updateItem(task, isEmpty);
                        if (task == null || isEmpty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(task.getName());
                        }
                    }
                };
            }
        });
        return listView;
    }

    /**
     * called for changeing the content of the description window of the currently shown pane
     */
    protected abstract void updateDescriptionContent(T item, Class clazz);


    /**
     * requests the focus for the element which should have it right after this page is shown
     */
    public abstract void requestFocus();
}