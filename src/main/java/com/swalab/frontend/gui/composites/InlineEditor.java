package com.swalab.frontend.gui.composites;

import com.swalab.frontend.api.IEditorSettings;
import com.swalab.frontend.api.IUpdateableWindowDescription;
import com.swalab.frontend.api.IPostSaveAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InlineEditor<T> extends GridPane {

    private final List<Node> _permanentVisibleNodeList;
    private final List<Node> _viewerColumnNodeList;
    private final List<Node> _editorColumnNodeList;
    private final ListView<T> _listView;
    private final IEditorSettings<T> _objectBuilder;
    private final IUpdateableWindowDescription<T> _updateableWindowDescription;
    private IPostSaveAction _postSaveAction;
    private TextField _idField;
    private Function<Boolean, Boolean> _postShowingFunction;
    private T _currentSubject;

    public InlineEditor(IPostSaveAction saveAction, ListView<T> listView, IEditorSettings objectBuilder, IUpdateableWindowDescription<T> updateableWindowDescription) {
        this(listView, objectBuilder, updateableWindowDescription);
        _postSaveAction = saveAction;
    }

    public InlineEditor(ListView<T> listView, IEditorSettings objectBuilder, IUpdateableWindowDescription<T> updateableWindowDescription) {
        super();
        _postSaveAction = null;
        _updateableWindowDescription = updateableWindowDescription;
        _listView = listView;
        _listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
                if (oldValue == null && newValue != null) {
                    setEditorMode(false);
                }
            }
        });
        _objectBuilder = objectBuilder;
        _permanentVisibleNodeList = new ArrayList<Node>();
        _viewerColumnNodeList = new ArrayList<Node>();
        _editorColumnNodeList = new ArrayList<Node>();
        _idField = null;
        setPadding(new Insets(5, 5, 5, 5));
        setHgap(3);
        setVgap(3);
    }

    public void addPermanentVisible(Node... nodes) {
        for (Node node : nodes) {
            addPermanentVisible(node);
        }
    }

    public void addPermanentVisible(Node node) {
        _permanentVisibleNodeList.add(node);
        add(node, 0, _permanentVisibleNodeList.size());
        setHalignment(node, HPos.LEFT);
        setValignment(node, VPos.TOP);
    }

    public void addViewerColumnNode(Node... nodes) {
        for (Node node : nodes) {
            addViewerColumnNode(node);
        }
    }

    public void addViewerColumnNode(Node node) {
        _viewerColumnNodeList.add(node);
        add(node, 1, _viewerColumnNodeList.size());
    }

    public void addEditorColumnNode(Node... nodes) {
        for (Node node : nodes) {
            addEditorColumnNode(node, 1, 1);
        }
    }

    public void addEditorColumnNode(Node node, int colSpan, int rowSpan) {
        _editorColumnNodeList.add(node);
        add(node, 2, _editorColumnNodeList.size(), colSpan, rowSpan);
        node.setVisible(false);
        node.setManaged(false);
    }

    public void addIDField(TextField text) {
        text.setVisible(false);
        text.setManaged(false);
        add(text, 3, 0);
        _idField = text;
    }

    public void setEditorMode(boolean isEditorMode) {
        for (Node node : _viewerColumnNodeList) {
            node.setVisible(!isEditorMode);
            node.setManaged(!isEditorMode);
        }
        for (Node node : _editorColumnNodeList) {
            node.setVisible(isEditorMode);
            node.setManaged(isEditorMode);
        }
        _currentSubject = _listView.getSelectionModel().getSelectedItem();
        if (!isEditorMode) {
            _updateableWindowDescription.updateDescriptionContent(_currentSubject);
        } else {
            _updateableWindowDescription.clearEditorEnvironment();
        }
        _objectBuilder.setDefaultValues(_currentSubject);
        if (_postShowingFunction != null) {
            _postShowingFunction.apply(isEditorMode);
        }

    }

    public void createAndAddDefaultButton() {
        HBox viewerButtons = new HBox();
        viewerButtons.setPadding(new Insets(3, 3, 3, 3));
        viewerButtons.setSpacing(5);
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(ae -> {
            T selectedItem = _listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                _listView.getItems().remove(selectedItem);
                // TODO reset editor fields
                // if _listView.isEmpty, hide editor (NOTE: What happens, if it's hidden and something should be created?
            }
        });
        deleteButton.setDisable(true);
        Button editButton = new Button("Edit");
        editButton.setOnAction(ae -> {
            setEditorMode(true);
            ae.consume();
        });
        editButton.setDisable(true);
        viewerButtons.getChildren().addAll(editButton, deleteButton);

        _listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
                if (oldValue == null && newValue != null) {
                    setEditorMode(false);
                }
                deleteButton.setDisable(newValue == null);
                editButton.setDisable(newValue == null);
            }
        });


        addViewerColumnNode(viewerButtons);

        HBox editorButtons = new HBox();
        editorButtons.setPadding(new Insets(3, 3, 3, 3));
        editorButtons.setSpacing(5);
        Button aboardButton = new Button("Aboard");
        aboardButton.setOnAction(ae -> {
            _listView.getSelectionModel().select(_currentSubject);
            setEditorMode(false);
            _listView.requestFocus();

        });
        Button saveButton = new Button("Save");
        saveButton.setOnAction(ae ->

        {
            if (isObjectCreationRequiered()) {
                if (_objectBuilder.canObjectBeCreated()) {
                    T object = _objectBuilder.createObject();
                    _listView.getItems().add(object);
                    setEditorMode(false);
                    _listView.getSelectionModel().select(object);
                }
            } else {
                _objectBuilder.updateContent(_currentSubject);
                setEditorMode(false);
            }
            if (_postSaveAction != null) {
                _postSaveAction.executeCustomizedPostSaveAction();
            }
        });
        editorButtons.getChildren().addAll(saveButton, aboardButton);

        addEditorColumnNode(editorButtons);
    }


    private boolean isObjectCreationRequiered() {
        String text = _idField.getText();
        return _idField == null || text == null || text.isEmpty();
    }

    public void setPostShowingFunction(Function<Boolean, Boolean> function) {
        _postShowingFunction = function;
    }
}
