package inncontrol.auth;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class LoginScreen extends VBox {
    public LoginScreen() {
        Label label = new Label("LoginScreen loaded.");
        this.getChildren().add(label);
    }
}
