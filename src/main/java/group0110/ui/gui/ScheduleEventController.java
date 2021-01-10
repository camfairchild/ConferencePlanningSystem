package group0110.ui.gui;

import group0110.eventSystem.DateTimeInterval;
import group0110.eventSystem.EventType;
import group0110.eventSystem.Room;
import group0110.eventSystem.exceptions.ExceedingRoomMaxCapacity;
import group0110.eventSystem.exceptions.InvalidIntervalTimes;
import group0110.eventSystem.exceptions.RoomIDNotFound;
import group0110.eventSystem.exceptions.RoomNotAvailable;
import group0110.ui.gui.exceptions.EmptyFieldException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

class ScheduleEventController extends SecondaryUserMenuController {
    @FXML
    private ChoiceBox<EventType> eventType;
    @FXML
    private TextField eventTitle;
    @FXML
    private ChoiceBox<String> cbRoomID;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField startHour;
    @FXML
    private TextField startMinute;
    @FXML
    private TextField endHour;
    @FXML
    private TextField endMinute;
    @FXML
    private TextField maxCap;
    @FXML
    private Text invalidTimeText;
    private List<String> allRoomIDS = new ArrayList<String>();

    protected ScheduleEventController(Stage previousMenu, Model model) {
        super(previousMenu, model);
    }

    @FXML
    protected void initialize() {
        ArrayList<EventType> choices = new ArrayList<>();
        choices.add(EventType.TALK);
        choices.add(EventType.PARTY);
        choices.add(EventType.DISCUSSION);
        choices.add(EventType.VIP);
        eventType.setItems(FXCollections.observableList(choices));
        List<String> roomIDs = (model.getEventSystem().getAllRooms()
                .stream() // Convert list to stream
                .map(Room::getId) // Map each Room to room.GetId()
                .collect(Collectors.toList())); // Collect the mapped values to a list
        cbRoomID.setItems(FXCollections.observableList(roomIDs));

        UnaryOperator<TextFormatter.Change> numFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        // Allows only numeric input on fields
        // Re-use of formatters is not allowed...
        startHour.setTextFormatter(new TextFormatter<>(numFilter));
        endHour.setTextFormatter(new TextFormatter<>(numFilter));

        startMinute.setTextFormatter(new TextFormatter<>(numFilter));
        endMinute.setTextFormatter(new TextFormatter<>(numFilter));

        maxCap.setTextFormatter(new TextFormatter<>(numFilter));
    }

    @FXML
    protected void handleSubmit() {
        try {
            fieldsChecker();
            String eventTitle_ = eventTitle.getText();
            String roomID_ = cbRoomID.getValue();
            int maxCap_ = Integer.parseInt(maxCap.getText());
            EventType eventType_ = eventType.getSelectionModel().getSelectedItem();

            int startH = Integer.parseInt(startHour.getText());
            int startM = Integer.parseInt(startMinute.getText());
            int endH = Integer.parseInt(endHour.getText());
            int endM = Integer.parseInt(endMinute.getText());
            LocalTime start = LocalTime.of(startH, startM);
            LocalTime end = LocalTime.of(endH, endM);
            // date validated here
            LocalDate date = datePicker.getValue();
            DateTimeInterval dateTimeInterval = new DateTimeInterval(date, start, end);
            model.getEventSystem().createEvent(eventType_, dateTimeInterval, roomID_, eventTitle_, maxCap_);
                System.out.println("Event created: " + eventTitle_);
                openSuccess("Event successfully scheduled!");
                clearFields();
        } catch (EmptyFieldException | InvalidIntervalTimes | RoomIDNotFound | RoomNotAvailable | ExceedingRoomMaxCapacity e) {
            openError(e);
        }
    }

    private void fieldsChecker() throws EmptyFieldException {
        if (eventTitle.getText().isEmpty() ||
                eventType.getSelectionModel().isEmpty() ||
                cbRoomID.getSelectionModel().isEmpty() ||
                startHour.getText().isEmpty() ||
                startMinute.getText().isEmpty() ||
                endHour.getText().isEmpty() ||
                endMinute.getText().isEmpty() ||
                maxCap.getText().isEmpty() ||
                datePicker.getEditor().getText().isEmpty()) {
            throw new EmptyFieldException("You cannot leave a field empty.");
        }
    }

    private void clearFields() {
        // Clears all TextFields
        eventType.getSelectionModel().clearSelection();
        eventTitle.clear();
        cbRoomID.getSelectionModel().clearSelection();
        datePicker.getEditor().clear();
        startHour.clear();
        startMinute.clear();
        endHour.clear();
        endMinute.clear();
        maxCap.clear();
    }
}
