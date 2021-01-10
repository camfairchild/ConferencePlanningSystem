package group0110.ui.gui;

import group0110.entities.User;
import group0110.eventSystem.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

class SuggestionMenuController extends SecondaryUserMenuController {
    @FXML private TableView tbview;
    @FXML private TableColumn<User, String> tcUsers;
    @FXML private TableColumn<User, String> tcCommonEvents;
    private User currentUser = model.getLoggedInUser();
    private ObservableList<User> userList;

    protected SuggestionMenuController(Stage previousMenu, Model model) {
        super(previousMenu, model);
    }

    @FXML
    private void initialize() {
        List<User> suggestions = model.getEventSystem().getUserRecommendations(currentUser);
        populateWithUsers();
        tcUsers.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUsername()));
        tcCommonEvents.setCellValueFactory(param -> new SimpleObjectProperty<>(model.getEventSystem().getCommonEvents(currentUser, param.getValue()).toString()));
        tbview.setItems(userList);
    }

    private void populateWithUsers() {
        List<User> users = model.getEventSystem().getUserRecommendations(model.getLoggedInUser());
        userList = FXCollections.observableList(users);
    }
}
