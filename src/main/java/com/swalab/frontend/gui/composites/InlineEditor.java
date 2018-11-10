package com.swalab.frontend.gui.composites;

import com.swalab.frontend.api.IEditorSettings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class InlineEditor<T> extends GridPane {

    private final List<Node> _permanentVisibleNodeList;
    private final List<Node> _viewerColumnNodeList;
    private final List<Node> _editorColumnNodeList;
    private final ListView<T> _listView;
    private final IEditorSettings<T> _objectBuilder;

    public InlineEditor(ListView<T> listView, IEditorSettings objectBuilder) {
        super();
        _listView = listView;
        _objectBuilder = objectBuilder;
        _permanentVisibleNodeList = new ArrayList<Node>();
        _viewerColumnNodeList = new ArrayList<Node>();
        _editorColumnNodeList = new ArrayList<Node>();
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
            addEditorColumnNode(node);
        }
    }

    public void addEditorColumnNode(Node node) {
        _editorColumnNodeList.add(node);
        add(node, 2, _editorColumnNodeList.size());
        node.setVisible(false);
        node.setManaged(false);
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
    }

    public void createAndAddDefaultButton() {
        HBox viewerButtons = new HBox();
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(ae -> {
            T selectedItem = _listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                _listView.getItems().remove(selectedItem);
            }
        });
        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(ae -> setEditorMode(true));
        viewerButtons.getChildren().addAll(deleteButton, modifyButton);

        addViewerColumnNode(viewerButtons);

        HBox editorButtons = new HBox();
        Button aboardButton = new Button("Aboard");
        aboardButton.setOnAction(ae -> setEditorMode(false));
        Button saveButton = new Button("Save");
        saveButton.setOnAction(ae -> {
            // TODO check whether it's an update or a new creation
            if (_objectBuilder.canObjectBeCreated()) {
                T object = _objectBuilder.createObject();
                _listView.getItems().add(object);
                setEditorMode(false);
                _listView.getSelectionModel().select(object);
            }
        });
        editorButtons.getChildren().addAll(aboardButton, saveButton);
        addEditorColumnNode(editorButtons);
    }
}
