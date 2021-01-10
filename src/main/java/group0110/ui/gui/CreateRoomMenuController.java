package group0110.ui.gui;

import group0110.eventSystem.TimeInterval;
import group0110.eventSystem.exceptions.InvalidIntervalTimes;
import group0110.eventSystem.exceptions.RoomNotAvailable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.function.UnaryOperator;

class CreateRoomMenuController extends SecondaryUserMenuController {
    @FXML private TextField txtStartH;
    @FXML private TextField txtStartM;
    @FXML private TextField txtEndH;
    @FXML private TextField txtEndM;
    @FXML private TextField txtID;
    @FXML private TextField txtCapacity;

    CreateRoomMenuController(Stage previousMenu, Model model) {
        super(previousMenu, model);
    }

    @FXML
    private void initialize() {
        UnaryOperator<TextFormatter.Change> numFilter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        // Allows only numeric input on fields
        // Re-use of formatters is not allowed...
        txtCapacity.setTextFormatter(new TextFormatter<>(numFilter));
        txtEndH.setTextFormatter(new TextFormatter<>(numFilter));
        txtEndM.setTextFormatter(new TextFormatter<>(numFilter));
        txtStartH.setTextFormatter(new TextFormatter<>(numFilter));
        txtStartM.setTextFormatter(new TextFormatter<>(numFilter));
    }
    @FXML
    private void handleSubmit(ActionEvent event) {
        // Submit info to create room
        String roomId = txtID.getText();
        String startHour = txtStartH.getText();
        String startMinute = txtStartM.getText();
        String endHour = txtEndH.getText();
        String endMinute = txtEndM.getText();
        int maxCapacity = Integer.parseInt(txtCapacity.getText());
        try {
            LocalTime startTime = LocalTime.parse(startHour + ":" + startMinute);
            LocalTime endTime = LocalTime.parse(endHour + ":" + endMinute);
            TimeInterval availableTimeInterval = new TimeInterval(startTime, endTime);

            boolean created = model.getEventSystem().createRoom(roomId, availableTimeInterval, maxCapacity);
            if (!created) {
                throw new RoomNotAvailable("Room was not created");
            }
            System.out.println("Room created");
            clearFields();
        } catch ( InvalidIntervalTimes | DateTimeParseException | RoomNotAvailable e) {
            openError(e);
        }
    }

    private void clearFields() {
        // Clears all TextFields
        txtStartM.clear();
        txtStartH.clear();
        txtEndM.clear();
        txtEndH.clear();
        txtCapacity.clear();
        txtID.clear();
    }
}
