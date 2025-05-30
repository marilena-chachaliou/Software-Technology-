package inncontrol;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class DashboardController {

    @FXML private AnchorPane contentPane;

    private void loadUI(String fxml) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/InnControlProject/" + fxml));
            contentPane.getChildren().setAll(node);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openBookings() {
        loadUI("Bookings.fxml");
    }

    @FXML
    public void openRooms() {
        loadUI("Rooms.fxml");
    }

    @FXML
    public void openRoomService() {
        loadUI("RoomService.fxml");
    }

    @FXML
    public void openMiniBar() {
        loadUI("MiniBar.fxml");
    }

    @FXML
    public void openMaintenance() {
        loadUI("Maintenance.fxml");
    }

    @FXML
    public void openMessages() {
        loadUI("Messages.fxml");
    }

    @FXML
    public void openStaffLeave() {
        loadUI("StaffLeave.fxml");
    }

    @FXML
    public void openEvents() {
        loadUI("Events.fxml");
    }
}