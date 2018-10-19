package com.swalab.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Controller {

    @FXML
    private Label label;

    @FXML
    private Button button;

    @FXML
    private void handleButton() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8080/hello", String.class);
        label.setText(response);
    }

}
