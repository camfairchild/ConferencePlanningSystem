package group0110.ui.gui;

import group0110.ui.gui.exceptions.EmptyPasswordException;
import group0110.ui.gui.exceptions.EmptyRoleException;
import group0110.ui.gui.exceptions.EmptyUsernameException;
import group0110.ui.gui.exceptions.UserLoginException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

class SignupController extends AuthorizationController {

    SignupController(Model model) {
        super(model);
    }

    @FXML protected void handleSubmit(ActionEvent event) {
        try {
            String username = getUsername();
            String password = getPassword();
            String role = getRole();
            createAccount(username, password, role);
            model.loginUser(username);
            openUserMenu(username);
        } catch (EmptyRoleException | EmptyUsernameException | EmptyPasswordException | UserLoginException e) {
            openError(e);
        }
    }

    @Override @FXML protected void handleSwitch(ActionEvent event) {
        // Switch to the login menu
        Stage stage = getStage(txtUsername);
        LoginController loginController = new LoginController(model);
        Stage newStage = openNewWindowAndClose(stage, "loginMenu", "Login Menu", loginController);
        stage.close();
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.out.println("Closed Login Window");
                exit();
            }
        });
    }
}
