package group0110.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import group0110.entities.Message;
import group0110.entities.Role;
import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.ui.criteria.RegexAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A message menu for the current logged in user.
 */

public class MessageMenu extends Menu {

    private User user;
    private ArrayList<User> listOfAttendees;
    private Message newMessage;
    private ArrayList<String> conversations = new ArrayList<>();

    /**
     * Creates a new message menu.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager
     * @param mm   a MessageManager
     * @param es   an EventSystem
     */

    public MessageMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
        this.listOfAttendees = new ArrayList<>();
    }

    /**
     * Displays a message menu. Allows user to mass message, individual message, or
     * both, depending on the user's role.
     */

    public void MessageMenuSelection() {
        if (user.getRole() == Role.ATTENDEE) {
            IndividualMessagingMenu();
        } else if (user.getRole() == Role.ORGANIZER || user.getRole() == Role.SPEAKER) {
            Scanner scanner = new Scanner(System.in);
            // Pattern matches "mass" or "individual" or "back" but not more than one.
            RegexAnswerCriteria messageChoiceCriteria = new RegexAnswerCriteria("^(MASS|INDIVIDUAL|0){1}$", true);
            String messageChoice = prompt(scanner,
                    "Do you want to send a mass message or an individual message?\n" + "Type '0' to go back.",
                    messageChoiceCriteria, "That is not a valid option.");

            if (messageChoice.toLowerCase().contains("mass")) {
                MassMessagingMenu();
            } else if (messageChoice.toLowerCase().contains("individual")) {
                IndividualMessagingMenu();
            } else if (messageChoice.equals("0")) {
                OutboxMenu outboxMenu = new OutboxMenu(user, um, mm, es);
                outboxMenu.outboxSelectionMenu();
            }
        }
    }

    /**
     * Displays menu for mass messaging. Only accessible to organizer and speaker
     * users.
     */

    public void MassMessagingMenu() {
        Scanner scanner = new Scanner(System.in);
        DisplayEvents();
        IntegerAnswerCriteria eventNumCriteria = new IntegerAnswerCriteria(0, es.getAllEvents().size());
        String eventNum_string = prompt(scanner,
                "Type the number of the event you want to message," + " or type '0' to go back.", eventNumCriteria,
                "That is not a valid event number.");
        int eventNum = Integer.parseInt(eventNum_string);

        if (eventNum != 0) {
            RegexAnswerCriteria contentCriteria = new RegexAnswerCriteria(".");
            String content = prompt(scanner, "Please type your message in the line below:", contentCriteria,
                    "You may not send an empty message.");

            List<String> attendees = es.getAllEvents().get(eventNum - 1).getUsers().getUsers(Role.ATTENDEE).stream()
                    .map(user -> user.getUsername()).collect(Collectors.toList());

            for (String attendee : attendees) {
                listOfAttendees.add(um.getUser(attendee));
            }
            newMessage = new Message(content, null, user.getUsername());
            mm.sendMassMessage(listOfAttendees, newMessage);
            System.out.println("Message Sent!");
        }
        MessageMenuSelection();
    }

    /**
     * Displays menu for mass messaging. Accessible to all users.
     */

    public void IndividualMessagingMenu() {
        Scanner scanner = new Scanner(System.in);
        OutboxMenu om = new OutboxMenu(user, um, mm, es);
        displayContacts();
        IntegerAnswerCriteria receiverCriteria = new IntegerAnswerCriteria(0, conversations.size() + 1);
        String receiver_string = prompt(scanner,
                "Type the number of the person you want to send a " + "message to, or type 0 to go back.",
                receiverCriteria, "That selection is not valid. Please try again:");
        int receiver = Integer.parseInt(receiver_string);
        if (receiver == 0) {
            om.outboxSelectionMenu();
        } else {
            RegexAnswerCriteria contentCriteria = new RegexAnswerCriteria(".");
            String content = prompt(scanner, "Please type your message in the line below:", contentCriteria,
                    "You may not send an empty message.");
            Message newMessage = new Message(content, conversations.get(receiver - 1), user.getUsername());
            mm.sendMessage(newMessage);

            System.out.println("Message Sent!");
            IndividualMessagingMenu();
        }
    }

    /**
     * Displays list of this user's contacts.
     */

    public void displayContacts() {
        if (um.getUserList().size() == 0) {
            System.out.println("There are currently no users you can send messages to.");
        } else {
            System.out.println("Below is the list of users you can message:");
        }
        int i = 0;
        for (User u1 : um.getUserList()) {
            if (!(user.getUsername().equals(u1.getUsername()))) {
                conversations.add(u1.getUsername());
                i++;
                System.out.println(i + ". " + u1.getUsername());
            }
        }
    }

    /**
     * Displays a list of events that this user can mass message.
     */

    public void DisplayEvents() {
        if (es.getAllEvents().size() == 0) {
            System.out.println("There are currently no events you can send mass messages to.");
        } else {
            System.out.println("Below is the list of events you can mass message:");
        }
        int i = 0;
        for (Event e : es.getAllEvents()) {
            String title = e.getTitle();
            String eventId = e.getId();
            i++;
            System.out.println(i + ". " + title + ", " + eventId);
        }
    }
}
