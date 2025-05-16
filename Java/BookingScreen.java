package inncontrol.booking;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class BookingScreen extends VBox {
    public BookingScreen() {
        Label label = new Label("BookingScreen loaded.");
        this.getChildren().add(label);
    }
}
