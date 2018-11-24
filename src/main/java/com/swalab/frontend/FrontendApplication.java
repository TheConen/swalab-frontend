package com.swalab.frontend;

import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.gui.*;
import com.swalab.frontend.model.Technician;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.function.Consumer;

@SpringBootApplication
public class FrontendApplication extends Application {

    private static SynchController _synchController;
    private ConfigurableApplicationContext springContext;
    private AbstractPaneContent _taskPaneContent;
    private AbstractPaneContent _customerPaneContent;
    private AbstractPaneContent _appointmentPaneContent;
    private OrdersAndPartsPaneContent _ordersAndPartPaneContent;
    private String baseUrl;

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(FrontendApplication.class);
        baseUrl = springContext.getEnvironment().getProperty("backend.baseurl");
        _synchController = new SynchController(baseUrl, "noJs");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Task Overview");
        Parent parent = createWindowContent();
        Scene scene = new Scene(parent, 800, 600);

        _synchController.updateTechnician();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Parent createWindowContent() {
        BorderPane pane = new BorderPane();

        // create button navigation for the top
        // Taps
        BorderPane buttonPane=new BorderPane();
        HBox navigationBox = new HBox(5);
        buttonPane.setLeft(navigationBox);
        navigationBox.setPadding(new Insets(3, 3, 3, 3));
        pane.setTop(buttonPane);
        buttonPane.setBorder(createBorder());

        HBox synchronizationBox=new HBox(5);
        buttonPane.setRight(synchronizationBox);
        synchronizationBox.setPadding(new Insets(3,3,3,3));
        Button toServerSyncButton=new Button("Local ->");
        toServerSyncButton.setOnAction(ae->_synchController.persistData());
        Button fromServerSyncButton=new Button("-> Local");
        fromServerSyncButton.setOnAction(ae->_synchController.loadData());
        synchronizationBox.getChildren().addAll(toServerSyncButton,fromServerSyncButton);
        synchronizationBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        ToggleButton appointmentsButton = new ToggleButton("Appointments");
        _appointmentPaneContent = new AppointmentOverview(_synchController);
        appointmentsButton.setOnAction(ae -> changeContent(pane, _appointmentPaneContent));

        ToggleButton taskButton = new ToggleButton("Tasks");
        _taskPaneContent = new TaskPaneContent(_synchController);
        taskButton.setOnAction(ae -> changeContent(pane, _taskPaneContent));

        ToggleButton customerButton = new ToggleButton("Customer");
        _customerPaneContent = new CustomerPaneContent(_synchController);
        customerButton.setOnAction(ae -> changeContent(pane, _customerPaneContent));

        ToggleButton ordersAndPartsButton = new ToggleButton("Orders and Parts");
        _ordersAndPartPaneContent = new OrdersAndPartsPaneContent(_synchController);
        ordersAndPartsButton.setOnAction(ae -> changeContent(pane, _ordersAndPartPaneContent));

        navigationBox.getChildren().addAll(appointmentsButton, taskButton, customerButton, ordersAndPartsButton);

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(appointmentsButton, taskButton, customerButton, ordersAndPartsButton);
        toggleGroup.selectToggle(appointmentsButton);

        // start screen content
        changeContent(pane, _appointmentPaneContent);

        // just further information at the bottom line
        HBox footline = new HBox(2);
        footline.setBorder(createBorder());
        pane.setBottom(footline);
        String user = System.getProperty("user.name");
        Label userDescriptionLabel = new Label("Logged in as: ");
        Label userLabel = new Label(user);
        Consumer<Technician> consumer=t->userLabel.setText(t.getName());
        _synchController.registerModelForUpdate(consumer);
        footline.getChildren().addAll(userDescriptionLabel, userLabel);


        return pane;
    }

    private Border createBorder() {
        BorderStroke stroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0.5));
        return new Border(stroke);
    }

    private void changeContent(BorderPane pane, AbstractPaneContent content) {
        pane.setLeft(content.getMainWindowContent());
        pane.setCenter(content.getDescriptionWindowContent());
        content.requestFocus();
    }

    @Override
    public void stop() throws Exception {
        unregisterUIModels();

        springContext.stop();
        System.exit(0);
    }

    private void unregisterUIModels() {
        _taskPaneContent.removeListener(_synchController);
        _appointmentPaneContent.removeListener(_synchController);
        _customerPaneContent.removeListener(_synchController);
        _ordersAndPartPaneContent.removeListener(_synchController);
    }


    public static void main(String[] args) {
        launch(FrontendApplication.class, args);

        //synchController = new SynchController("http://localhost:8080", "noJs");
        //synchController.getDataFromServer();
        //synchController.saveDataToFile();
        //synchController.loadDateFromFile();
        //synchController.sendDataToServer();
    }
}
