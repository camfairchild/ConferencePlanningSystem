package group0110.ui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import group0110.entities.Message;
import group0110.entities.User;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * An outbox menu for the current logged-in user.
 *
 * @see InboxMenu
 */

public class OutboxMenu extends Menu {
    private User user;
    private ArrayList<String> conversations = new ArrayList<>();

    /**
     * Creates a new outbox menu for users.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */
    public OutboxMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays the current user's list of contacts.
     */
    public void displayContacts() {
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
     * Displays all messages sent by the user.
     */
    public void displayContent() {
        for (ArrayList<Message> lst : user.getOutbox().getUserOutbox().values()) {
            for (Message message : lst) {
                System.out.println("To: " + message.getReceiver() + "\n" + message.getMessageContent() + "\n");
            }
        }
        Scanner sc = new Scanner(System.in);
        IntegerAnswerCriteria integerAnswerCriteria = new IntegerAnswerCriteria(0, 0);
        prompt(sc, "Type '0' to go back to the Outbox Menu.", integerAnswerCriteria);
        outboxSelectionMenu();
    }

    /**
     * Displays all messages sent to the chosen contact.
     *
     * @param choice the current user's input of a number designated to a specific
     *               contact in the user's contact list.
     */
    public void displayContent(int choice) {
        String sender = conversations.get(choice);
        Map<String, ArrayList<Message>> userOutbox = user.getOutbox().getUserOutbox();
        ArrayList<Message> contactMessages = userOutbox.get(sender);
        if (contactMessages == null) {
            System.out.println("You have never sent a message to this contact.");
        } else {
            for (Message sent : contactMessages) {
                System.out.println(sent.getMessageContent() + "\n");
            }
        }
    }

    /**
     * Displays the main outbox menu for the current user. Users can view a list of
     * their contacts or send a message.
     */
    public void outboxSelectionMenu() {
        Scanner scanner = new Scanner(System.in);
        IntegerAnswerCriteria choiceCriteria = new IntegerAnswerCriteria(0, 3);
        String choice_string = prompt(scanner, "Please choose an option:\n" + "1. View Contacts\n" + "2. Send Message\n"
                + "3. View All Sent Messages\n" + "0. Return to Main Menu", choiceCriteria);
        int choice = Integer.parseInt(choice_string);

        if (choice == 1) {
            viewContactsMenu();
        } else if (choice == 2) {
            MessageMenu messageMenuScreen = new MessageMenu(user, um, mm, es);
            messageMenuScreen.MessageMenuSelection();
        } else if (choice == 3) {
            if (user.getOutbox().getUserOutbox().isEmpty()) {
                Scanner sc = new Scanner(System.in);
                IntegerAnswerCriteria integerAnswerCriteria = new IntegerAnswerCriteria(0, 0);
                prompt(sc, "You have not sent any messages. Type '0' to go back to the Outbox Menu.",
                        integerAnswerCriteria);
                outboxSelectionMenu();
            } else {
                displayContent();
            }
        }
    }


    /**
     * Displays list of contacts.
     */
    private void viewContactsMenu() {
        displayContacts();
        Scanner scanner = new Scanner(System.in);
        IntegerAnswerCriteria choiceCriteria = new IntegerAnswerCriteria(0, conversations.size());
        String option_string = prompt(scanner,
                "Please choose a contact to view message history, " + "or type '0' to go back to the Outbox Menu.", choiceCriteria);
        int option = Integer.parseInt(option_string);

        if (option != 0) {
            displayContent(option - 1);
        }
        outboxSelectionMenu();
    }
}
