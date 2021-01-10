package group0110.ui.gui;

import group0110.ui.criteria.NewPasswordAnswerCriteria;
import group0110.ui.criteria.NewUsernameAnswerCriteria;
import group0110.ui.gui.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

class CreateAccountMenuController extends SecondaryUserMenuController {
    @FXML private TextField txtUsername;
    @FXML private PasswordField pfPassword;
    @FXML private Button btnSubmit;
    @FXML private ChoiceBox<String> choiceBox;

    CreateAccountMenuController(Stage previousMenu, Model model) {
        super(previousMenu, model);
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        try {
            String username = getUsername();
            String password = getPassword();
            String role = getRole();
            createAccount(username, password, role);
            model.loginUser(username);
            System.out.println("Created account with Username: " + username + " and Password: " + password);
            txtUsername.clear();
            pfPassword.clear();
            choiceBox.getSelectionModel().clearSelection();
        } catch (EmptyRoleException | EmptyUsernameException | EmptyPasswordException | UserLoginException e) {
            openError(e);
        }
    }

    void createAccount(String username, String password, String role) throws UserLoginException {
        NewUsernameAnswerCriteria newUsernameAnswerCriteria = new NewUsernameAnswerCriteria(model.um);
        NewPasswordAnswerCriteria newPasswordAnswerCriteria = new NewPasswordAnswerCriteria();
        if (!newUsernameAnswerCriteria.check(username)) {
            throw new NewUsernameException();
        } else if (!newPasswordAnswerCriteria.check(password)) {
            throw new NewPasswordException();
        }
        model.createAccount(username, password, role);
    }

    String getRole() throws EmptyRoleException {
        if (choiceBox.getValue() == null) {
            throw new EmptyRoleException();
        } else {
            return choiceBox.getValue();
        }
    }

    String getUsername() throws EmptyUsernameException {
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            throw new EmptyUsernameException();
        } else {
            return username;
        }
    }

    String getPassword() throws EmptyPasswordException {
        String password = pfPassword.getText();
        if (password.isEmpty()) {
            throw new EmptyPasswordException();
        } else {
            return password;
        }
    }
}
