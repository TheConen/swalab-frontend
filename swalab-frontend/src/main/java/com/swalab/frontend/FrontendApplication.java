package com.swalab.frontend;

import com.swalab.frontend.gui.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FrontendApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private AbstractPaneContent _taskPaneContent;
    private AbstractPaneContent _customerPaneContent;
    private AbstractPaneContent _appointmentPaneContent;
    private OrdersAndPartsPaneContent _ordersAndPartPaneContent;

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(FrontendApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        Parent parent = createWindowContent();
        Scene scene = new Scene(parent, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Parent createWindowContent() {
        BorderPane pane = new BorderPane();

        // create button navigation for the top
        // Taps
        HBox navigationBox = new HBox(5);
        navigationBox.setPadding(new Insets(3,3,3,3));
        pane.setTop(navigationBox);
        navigationBox.setBorder(createBorder());

        Button appointmentsButton = new Button("Appointments");
        _appointmentPaneContent = new AppointmentOverview();
        appointmentsButton.setOnAction(ae -> changeContent(pane, _appointmentPaneContent));

        Button taskButton = new Button("Tasks");
        _taskPaneContent = new TaskPaneContent();
        taskButton.setOnAction(ae -> changeContent(pane, _taskPaneContent));

        Button customerButton = new Button("Customer");
        _customerPaneContent = new CustomerPaneContent();
        customerButton.setOnAction(ae -> changeContent(pane, _customerPaneContent));

        Button ordersAndPartsButton = new Button("Orders and Parts");
        _ordersAndPartPaneContent = new OrdersAndPartsPaneContent();
        ordersAndPartsButton.setOnAction(ae -> changeContent(pane, _ordersAndPartPaneContent));

        navigationBox.getChildren().addAll(appointmentsButton, taskButton, customerButton, ordersAndPartsButton);

        // start screen content
        changeContent(pane, _appointmentPaneContent);

        // just further information at the bottom line
        HBox footline = new HBox(2);
        footline.setBorder(createBorder());
        pane.setBottom(footline);
        Label user = new Label("Logged in as: Username");
        footline.getChildren().add(user);

        return pane;
    }

    private Border createBorder() {
        BorderStroke stroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0.5));
        return new Border(stroke);
    }

    private void changeContent(BorderPane pane, AbstractPaneContent content) {
        pane.setLeft(content.getMainWindowContent());
        pane.setCenter(content.getDescriptionWindowContent());
    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(FrontendApplication.class, args);
    }
}
