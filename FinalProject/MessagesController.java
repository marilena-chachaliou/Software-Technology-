package inncontrol;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MessagesController {

    @FXML private TableView<Message> messagesTable;
    @FXML private TableColumn<Message, Integer> colId;
    @FXML private TableColumn<Message, String> colSender;
    @FXML private TableColumn<Message, String> colReceiver;
    @FXML private TableColumn<Message, String> colMessage;
    @FXML private TableColumn<Message, String> colDate;

    private ObservableList<Message> messages = FXCollections.observableArrayList();

    private final String DB_URL = "jdbc:mysql://localhost:3306/inncontrol";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSender.setCellValueFactory(new PropertyValueFactory<>("sender"));
        colReceiver.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        messagesTable.setItems(messages);

        loadMessages();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private void loadMessages() {
        messages.clear();
        try (Connection conn = connect()) {
            String query = "SELECT * FROM messages ORDER BY sent_at DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("id"),
                    rs.getString("sender_id"),
                    rs.getString("receiver_id"),
                    rs.getString("content"),
                    rs.getString("sent_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error loading messages.");
        }
    }

    public void handleNew() {
        showAlert("Send New Message (not implemented yet)");
    }

    public void handleDelete() {
        Message selected = messagesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a message to delete.");
            return;
        }

        try (Connection conn = connect()) {
            String delete = "DELETE FROM messages WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setInt(1, selected.getId());
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                loadMessages();
                showAlert("Message deleted.");
            } else {
                showAlert("Failed to delete message.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error deleting message.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Messages");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}