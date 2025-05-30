package inncontrol;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MiniBarController {

    @FXML private TableView<MiniBarItem> minibarTable;
    @FXML private TableColumn<MiniBarItem, Integer> colBookingId;
    @FXML private TableColumn<MiniBarItem, String> colItem;
    @FXML private TableColumn<MiniBarItem, Integer> colQuantity;
    @FXML private TableColumn<MiniBarItem, Boolean> colCharged;

    @FXML private ComboBox<Integer> bookingBox;
    @FXML private ComboBox<String> itemBox;
    @FXML private TextField quantityField;

    private ObservableList<MiniBarItem> items = FXCollections.observableArrayList();

    private final String DB_URL = "jdbc:mysql://localhost:3306/inncontrol";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    @FXML
    public void initialize() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCharged.setCellValueFactory(new PropertyValueFactory<>("charged"));

        minibarTable.setItems(items);

        loadBookings();
        loadItems();
        loadData();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private void loadBookings() {
        bookingBox.getItems().clear();
        try (Connection conn = connect()) {
            String sql = "SELECT id FROM bookings ORDER BY id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookingBox.getItems().add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            showAlert("Error loading bookings: " + e.getMessage());
        }
    }

    private void loadItems() {
        itemBox.getItems().clear();
        try (Connection conn = connect()) {
            String sql = "SELECT name FROM minibar_items ORDER BY name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                itemBox.getItems().add(rs.getString("name"));
            }
        } catch (SQLException e) {
            showAlert("Error loading items: " + e.getMessage());
        }
    }

    private void loadData() {
        items.clear();
        try (Connection conn = connect()) {
            String sql = """
                SELECT mo.booking_id, mi.name, mo.quantity, mo.charged
                FROM minibar_orders mo
                JOIN minibar_items mi ON mo.item_id = mi.id
                ORDER BY mo.booking_id
                """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new MiniBarItem(
                    rs.getInt("booking_id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getBoolean("charged")
                ));
            }
        } catch (SQLException e) {
            showAlert("Error loading minibar data.");
        }
    }

    public void handleAdd() {
        Integer bookingId = bookingBox.getValue();
        String itemName = itemBox.getValue();
        String quantityText = quantityField.getText().trim();

        if (bookingId == null || itemName == null || quantityText.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showAlert("Quantity must be a valid number.");
            return;
        }

        try (Connection conn = connect()) {
            String getItemIdSql = "SELECT id FROM minibar_items WHERE name = ?";
            PreparedStatement ps1 = conn.prepareStatement(getItemIdSql);
            ps1.setString(1, itemName);
            ResultSet rs = ps1.executeQuery();
            if (!rs.next()) {
                showAlert("Selected item not found.");
                return;
            }
            int itemId = rs.getInt("id");

            String insertSql = "INSERT INTO minibar_orders (booking_id, item_id, quantity, charged) VALUES (?, ?, ?, false)";
            PreparedStatement ps2 = conn.prepareStatement(insertSql);
            ps2.setInt(1, bookingId);
            ps2.setInt(2, itemId);
            ps2.setInt(3, quantity);
            ps2.executeUpdate();

            showAlert("Item added!");
            quantityField.clear();
            loadData();

        } catch (SQLException e) {
            showAlert("Error adding minibar item: " + e.getMessage());
        }
    }

    public void handleCharge() {
        MiniBarItem selected = minibarTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select an item to charge.");
            return;
        }

        try (Connection conn = connect()) {
            String updateSql = "UPDATE minibar_orders SET charged = true WHERE booking_id = ? AND item_id = (SELECT id FROM minibar_items WHERE name = ?)";
            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setInt(1, selected.getBookingId());
            ps.setString(2, selected.getItem());
            int updated = ps.executeUpdate();

            if (updated > 0) {
                showAlert("Item charged!");
                loadData();
            } else {
                showAlert("Error charging item.");
            }
        } catch (SQLException e) {
            showAlert("Error charging item: " + e.getMessage());
        }
    }

    public void handleRemove() {
        MiniBarItem selected = minibarTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Select an item to remove.");
            return;
        }

        try (Connection conn = connect()) {
            String deleteSql = "DELETE FROM minibar_orders WHERE booking_id = ? AND item_id = (SELECT id FROM minibar_items WHERE name = ?)";
            PreparedStatement ps = conn.prepareStatement(deleteSql);
            ps.setInt(1, selected.getBookingId());
            ps.setString(2, selected.getItem());
            int deleted = ps.executeUpdate();

            if (deleted > 0) {
                showAlert("Item removed!");
                loadData();
            } else {
                showAlert("Error removing item.");
            }
        } catch (SQLException e) {
            showAlert("Error removing item: " + e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mini Bar");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}