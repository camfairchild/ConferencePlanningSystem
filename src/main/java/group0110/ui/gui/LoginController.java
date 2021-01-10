package group0110.ui.gui;

import group0110.ui.criteria.ExistingPasswordAnswerCriteria;
import group0110.ui.criteria.ExistingUsernameAnswerCriteria;
import group0110.ui.gui.exceptions.EmptyPasswordException;
import group0110.ui.gui.exceptions.EmptyUsernameException;
import group0110.ui.gui.exceptions.UserLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

class LoginController extends AuthorizationController {

    LoginController(Model model) {
        super(model);
    }

    @Override
    @FXML void handleSubmit(ActionEvent event) {
        String username;
        String password;
        //String role;
        try {
            username = getUsername();
            password = getPassword();
            //role = getRole();
            if (verifyLogin(username, password)) {//, role) {
                openUserMenu(username);
                model.loginUser(username);
            }
        } catch (EmptyUsernameException | EmptyPasswordException | UserLoginException e) {//| EmptyRoleException e) {
            openError(e);
            return;
        }
    }

    private boolean verifyLogin(String username, String password) throws UserLoginException {
        ExistingUsernameAnswerCriteria usernameAnswerCriteria = new ExistingUsernameAnswerCriteria(model.um);
        ExistingPasswordAnswerCriteria passwordCriteria = new ExistingPasswordAnswerCriteria(username, model.um);
        //UserRoleAnswerCriteria roleCriteria = new UserRoleAnswerCriteria(username, model.um);
            if (!usernameAnswerCriteria.check(username)) {
                throw new UserLoginException("Username does not exist. Please try again.");
            } else if (!passwordCriteria.check(password)) {
                throw new UserLoginException("Incorrect password. Please try again.");
            } /*else if (!roleCriteria.check(role)) {
                throw new UserLoginException("You do not have the correct role for that. Please try again.");
            }*/
            else {
               return true;
            }
    }

    @Override
    @FXML
    void handleSwitch(ActionEvent event) {
        // Switch to the signup menu
        Stage stage = getStage(txtUsername);
        SignupController signupController = new SignupController(model);
        openNewWindowAndClose(stage, "signupMenu", "Create Account", signupController);
        stage.close();
    }
}
