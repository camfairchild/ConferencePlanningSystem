package group0110.ui.gui;

import group0110.entities.Message;
import group0110.entities.User;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


class MessagesMenuController extends SecondaryUserMenuController {
    @FXML
    private ListView<User> lvContacts;
    @FXML
    private ListView<Message> lvMessages;
    private List<User> userList = new ArrayList<User>();
    @FXML private TextArea txtMessageContent;
    @FXML private TextArea txtNewMessage;

    protected MessagesMenuController(Stage previousMenu, Model model) {
        super(previousMenu, model);
    }

    @FXML
    protected void initialize() {
        // Called when fxml is rendered
        List<User> users = model.um.getUserList();
        for (User user: users) {
            if (!user.equals(model.getLoggedInUser())) {
                userList.add(user);
            }
        }
        lvContacts.getItems().setAll(userList);
        if (model.canMassMessage()) {
            lvContacts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            lvContacts.getSelectionModel().getSelectedItems().addListener(handleMultipleUserSelectionChange);
        } else {
            lvContacts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            lvContacts.getSelectionModel().selectedItemProperty().addListener(handleSingleUserSelectionChanged);
        }
        lvMessages.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lvMessages.getSelectionModel().selectedItemProperty().addListener(handleMessageSelectionChanged);
    }

    private final ChangeListener<User> handleSingleUserSelectionChanged = (observable, oldValue, newValue) -> {
        populateListViewWithMessages(newValue);
        System.out.println("Selected User: " + newValue.getUsername());
    };


    private final ListChangeListener<User> handleMultipleUserSelectionChange = (observable) -> {
        if (observable.getList().size() == 1) {
            populateListViewWithMessages(lvContacts.getSelectionModel().getSelectedItem());
            System.out.println("Selected User: " + lvContacts.getSelectionModel().getSelectedItems().toString());
        } else {
            txtMessageContent.setText("This is a mass message to users: " + observable.getList().toString());
            System.out.println("Selected Users: " + observable.getList().toString());
        }
    };

    private final ChangeListener<Message> handleMessageSelectionChanged = (observable, oldValue, newValue) -> {
        if (newValue != null) {
            populateTextAreaWithMessages(newValue);
            System.out.println("Selected Message: " + newValue.toString());
            setRead(newValue);
        } else {
            txtMessageContent.setText("No Message Selected");
            System.out.println("Deselected Message");
        }
    };

    private void setRead(Message newValue) {
        newValue.setStatusRead();
        int index = lvMessages.getSelectionModel().getSelectedIndex();
        lvMessages.getSelectionModel().select(index);
    }


    private void populateTextAreaWithMessages(Message newValue) {
        if (newValue != null) {
            txtMessageContent.setText(newValue.toString());
        } else {
            txtMessageContent.setText("No Message Selected");
        }
    }

    private void populateListViewWithMessages(User user) {
        Map<String, ArrayList<Message>> inbox = model.getLoggedInUser().getInbox().getUserInbox();
        Map<String, ArrayList<Message>> outbox = model.getLoggedInUser().getOutbox().getUserOutbox();
        List<Message> messagesList = new ArrayList<>();
        if (!inbox.isEmpty() && inbox.containsKey(user.getUsername())) {
            List<Message> receivedList = inbox.get(user.getUsername());
            messagesList.addAll(receivedList);
        }
        if (!outbox.isEmpty() && outbox.containsKey(user.getUsername())) {
            List<Message> sentList = outbox.get(user.getUsername());
            messagesList.addAll(sentList);
        }
        messagesList.sort(Collections.reverseOrder()); // Sort by date descending
        lvMessages.getItems().setAll(messagesList);
    }

    @FXML
    private void handleSend(ActionEvent event) {
        sendMessage();
    }

    private void sendMessage() {
        User selected = lvContacts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Message: " + selected.toString() + " was chosen!");
            String receiver = selected.getUsername();
            String content = txtNewMessage.getText();
            String sender = model.getLoggedInUser().getUsername();
            // send message to receiver
            Message newMessage = new Message(content, receiver, sender);
            sendMessage(newMessage);
            refresh(selected);
        }
    }

    private void sendMessage(Message message) {
        if (lvContacts.getSelectionModel().getSelectedItems().size() > 1) {
            model.mm.sendMassMessage(lvContacts.getSelectionModel().getSelectedItems(), message);
        } else {
            model.mm.sendMessage(message);
        }
    }

    @FXML
    private void handleUnread(ActionEvent event) { markUnread(); }

    private void markUnread() {
        Message selected = lvMessages.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatusUnread();
            refresh(model.getLoggedInUser());
        }
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        // Ignores Shift+Enter
        if (!event.isShiftDown() && event.getCode().equals(KeyCode.ENTER)) {
            sendMessage();
        }
    }

    private void refresh(User user) {
        lvContacts.getSelectionModel().clearSelection();
        txtNewMessage.clear();
        txtMessageContent.clear();
        populateListViewWithMessages(user);
        lvMessages.getItems().clear();
        lvMessages.refresh();
    }
}
