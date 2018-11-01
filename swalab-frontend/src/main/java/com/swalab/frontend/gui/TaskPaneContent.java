package com.swalab.frontend.gui;

import com.swalab.frontend.model.Status;
import com.swalab.frontend.model.Task;
import com.swalab.frontend.model.Technican;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Date;

public class TaskPaneContent extends AbstractPaneContent<Task> {

    private Label _nameLabel;
    private Label _descriptionlabel;
    private Label _statusLabel;
    private Label _creationLabell;
    private Label _technicanLabel;

    @Override
    public Parent getMainWindowContent() {
        ObservableList<Task> list = FXCollections.observableArrayList();
        list.add(new Task("title", "description", null, null, null));
        list.add(new Task("title2", "description", null, null, null));

        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        ListView<Task> listView = createListView();
        listView.setItems(list);

        pane.setCenter(listView);

        return pane;
    }

    @Override
    public Parent getDescriptionWindowContent() {
        GridPane pane=new GridPane();
        pane.setPadding(new Insets(5,5,5,5));
        pane.setVgap(3);
        pane.setHgap(3);
        Label nameLabel=new Label("Name");
        Label descriptionLabel=new Label("Description");
        Label statusLabel=new Label("Status");
        Label creationLabel=new Label("Creation date");
        Label technicanLabel=new Label("Technican");

        _nameLabel=new Label();
        _descriptionlabel=new Label();
        _statusLabel=new Label();
        _creationLabell=new Label();
        _technicanLabel=new Label();

        pane.addColumn(0,nameLabel,descriptionLabel,statusLabel,creationLabel,technicanLabel);
        pane.addColumn(1,_nameLabel,_descriptionlabel,_statusLabel,_creationLabell,_technicanLabel);

        return pane;
    }

    @Override
    protected void updateDescriptionContent(Task item) {
        _nameLabel.setText(item.getName());
        _descriptionlabel.setText(item.getDescription());
        Status status = item.getStatus();
        _statusLabel.setText(status==null?null:status.name());
        Date creationDate = item.getCreationDate();
        _creationLabell.setText(creationDate==null?null:creationDate.toGMTString());
        Technican technican = item.getTechnican();
        _technicanLabel.setText(technican==null?null:technican.getName());
    }


}
