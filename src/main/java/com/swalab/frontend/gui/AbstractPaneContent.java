package com.swalab.frontend.gui;

import com.swalab.frontend.api.IObjectDataSourceArtefact;
import com.swalab.frontend.api.IUpdateableWindowDescription;
import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.gui.composites.NamedArtefactBasedListCellFactory;
import com.swalab.frontend.model.Technician;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractPaneContent<T extends IObjectDataSourceArtefact> implements IUpdateableWindowDescription<T> {

    private final Parent _descriptionWindowContent;
    private final Parent _mainWindowContent;

    public abstract Parent createMainWindowContent();

    public abstract Parent createDescriptionWindowContent();

    public AbstractPaneContent(SynchController synchController) {
        synchController.registerModelForUpdate(getUpdateConsumer());
        _mainWindowContent = createMainWindowContent();
        _descriptionWindowContent = createDescriptionWindowContent();
    }

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
                    updateDescriptionContentInternal(newValue, newValue.getClass());
                }
            }
        });
        listView.setCellFactory(new NamedArtefactBasedListCellFactory<T>());
        return listView;
    }

    public void updateDescriptionContent(T item) {
        if (item == null) {
            updateDescriptionContentInternal(item, null);
        } else {
            updateDescriptionContentInternal(item, item.getClass());
        }
    }

    /**
     * called for changeing the content of the description window of the currently shown pane
     */
    protected abstract void updateDescriptionContentInternal(T item, Class clazz);

    /**
     * offers an callable for updating the corresponding ui model
     *
     * @return the callable which implements the update mechanism
     */
    protected abstract Consumer<Technician> getUpdateConsumer();

    public void removeListener(SynchController synchController) {
        // TODO check whether the instances are the same or if the consumer should be cached
        synchController.removeModelForUpdate(getUpdateConsumer());
    }


    /**
     * requests the focus for the element which should have it right after this page is shown
     */
    public abstract void requestFocus();

    public void defaultListElementSelection(ListView<T> listView) {
        MultipleSelectionModel<T> selectionModel = listView.getSelectionModel();
        if (listView.getItems().isEmpty()) {
            return;
        }
        if (selectionModel.getSelectedItems().isEmpty()) {
            selectionModel.select(0);
        }
    }

    /**
     * @return the content for the main window
     */
    public Node getMainWindowContent() {
        return _mainWindowContent;
    }

    /**
     * @return the content for the description window
     */
    public Node getDescriptionWindowContent() {
        return _descriptionWindowContent;
    }

    /**
     * calculates a certain object from the main model of the scene by the id
     * @param id
     * @param <S> generic type of the object. it must be compatible with the managed object type
     * @return the found object or null
     */
    public abstract <S> Optional<S> getElementById(long id);

    /**
     * selects a given object as the data source of the scene
     * @param finding
     * @param <S> generic type of the object. it must be compatible with the managed object type
     */
    public abstract <S> void select(S finding);
}