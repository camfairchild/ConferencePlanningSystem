package group0110.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

class AttendeeMenuController extends UserMenuController {
    AttendeeMenuController(Model model) {
        super(model);
    }

    @FXML
    @Override
    void handleBack(ActionEvent event) {
        handleLogout(event);
    }

    @FXML protected void handleMessagesMenu(ActionEvent event) {
        Stage stage = getStage();
        MessagesMenuController messagesMenuController = new MessagesMenuController(stage, model);
        openMenu(stage, "messageMenu", "Messages Menu", messagesMenuController);
    }

    @FXML protected void handleAllEvent(ActionEvent event) {
        Stage stage = getStage();
        EventDisplayController eventDisplayController = new EventDisplayController(stage, model);
        openMenu(stage, "eventsMenu", "Events Menu", eventDisplayController);
    }

    @FXML protected void handleSuggestions(ActionEvent event) {
        Stage stage = getStage();
        SuggestionMenuController suggestionMenuController = new SuggestionMenuController(stage, model);
        openMenu(stage, "suggestionMenu", "Suggestion Menu", suggestionMenuController);
    }

    @FXML protected void handleRequests(ActionEvent event) {
        Stage stage = getStage();
        RequestMenuController requestMenuController = new RequestMenuController(stage, model);
        openMenu(stage, "requestMenu", "Requests Menu", requestMenuController);
    }

    void openMenu(Stage parentStage, String fxml, String title, SecondaryUserMenuController controller) {
        Stage newStage =  super.openNewWindowAndClose(parentStage, fxml, title, controller);
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                controller.back();
                System.out.println(((Stage) t.getSource()).getTitle() + " closed");
            }
        });
    }
}
