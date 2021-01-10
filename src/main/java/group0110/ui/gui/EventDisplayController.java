package group0110.ui.gui;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import group0110.entities.Role;
import group0110.entities.User;
import group0110.eventSystem.DateTimeInterval;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventType;
import group0110.eventSystem.exceptions.ExceedingRoomMaxCapacity;
import group0110.eventSystem.exceptions.InvalidIntervalTimes;
import group0110.eventSystem.exceptions.RoomIsFull;
import group0110.eventSystem.exceptions.RoomNotAvailable;
import group0110.eventSystem.exceptions.UserNotAvailable;
import group0110.eventSystem.exceptions.UserTypeNotAllowedInEvent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class EventDisplayController extends SecondaryUserMenuController {
    @FXML
    private TableColumn<Event, String> tcTitle;
    @FXML
    private TableColumn<Event, String> tcRoom;
    @FXML
    private TableColumn<Event, Integer> tcParticipants;
    @FXML
    private TableColumn<Event, String> tcId;
    @FXML
    private TableColumn<Event, Integer> tcCapacity;
    @FXML
    private TableColumn<Event, DateTimeInterval> tcDate;
    @FXML
    private TableColumn<Event, EventType> tcType;
    @FXML
    private TableView<Event> tbview;
    private ObservableList<Event> eventList;

    @FXML
    private Menu mnuOpt1;
    @FXML
    private Menu mnuOpt2;
    @FXML
    private Menu mnuOpt3;
    @FXML
    private MenuItem print;

    private boolean myEventsFiltered = false;

    protected EventDisplayController(Stage previousMenu, Model model) {
        super(previousMenu, model);
    }

    @FXML
    private void initialize() {
        // Runs on scene load
        // See MessagesMenuController
        populateWithEvents();
        tcId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        tcTitle.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTitle()));
        tcRoom.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getRoom()));
        tcCapacity.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMaxCapacity()));
        tcDate.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDateTimeInterval()));
        tcParticipants.setCellValueFactory(
                param -> new SimpleObjectProperty<>(param.getValue().getUsers().getAllUserCount()));
        tcType.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEventType()));
        tbview.setItems(eventList);
        // If user is organizer, enable option 1. Default is disabled.
        if (model.getLoggedInUser().getRole().equals(Role.ORGANIZER)) {
            mnuOpt1.setDisable(false);
        }
        tbview.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            if (newSelection == null || !newSelection.canJoin(model.getLoggedInUser())) {
                mnuOpt3.setDisable(true);
            } else {
                mnuOpt3.setDisable(false);
                if (newSelection.getUsers().hasUser(model.getLoggedInUser())) {
                    mnuOpt3.setText("Un-enrol");
                } else {
                    mnuOpt3.setText("Enroll");
                }
            }
        });
    }

    /**
     * Confirms if a user wants to cancel the event and handles input with dialogue
     *
     * @param eventId The id of the event.
     */
    private void confirmCancel(String eventId) {
        Optional<ButtonType> res = getConfirmationDialogue("Cancel this event?");
        if (res.isPresent()) {
            ButtonType choice = res.get();
            if (choice == ButtonType.OK) {
                model.getEventSystem().removeEvent(eventId);
                openSuccess("Event successfully cancelled.");
                System.out.println("Event: " + eventId + " cancelled");
            }
        }
    }

    /**
     * Confirm that user wants to enrol in an event and handles input and shows
     * dialogue.
     *
     * @param eventId The id of the event.
     */
    private void confirmEnrolment(String eventId, User user) {
        Optional<ButtonType> res = getConfirmationDialogue("Enrol in this event?");
        if (res.isPresent()) {
            ButtonType choice = res.get();
            if (choice == ButtonType.OK) {
                try {
                    model.getEventSystem().signUserUp(user, eventId);
                    openSuccess("Enrollment successful!");
                    System.out.println("User enrolled to event.");
                } catch (UserNotAvailable | RoomIsFull | UserTypeNotAllowedInEvent e) {
                    openError(e);
                }
            }
        }
    }

    /**
     * Confirm that the user wants to unenrol in the event and handles input and
     * shows dialogue.
     *
     * @param eventId The id of the event.
     */
    private void confirmUnenrolment(String eventId, User user) {
        Optional<ButtonType> res = getConfirmationDialogue("Un-enrol from this event?");
        if (res.isPresent()) {
            ButtonType choice = res.get();
            if (choice == ButtonType.OK) {
                model.getEventSystem().cancelUserRegistration(user, eventId);
                openSuccess("Successfully un-enrolled from the event.");
                System.out.println("User un-enrolled from event.");
            }
        }
    }

    /**
     * Refreshes the display with updated information.
     */
    private void refresh() {
        filterMyEvents();
        populateWithEvents();
        tbview.refresh();
        tbview.getSelectionModel().clearSelection();
    }

    private void filterMyEvents() {
        if (myEventsFiltered) {
            eventList = FXCollections.observableList(model.getEventSystem().getUserEvents(model.getLoggedInUser()));
        } else {
            eventList = FXCollections.observableList(model.getEventSystem().getAllEvents());
        }
        tbview.setItems(eventList);
    }

    /**
     * Get an updated list of events from the event system.
     */
    private void populateWithEvents() {
        filterMyEvents();
    }

    @FXML
    private void handleOption1(ActionEvent event) {
        // Menu option 1: Reschedule Events. Only for organizers
        Event selected = tbview.getSelectionModel().getSelectedItem();
        String currentRoom = selected.getRoom();
        DateTimeInterval currentTime = selected.getDateTimeInterval();
        // Open prompt
        Pair<DateTimeInterval, String> result = openReschedulePrompt(selected);
        if (result != null) {
            DateTimeInterval newDateTimeInterval = result.getKey();
            String newRoomID = result.getValue();
            // Reschedule
            try {
                model.getEventSystem().rescheduleEvent(selected.getId(), newDateTimeInterval, newRoomID);
            } catch (ExceedingRoomMaxCapacity | RoomNotAvailable e) {
                openError(e);
            }
            // Refresh
            List<Event> events = model.getEventSystem().getAllEvents();
            eventList = FXCollections.observableList(events);
            refresh();
        }
    }

    /**
     * Prompts user to reschedule an Event using Custom Dialog for input
     *
     * @return null if the no answer
     */
    private Pair<DateTimeInterval, String> openReschedulePrompt(Event event) {
        // Create the custom dialog.
        Dialog<Pair<DateTimeInterval, String>> dialog = new Dialog<>();
        dialog.setTitle("Reschedule Event");
        dialog.setHeaderText("Reschedule Event with\n" + "ID: " + event.getId() + "\n" + "In Room: " + event.getRoom()
                + "\n" + "At: " + event.getDateTimeInterval().toString());

        // Set the button types.
        ButtonType reschButtonType = new ButtonType("Reschedule", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(reschButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker date = new DatePicker();
        TextField txtTime = new TextField();
        txtTime.setPromptText("Time Range i.e. 12:30-14:50");
        TextField txtRoom = new TextField();
        txtRoom.setPromptText("Room ID");

        grid.add(new Label("Date:"), 0, 0);
        grid.add(date, 1, 0);
        grid.add(new Label("Time:"), 0, 1);
        grid.add(txtTime, 1, 1);
        grid.add(new Label("Room:"), 0, 2);
        grid.add(txtRoom, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convert result to Pair when button clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == reschButtonType) {
                String timeRange = txtTime.getText();
                Pattern pat = Pattern.compile("(\\d{1,2}):(\\d{2})");
                Matcher mat = pat.matcher(timeRange);
                if (!mat.find()) {
                    throw new InputMismatchException("The time range does not fit the format HH:MM-HH:MM");
                }
                LocalTime startTime = LocalTime.parse(mat.group(1));
                LocalTime endTime = LocalTime.parse(mat.group(2));
                LocalDate localDate = date.getValue();
                DateTimeInterval dateTimeInterval = null;
                try {
                    dateTimeInterval = new DateTimeInterval(localDate, startTime, endTime);
                } catch (InvalidIntervalTimes invalidIntervalTimes) {
                    openError(invalidIntervalTimes);
                }
                String roomId = txtRoom.getText();
                return new Pair<>(dateTimeInterval, roomId);
            }
            return null;
        });

        Optional<Pair<DateTimeInterval, String>> result = dialog.showAndWait();

        return result.orElse(null);
    }

    @FXML
    private void handleOption2(ActionEvent event) {
        // Menu option 2: Show my events
        // Swap function
        if (!myEventsFiltered) {
            myEventsFiltered = true;
            mnuOpt2.setText("Show All Events");
        } else {
            myEventsFiltered = false;
            mnuOpt2.setText("Show My Events");
        }
        refresh();
    }

    @FXML
    private void handleOption3(ActionEvent event) {
        // Menu option 3: (Un)Enroll in event
        Event selected = tbview.getSelectionModel().getSelectedItem();
        modifyUserEnrollment(selected, model.getLoggedInUser());
    }

    private void modifyUserEnrollment(Event event, User user) {
        if (event.getUsers().hasUser(user)) {
            // Un-enroll
            confirmUnenrolment(event.getId(), user);
        } else {
            confirmEnrolment(event.getId(), user);
        }
        refresh();
    }

    @FXML
    private void handleChangeCap(ActionEvent event) {
        Event selected = tbview.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        int roomCapacity = model.getEventSystem().getRoomById(selected.getRoom()).getMaxCapacity();
        int currentCap = selected.getMaxCapacity();

        TextInputDialog dialog = new TextInputDialog("event capacity");
        dialog.setTitle("Change Event Capacity");
        dialog.setHeaderText(
                String.format("Current capacity is %s and room capacity is %s", "" + currentCap, "" + roomCapacity));
        dialog.setContentText("New Capacity:");

        // Numbers only
        UnaryOperator<TextFormatter.Change> numFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        dialog.getEditor().setTextFormatter(new TextFormatter<>(numFilter));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int newCap = Integer.parseInt(result.get());
            if (newCap > roomCapacity) {
                openError(new NumberFormatException("You went over the room capacity of: " + roomCapacity));
            } else {
                model.getEventSystem().setEventCapacity(selected, newCap);
                openSuccess("Event capacity changed to: " + newCap);
                System.out.println("Event capacity changed to: " + newCap);
                refresh();
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Event selected = tbview.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        confirmCancel(selected.getId());
        refresh();
    }

    @FXML
    private void handlePrint(ActionEvent event) throws IOException {
        File f = new File("Schedule");
        FileWriter writer = new FileWriter("Schedule", false);
        List<Event> allEvents = model.getEventSystem().getAllEvents();
        for (Event event1 : allEvents) {
            writer.write(event1.toString() + System.lineSeparator());
        }
        writer.close();
        try (BufferedReader br = new BufferedReader(new FileReader("Schedule"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        openSuccess("Schedule successfully created! You can open a text file of the schedule, located in " +
                f.getAbsolutePath());
    }

    @FXML
    private void handleEnrollOtherUser(ActionEvent event) {
        Event selected = tbview.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        List<User> choices = new ArrayList<>();
        choices.addAll(model.um.getUserList());

        ChoiceDialog<User> dialog = new ChoiceDialog<User>(null, choices);
        dialog.setTitle("Modify User Enrollment");
        dialog.setHeaderText("Pick a User to modify enrollment of for Event: "+ selected.getTitle());
        dialog.setContentText("Choose the user:");

        Optional<User> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("User: " + result.get() + " enrollment was modified");
            modifyUserEnrollment(selected, result.get());
        }
        refresh();
    }
}
