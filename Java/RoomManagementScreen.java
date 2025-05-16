package inncontrol.rooms;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class RoomManagementScreen extends VBox {
    public RoomManagementScreen() {
        Label label = new Label("RoomManagementScreen loaded.");
        this.getChildren().add(label);
    }
}
