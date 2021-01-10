package group0110.ui.gui;

import group0110.entities.User;
import group0110.entities.UserRequest;
import group0110.entities.UserRequestCategory;
import group0110.entities.UserRequestStatus;
import group0110.ui.gui.exceptions.EmptyFieldException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

class RequestMenuController extends SecondaryUserMenuController {
    @FXML
    private TableColumn<UserRequest, String> tcRequest;
    @FXML private TableColumn<UserRequest, UserRequestStatus> tcStatus;
    @FXML private TableColumn<UserRequest, UserRequestCategory> tcCategory;
    @FXML private TableView<UserRequest> tbview;
    private ObservableList<UserRequest> requestList;

    @FXML private Menu mnuOpt1;
    @FXML private Menu mnuOpt2;
    @FXML private CheckBox checkUrgent;
    @FXML private ChoiceBox<UserRequestCategory> cbCategory;
    @FXML private TextArea txtRequest;
    @FXML private ChoiceBox<User> cbUser;

    protected RequestMenuController(Stage previousMenu, Model model) { super(previousMenu, model); }

    @FXML
    private void initialize() {
        // Runs on scene load
        // See MessagesMenuController

        tcRequest.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRequest()));
        tcCategory.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCategory()));
        tcStatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getStatus()));
        refresh();

        cbCategory.setItems(FXCollections.observableList(model.um.getAllUserRequestCategories()));
        cbUser.setItems(FXCollections.observableList(model.um.getUserList()));
        // Enabled only when a row is selected
        tbview.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            mnuOpt1.setDisable(newSelection == null);
            mnuOpt2.setDisable(newSelection == null);
        });
    }

    @FXML
    private void handleOption1(ActionEvent event) {
        // Menu option 1: Update request.
        User user = model.getLoggedInUser();
        UserRequest selected = tbview.getSelectionModel().getSelectedItem();
        String out = "Updated request \"" + selected + "\" from Status \"" + selected.getStatus() + "\" to \"";
        model.um.updateUserRequestStatus(selected);
        System.out.println(out + selected.getStatus() + "\"");
        refresh();
    }

    @FXML
    private void handleOption2(ActionEvent event) {
        // Menu option 2: Delete request
        User user = model.getLoggedInUser();
        UserRequest selected = tbview.getSelectionModel().getSelectedItem();
        model.um.removeUserRequestFromUser(user, selected);
        System.out.println("Removed request \"" + selected + "\" from User \"" + user + "\"");
        refresh();
    }

    private void refresh() {
        User user = model.getLoggedInUser();
        List<UserRequest> requests = model.um.getUserRequestList(user);
        requestList = FXCollections.observableList(requests);
        tbview.setItems(requestList);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        try {
            if (cbUser.getSelectionModel().isEmpty() ||
                    cbCategory.getSelectionModel().isEmpty() ||
                    txtRequest.getText().isEmpty()) {
                throw new EmptyFieldException("All fields must be filled!");
            }
            User selected = cbUser.getValue();
            UserRequestCategory category = cbCategory.getValue();
            UserRequestStatus status = UserRequestStatus.PENDING;
            if (checkUrgent.isSelected()) {
                status = UserRequestStatus.URGENT;
            }
            String requestBody = txtRequest.getText();
            UserRequest request = new UserRequest(requestBody, category, status);
            model.um.addUserRequestToUser(selected, request);
            System.out.println("Added request \"" + request + "\" to User \"" + selected + "\"");
            refresh();
        } catch (EmptyFieldException e) {
            openError(e);
        }
    }
}
