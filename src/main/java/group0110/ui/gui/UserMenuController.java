package group0110.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

abstract class UserMenuController extends MenuController {
     UserMenuController(Model model) {
        super(model);
    }

    @FXML void handleLogout(ActionEvent event) {
        logout();
    }

     void logout() {
        model.logoutUser();
        model.save();
        LoginController loginController = new LoginController(model);
        Stage newStage = openNewWindowAndClose(getStage(), "loginMenu", "Login Menu", loginController);
        newStage.setOnCloseRequest(t -> {
            System.out.println("Closed Login Window");
            exit();
        });
    }

    abstract void handleBack(ActionEvent event);
}

