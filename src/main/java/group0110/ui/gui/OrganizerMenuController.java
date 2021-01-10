package group0110.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

class OrganizerMenuController extends AttendeeMenuController {
    protected OrganizerMenuController(Model model) {
        super(model);
    }

    @FXML protected void handleScheduleEvent(ActionEvent event) {
        Stage stage = getStage();
        ScheduleEventController scheduleEventController = new ScheduleEventController(stage, model);
        openMenu(stage, "scheduleEventMenu", "Schedule Event", scheduleEventController);
    }

    @FXML protected void handleCreateAccount(ActionEvent event) {
        Stage stage = getStage();
        CreateAccountMenuController createAccountMenuController = new CreateAccountMenuController(stage, model);
        openMenu(getStage(), "createAccountMenu", "Create Account Menu", createAccountMenuController);
    }

    @FXML protected void handleCreateRoom(ActionEvent event) {
        Stage stage = getStage();
        CreateRoomMenuController createRoomMenuController = new CreateRoomMenuController(stage, model);
        openMenu(getStage(), "createRoomMenu", "Create Room Menu", createRoomMenuController);
    }

    @FXML protected void handleAllRooms(ActionEvent event) {
        Stage stage = getStage();
        RoomMenuController roomMenuController = new RoomMenuController(stage, model);
        openMenu(stage, "roomsMenu", "All Rooms Menu", roomMenuController);
    }
}
