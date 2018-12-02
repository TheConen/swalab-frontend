package com.swalab.frontend;

import com.swalab.frontend.gui.*;
import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class FrontendApplication extends Application {


    private ConfigurableApplicationContext _springContext;
    private ApplicationSceneBuilder _applicationSceneBuilder;

    @Override
    public void init() throws Exception {
        _springContext = SpringApplication.run(FrontendApplication.class);
        String baseUrl = _springContext.getEnvironment().getProperty("backend.baseurl");

    }

    public String getBaseUrl() {
        return _springContext.getEnvironment().getProperty("backend.baseurl");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Task Overview");
        Parent parent = createLoginMask(primaryStage);
        Scene scene = new Scene(parent, 300, 200);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Parent createLoginMask(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        Label userName = new Label("Username");
        TextField textField = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        pane.addColumn(1, userName, passwordLabel);
        pane.addColumn(2, textField, passwordField, loginButton);
        _applicationSceneBuilder = new ApplicationSceneBuilder(this);
        loginButton.setOnAction(ae -> {
            Scene scene = new Scene(_applicationSceneBuilder.buildAndInitializeWorkingSites(), 800, 600);
            primaryStage.setScene(scene);
        });

        return pane;
    }


    @Override
    public void stop() throws Exception {
        _applicationSceneBuilder.unregisterUIModels();
        _springContext.stop();
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(FrontendApplication.class, args);
    }
}
