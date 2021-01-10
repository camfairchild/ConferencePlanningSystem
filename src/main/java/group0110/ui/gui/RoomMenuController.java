package group0110.ui.gui;

import group0110.entities.Role;
import group0110.eventSystem.Room;
import group0110.eventSystem.TimeInterval;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

class RoomMenuController extends SecondaryUserMenuController {
    @FXML private TableColumn<Room, String> tcId;
    @FXML private TableColumn<Room, Integer> tcCapacity;
    @FXML private TableColumn<Room, TimeInterval> tcAvailableTime;
    @FXML private TableColumn<Room, String> tcBookedTimes;
    @FXML private TableView<Room> tbview;
    private ObservableList<Room> roomList;

    @FXML private Menu mnuOpt1;
    @FXML private Menu mnuOpt2;
    @FXML private Menu mnuOpt3;

    protected RoomMenuController(Stage previousMenu, Model model) {
        super(previousMenu, model);
    }

    @FXML
    private void initialize() {
        // Runs on scene load
        // See MessagesMenuController
        List<Room> rooms = model.getEventSystem().getAllRooms();
        roomList = FXCollections.observableList(rooms);
        tcId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        tcCapacity.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMaxCapacity()));
        tcAvailableTime.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAvailableTimeInterval()));
        tcBookedTimes.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getBookedTimeIntervals().toString()));
        tbview.setItems(roomList);
        // If user is organizer, enable option 1. Default is disabled.
        if (model.getLoggedInUser().getRole().equals(Role.ORGANIZER)) {
            mnuOpt1.setDisable(false);
        }
    }

    @FXML
    private void handleOption1(ActionEvent event) {
        // Create Room. Only available to Organizer
        CreateRoomMenuController createRoomMenuController = new CreateRoomMenuController(getStage(), model);
        openMenu(getStage(), "createRoomMenu", "Create Room Menu", createRoomMenuController);
    }
}
