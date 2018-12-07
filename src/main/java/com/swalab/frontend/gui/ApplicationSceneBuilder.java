package com.swalab.frontend.gui;

import com.swalab.frontend.FrontendApplication;
import com.swalab.frontend.controller.SynchController;
import com.swalab.frontend.model.*;
import com.swalab.frontend.util.AutomatedSceneSwitcher;
import com.swalab.frontend.util.HyperlinkRefresher;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class ApplicationSceneBuilder {
    private static SynchController _synchController;
    private final FrontendApplication _frontendApplication;
    private AbstractPaneContent _taskPaneContent;
    private AbstractPaneContent _customerPaneContent;
    private AbstractPaneContent _appointmentPaneContent;
    private OrdersAndPartsPaneContent _ordersAndPartPaneContent;

    public ApplicationSceneBuilder(FrontendApplication frontendApplication) {
        _frontendApplication = frontendApplication;
    }

    public Parent buildAndInitializeWorkingSites() {
        String baseUrl = _frontendApplication.getBaseUrl();
        _synchController = new SynchController(baseUrl, "noJs");
        Parent parent = createWindowContent();
        _synchController.updateTechnician();
        return parent;
    }

    private Parent createWindowContent() {
        BorderPane pane = new BorderPane();

        AutomatedSceneSwitcher sceneSwitcher = new AutomatedSceneSwitcher();
        HyperlinkRefresher hyperlinkRefresher = new HyperlinkRefresher(sceneSwitcher);

        // create button navigation for the top
        // Taps
        BorderPane buttonPane = new BorderPane();
        HBox navigationBox = new HBox(5);
        buttonPane.setLeft(navigationBox);
        navigationBox.setPadding(new Insets(3, 3, 3, 3));
        pane.setTop(buttonPane);
        buttonPane.setBorder(createBorder());

        HBox synchronizationBox = new HBox(5);
        buttonPane.setRight(synchronizationBox);
        synchronizationBox.setPadding(new Insets(3, 3, 3, 3));
        Button toServerSyncButton = new Button("Local ->");
        toServerSyncButton.setOnAction(ae -> _synchController.persistData());
        Button fromServerSyncButton = new Button("-> Local");
        fromServerSyncButton.setOnAction(ae -> _synchController.loadData());
        synchronizationBox.getChildren().addAll(toServerSyncButton, fromServerSyncButton);
        synchronizationBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        ToggleButton appointmentsButton = new ToggleButton("Appointments");
        _appointmentPaneContent = new AppointmentOverview(_synchController,hyperlinkRefresher);
        appointmentsButton.setOnAction(ae -> changeContent(pane, _appointmentPaneContent));
        sceneSwitcher.addElements(Appointment.class,_appointmentPaneContent,appointmentsButton);

        ToggleButton taskButton = new ToggleButton("Tasks");
        _taskPaneContent = new TaskPaneContent(_synchController);
        taskButton.setOnAction(ae -> changeContent(pane, _taskPaneContent));
        sceneSwitcher.addElements(AbstractTaskAndNote.class,_taskPaneContent,taskButton);

        ToggleButton customerButton = new ToggleButton("Customer");
        _customerPaneContent = new CustomerPaneContent(_synchController);
        customerButton.setOnAction(ae -> changeContent(pane, _customerPaneContent));
        sceneSwitcher.addElements(Customer.class,_customerPaneContent,customerButton);

        ToggleButton ordersAndPartsButton = new ToggleButton("Orders and Parts");
        _ordersAndPartPaneContent = new OrdersAndPartsPaneContent(_synchController);
        ordersAndPartsButton.setOnAction(ae -> changeContent(pane, _ordersAndPartPaneContent));
        sceneSwitcher.addElements(AbstractTaskAndNote.class,_ordersAndPartPaneContent,ordersAndPartsButton);

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
        Consumer<Technician> consumer = t -> userLabel.setText(t.getName());
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

    public void unregisterUIModels() {
        if (_taskPaneContent != null)
            _taskPaneContent.removeListener(_synchController);
        if (_appointmentPaneContent != null)
            _appointmentPaneContent.removeListener(_synchController);
        if (_customerPaneContent != null)
            _customerPaneContent.removeListener(_synchController);
        if (_ordersAndPartPaneContent != null)
            _ordersAndPartPaneContent.removeListener(_synchController);
    }


}
