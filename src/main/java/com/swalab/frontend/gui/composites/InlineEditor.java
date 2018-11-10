package com.swalab.frontend.gui.composites;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class InlineEditor extends GridPane {

    private final List<Node> _permanentVisibleNodeList;
    private final List<Node> _viewerColumnNodeList;
    private final List<Node> _editorColumnNodeList;

    public InlineEditor() {
        super();
        _permanentVisibleNodeList = new ArrayList<Node>();
        _viewerColumnNodeList = new ArrayList<Node>();
        _editorColumnNodeList = new ArrayList<Node>();
    }

    public void addPermanentVisible(Node node) {
        _permanentVisibleNodeList.add(node);
        add(node, 0, _editorColumnNodeList.size());
    }

    public void addViewerColumnNode(Node node) {
        _viewerColumnNodeList.add(node);
        add(node, 1, _editorColumnNodeList.size());
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

}
