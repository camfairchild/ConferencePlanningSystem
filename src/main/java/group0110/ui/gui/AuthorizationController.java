package group0110.ui.gui;

import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import group0110.googleAuth.GoogleAuthorizer;
import group0110.ui.criteria.NewPasswordAnswerCriteria;
import group0110.ui.criteria.NewUsernameAnswerCriteria;
import group0110.ui.gui.exceptions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

abstract class AuthorizationController extends MenuController {
    @FXML TextField txtUsername;
    @FXML private PasswordField pfPassword;
    @FXML private Button btnSubmit, btnGoogle;
    @FXML private ChoiceBox<String> choiceBox;

    AuthorizationController(Model model) {
        super(model);
    }

    @FXML abstract void handleSubmit(ActionEvent event);

    @FXML protected void handleGoogle(ActionEvent event) throws UserLoginException {
        GoogleAuthorizer gAuth = new GoogleAuthorizer();
        String username;
        String password;
        String role;
        try {
            gAuth.open();
            username = gAuth.getUsername();
            password = gAuth.getPassword();
            // Authentication successful
            if (model.getUser(username) == null) {
                // If User does not exist
                role = getRole();
                createAccount(username, password, role);
            }

        } catch (EmptyRoleException | IOException | URISyntaxException
                | GeneralSecurityException | AuthorizationException e) {
            openError(e);
            return;
        }

        model.loginUser(username);
        openUserMenu(username);
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

    void openUserMenu(String username) {
        Stage stage = getStage(txtUsername);
        String role = model.getUser(username).getRole().name().toLowerCase();
        AttendeeMenuController controller;
        if (role.equals("attendee")) {
            System.out.println("Attendee Logged-in");
            controller = new AttendeeMenuController(model);
        } else if (role.equals("organizer")) {
            System.out.println("Organizer Logged-in");
            controller = new OrganizerMenuController(model);
        } else if (role.equals("vip")) {
            System.out.println("VIP Logged-in");
            controller = new VIPMenuController(model);
        } else if (role.equals("speaker")) {
            System.out.println("Speaker Logged-in");
            controller = new SpeakerMenuController(model);
        } else {
            System.out.println("Login messed up");
            return;
        }
        Stage newStage = openNewWindowAndClose(stage, role + "Menu",
                StringUtils.capitalize(role) + " Menu", controller);
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                controller.logout();
                System.out.println(((Stage) t.getSource()).getTitle() + " closed");
            }
        });
        stage.close();
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

    @FXML abstract void handleSwitch(ActionEvent event);
}
