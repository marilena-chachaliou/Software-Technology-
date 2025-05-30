package inncontrol;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventsController {

    @FXML private TableView<Event> eventsTable;
    @FXML private TableColumn<Event, String> colEvent;
    @FXML private TableColumn<Event, String> colLocation;
    @FXML private TableColumn<Event, String> colDate;
    @FXML private TableColumn<Event, String> colOrganizer;

    @FXML private TextField inputTitle;
    @FXML private TextField inputLocation;
    @FXML private DatePicker inputDate;
    @FXML private TextField inputOrganizer;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;

    private ObservableList<Event> events = FXCollections.observableArrayList();
    private Event selectedEvent = null;

    @FXML
    public void initialize() {
        colEvent.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrganizer.setCellValueFactory(new PropertyValueFactory<>("organizer"));

        eventsTable.setItems(events);

        // Sample data
        events.add(new Event("Wedding Reception", "Main Hall", "2025-07-15", "Mr. Smith"));

        // Listen for selection changes
        eventsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                selectedEvent = newSelection;
                btnAdd.setVisible(false);
                btnUpdate.setVisible(true);
                btnCancel.setVisible(true);

                // Populate form fields
                inputTitle.setText(selectedEvent.getEventName());
                inputLocation.setText(selectedEvent.getLocation());
                inputDate.setValue(java.time.LocalDate.parse(selectedEvent.getDate()));
                inputOrganizer.setText(selectedEvent.getOrganizer());
            }
        });
    }

    public void handleAddSimple() {
        if (inputTitle.getText().isEmpty() || inputLocation.getText().isEmpty() || 
            inputDate.getValue() == null || inputOrganizer.getText().isEmpty()) {
            showAlert("Please fill all fields before adding.");
            return;
        }

        Event newEvent = new Event(
            inputTitle.getText(),
            inputLocation.getText(),
            inputDate.getValue().toString(),
            inputOrganizer.getText()
        );
        events.add(newEvent);
        clearForm();
        showAlert("Event added.");
    }

    public void handleEdit() {
        if (selectedEvent == null) {
            showAlert("Select an event to edit.");
            return;
        }
        // Fields are already populated on selection
    }

    public void handleUpdate() {
        if (selectedEvent == null) {
            showAlert("No event selected for update.");
            return;
        }
        if (inputTitle.getText().isEmpty() || inputLocation.getText().isEmpty() || 
            inputDate.getValue() == null || inputOrganizer.getText().isEmpty()) {
            showAlert("Please fill all fields before updating.");
            return;
        }
        // Update selected event (for a real app you'd update DB here)
        selectedEvent = new Event(
            inputTitle.getText(),
            inputLocation.getText(),
            inputDate.getValue().toString(),
            inputOrganizer.getText()
        );

        int idx = eventsTable.getSelectionModel().getSelectedIndex();
        events.set(idx, selectedEvent);
        clearForm();
        showAlert("Event updated.");

        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnCancel.setVisible(false);
    }

    public void handleCancel() {
        clearForm();
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
        btnCancel.setVisible(false);
        eventsTable.getSelectionModel().clearSelection();
        selectedEvent = null;
    }

    public void handleDelete() {
        Event selected = eventsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an event to delete.");
            return;
        }
        events.remove(selected);
        clearForm();
        showAlert("Event deleted.");
    }

    private void clearForm() {
        inputTitle.clear();
        inputLocation.clear();
        inputDate.setValue(null);
        inputOrganizer.clear();
        eventsTable.getSelectionModel().clearSelection();
        selectedEvent = null;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Events");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}