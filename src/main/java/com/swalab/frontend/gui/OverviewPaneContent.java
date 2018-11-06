package com.swalab.frontend.gui;

import com.swalab.frontend.INamedArtefact;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OverviewPaneContent extends AbstractPaneContent<INamedArtefact> {

    @Override
    public Parent getMainWindowContent() {
        BorderPane pane = new BorderPane();
        pane.setPrefWidth(200);
        pane.setBorder(createBorder());
        // what kind of elements should be shown here? please specify or change it
        ListView<?> listView = createListView();
        pane.setCenter(listView);
        return pane;
    }

    @Override
    public Parent getDescriptionWindowContent() {
        VBox box = new VBox();
        Label label = new Label("MAIN - description");
        box.getChildren().add(label);
        return box;
    }

    @Override
    protected void updateDescriptionContent(INamedArtefact item) {

    }

}
