package inncontrol.events;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class EventsScreen extends VBox {
    public EventsScreen() {
        Label label = new Label("EventsScreen loaded.");
        this.getChildren().add(label);
    }
}
