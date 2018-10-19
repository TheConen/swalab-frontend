package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label label;

    @FXML
    private Button button;

    @FXML
    private void handleButton() {
        label.setText("Woah");
    }
}
