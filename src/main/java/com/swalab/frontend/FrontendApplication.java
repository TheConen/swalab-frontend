package com.swalab.frontend;

import com.swalab.frontend.gui.ApplicationSceneBuilder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    public void init() {
        _springContext = SpringApplication.run(FrontendApplication.class);

    }

    public String getBaseUrl() {
        return _springContext.getEnvironment().getProperty("backend.baseurl");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Overview");
        Parent parent = createLoginMask(primaryStage);
        Scene scene = new Scene(parent, 300, 200);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Parent createLoginMask(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(3,3,3,3));
        pane.setHgap(5);
        pane.setVgap(5);
        EventHandler submitHandler= ae-> {
            Scene scene = new Scene(_applicationSceneBuilder.buildAndInitializeWorkingSites(), 800, 600);
            primaryStage.setScene(scene);
        };
        Label userName = new Label("Username");
        TextField textField = new TextField("noJs@swa.lab");
        textField.setOnAction(submitHandler);
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setOnAction(submitHandler);
        passwordField.setText("secure?");
        Button loginButton = new Button("Login");
        pane.addColumn(1, userName, passwordLabel);
        pane.addColumn(2, textField, passwordField, loginButton);
        _applicationSceneBuilder = new ApplicationSceneBuilder(this);
        loginButton.setOnAction(submitHandler);


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
