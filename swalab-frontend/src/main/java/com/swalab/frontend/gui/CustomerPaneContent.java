package com.swalab.frontend.gui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CustomerPaneContent extends  AbstractPaneContent {

    public Parent getMainWindowContent(){
        VBox box=new VBox();
        box.setPrefWidth(200);
        box.setBorder(createBorder());
        Label label=new Label("CUSTOMER - main");
        box.getChildren().add(label);
        return  box;
    }

    public Parent getDescriptionWindowContent(){
        VBox box=new VBox();
        Label label=new Label("CUSTOMER - description");
        box.getChildren().add(label);
        return  box;
    }
}
