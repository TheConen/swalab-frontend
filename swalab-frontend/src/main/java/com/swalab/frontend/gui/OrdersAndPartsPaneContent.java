package com.swalab.frontend.gui;

import com.swalab.frontend.INamedArtefact;
import com.swalab.frontend.model.Customer;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OrdersAndPartsPaneContent extends  AbstractPaneContent<INamedArtefact> {

    public Parent getMainWindowContent(){
        BorderPane pane=new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        ListView<INamedArtefact> listView = createListView();
        pane.setCenter(listView);
        return  pane;
    }

    public Parent getDescriptionWindowContent(){
        VBox box=new VBox();
        Label label=new Label("ORDERS AND PARTS - description");
        box.getChildren().add(label);
        return  box;
    }

    @Override
    protected void updateDescriptionContent(INamedArtefact item) {

    }
}
