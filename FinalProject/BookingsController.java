package inncontrol;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class BookingsController {

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> colGuest;
    @FXML private TableColumn<Booking, String> colRoom;
    @FXML private TableColumn<Booking, String> colCheckIn;
    @FXML private TableColumn<Booking, String> colCheckOut;

    @FXML private TextField guestNameField;
    @FXML private TextField roomNumberField;
    @FXML private DatePicker checkInPicker;
    @FXML private DatePicker checkOutPicker;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button btnAdd;
    @FXML private Button editBookingButton;
    @FXML private Button deleteBookingButton;

    private ObservableList<Booking> bookings = FXCollections.observableArrayList();

    private final String DB_URL = "jdbc:mysql://localhost:3306/inncontrol";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    private Booking selectedBooking = null;
    private boolean isEditing = false;

    @FXML
    public void initialize() {
        colGuest.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));

        bookingTable.setItems(bookings);
        loadBookingsFromDB();

        bookingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedBooking = newSel;
            if (newSel != null) {
                populateForm(newSel);
                setEditingState(true);
            }
        });

        setEditingState(false);
    }

    private void populateForm(Booking booking) {
        guestNameField.setText(booking.getGuestName());
        roomNumberField.setText(booking.getRoomNumber());
        checkInPicker.setValue(java.time.LocalDate.parse(booking.getCheckIn()));
        checkOutPicker.setValue(java.time.LocalDate.parse(booking.getCheckOut()));
    }

    private void clearForm() {
        guestNameField.clear();
        roomNumberField.clear();
        checkInPicker.setValue(null);
        checkOutPicker.setValue(null);
        bookingTable.getSelectionModel().clearSelection();
        selectedBooking = null;
    }

    private void setEditingState(boolean editing) {
        isEditing = editing;
        saveButton.setText(editing ? "Update Booking" : "Save Booking");
        cancelButton.setVisible(editing);
        btnAdd.setDisable(editing);
        editBookingButton.setDisable(!editing);
        deleteBookingButton.setDisable(!editing);
    }

    private void loadBookingsFromDB() {
        bookings.clear();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT b.check_in, b.check_out, u.full_name AS guestName, r.room_number AS roomNumber " +
                         "FROM bookings b " +
                         "LEFT JOIN users u ON b.user_id = u.id " +
                         "LEFT JOIN rooms r ON b.room_id = r.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getString("guestName"),
                    rs.getString("roomNumber"),
                    rs.getString("check_in"),
                    rs.getString("check_out")
                ));
            }
        } catch (SQLException e) {
            showAlert("Error loading bookings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void saveBooking() {
        String guestName = guestNameField.getText().trim();
        String roomNumber = roomNumberField.getText().trim();
        String checkIn = checkInPicker.getValue() != null ? checkInPicker.getValue().toString() : "";
        String checkOut = checkOutPicker.getValue() != null ? checkOutPicker.getValue().toString() : "";

        if (guestName.isEmpty() || roomNumber.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // Get or insert user
            int userId = getUserIdByName(conn, guestName);
            if (userId == -1) {
                userId = insertUser(conn, guestName);
            }

            // Get room id
            int roomId = getRoomIdByNumber(conn, roomNumber);
            if (roomId == -1) {
                showAlert("Room not found.");
                return;
            }

            if (isEditing && selectedBooking != null) {
                // Update existing booking
                String updateSql = "UPDATE bookings SET user_id = ?, room_id = ?, check_in = ?, check_out = ? " +
                                   "WHERE user_id = ? AND room_id = ? AND check_in = ? AND check_out = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, userId);
                updateStmt.setInt(2, roomId);
                updateStmt.setString(3, checkIn);
                updateStmt.setString(4, checkOut);
                // Old keys
                int oldUserId = getUserIdByName(conn, selectedBooking.getGuestName());
                int oldRoomId = getRoomIdByNumber(conn, selectedBooking.getRoomNumber());
                updateStmt.setInt(5, oldUserId);
                updateStmt.setInt(6, oldRoomId);
                updateStmt.setString(7, selectedBooking.getCheckIn());
                updateStmt.setString(8, selectedBooking.getCheckOut());

                int rows = updateStmt.executeUpdate();
                if (rows > 0) {
                    showAlert("Booking updated!");
                    loadBookingsFromDB();
                    clearForm();
                    setEditingState(false);
                } else {
                    showAlert("Failed to update booking.");
                }
            } else {
                // Insert new booking
                String insertSql = "INSERT INTO bookings (user_id, room_id, check_in, check_out, guests) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, roomId);
                insertStmt.setString(3, checkIn);
                insertStmt.setString(4, checkOut);
                insertStmt.setInt(5, 1); 
                insertStmt.executeUpdate();

                showAlert("Booking saved!");
                loadBookingsFromDB();
                clearForm();
            }
        } catch (SQLException e) {
            showAlert("Error saving booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        clearForm();
        setEditingState(false);
    }

    @FXML
    private void handleEdit() {
        if (selectedBooking == null) {
            showAlert("Select a booking to edit.");
            return;
        }
        setEditingState(true);
    }

    @FXML
    private void handleDelete() {
        if (selectedBooking == null) {
            showAlert("Select a booking to delete.");
            return;
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            int userId = getUserIdByName(conn, selectedBooking.getGuestName());
            int roomId = getRoomIdByNumber(conn, selectedBooking.getRoomNumber());

            String deleteSql = "DELETE FROM bookings WHERE user_id = ? AND room_id = ? AND check_in = ? AND check_out = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteSql);
            stmt.setInt(1, userId);
            stmt.setInt(2, roomId);
            stmt.setString(3, selectedBooking.getCheckIn());
            stmt.setString(4, selectedBooking.getCheckOut());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showAlert("Booking deleted.");
                loadBookingsFromDB();
                clearForm();
                setEditingState(false);
            } else {
                showAlert("Failed to delete booking.");
            }
        } catch (SQLException e) {
            showAlert("Error deleting booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int getUserIdByName(Connection conn, String fullName) throws SQLException {
        String sql = "SELECT id FROM users WHERE full_name = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, fullName);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt("id") : -1;
    }

    private int getRoomIdByNumber(Connection conn, String roomNumber) throws SQLException {
        String sql = "SELECT id FROM rooms WHERE room_number = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, roomNumber);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt("id") : -1;
    }

    private int insertUser(Connection conn, String fullName) throws SQLException {
        String insertSql = "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, fullName.toLowerCase().replaceAll("\\s+", ""));
        stmt.setString(2, "default123");
        stmt.setString(3, fullName);
        stmt.setString(4, "guest");
        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        } else {
            throw new SQLException("Failed to retrieve user ID.");
        }
    }

    @FXML
    private void handleCancel() {
        clearForm();
        setEditingState(false);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bookings");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}