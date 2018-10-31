package com.swalab.frontend.gui;

import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public abstract class AbstractPaneContent {

    public abstract Parent getMainWindowContent();

    public abstract Parent getDescriptionWindowContent();

    protected Border createBorder(){
        BorderStroke stroke=new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(1),new BorderWidths(1));
        return new Border(stroke);
    }
}
