package group0110.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import group0110.entities.Message;
import group0110.entities.User;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.ui.criteria.RegexAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;
import java.lang.Math;

/**
 * An inbox menu for the current logged-in user.
 *
 * @see OutboxMenu
 */

public class InboxMenu extends Menu {

    private User user;
    private ArrayList<String> conversations = new ArrayList<>();

    /**
     * Creates a new inbox menu for users.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */

    public InboxMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays the current user's list of contacts.
     */

    void displayContacts() {
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
     * Displays all messages from a list
     * @param list List of messages to display
     */
    void displayMessagesFromList(List<Message> list) {
        int i = 0;
        for (Message message : list) {
            i++;
            System.out.println(i + ". " + message.toString() + "        " + message.getStatus());
            if(!message.isArchived()){
                message.setStatusRead();
            }
        }
    }

    /**
     * Displays all messages sent to the chosen contact.
     *
     * @param choice the current user's input of a number designated to a specific
     *               contact in the user's contact list.
     */
    public void displayMessages(int choice) {
        Scanner sc = new Scanner(System.in);

        String sender = conversations.get(choice - 1);
        Map<String, ArrayList<Message>> userInbox = user.getInbox().getUserInbox();
        ArrayList<Message> contactMessages = userInbox.get(sender);

        if (contactMessages == null) {
            System.out.println("You have no messages from this contact.");
        } else {
            displayMessagesFromList(contactMessages);
            IntegerAnswerCriteria numCriteria = new IntegerAnswerCriteria(-contactMessages.size(), contactMessages.size());
            String num_string = prompt(sc, "Please type the number of the message you wish to reply to,"
                    + " if you would like to mark a message as unread, place a '-' in front of the number you " +
                    "wish to mark as unread," + " or type '0' to go back to the main menu.", numCriteria);
            int num = Integer.parseInt(num_string);
            if (num != 0) {
                if (num < 0){
                    int abs_num = Math.abs(num);
                    for(int i = abs_num; i < contactMessages.size(); i++){
                        contactMessages.get(i - 1).setStatusUnread();
                    }
                } else {
                    RegexAnswerCriteria replyCriteria = new RegexAnswerCriteria(".");
                    String reply = prompt(sc, "Please type your reply in the line below:", replyCriteria,
                            "You may not send an empty message");

                    String fullReply = "RE: " + contactMessages.get(num - 1).getMessageContent() + "\n" + reply;
                    Message msg = new Message(fullReply, sender, user.getUsername());
                    mm.sendMessage(msg);
                    System.out.println("Message Sent!");
                }
            }
        }
        inboxSelectionMenu();
    }

    /**
     * Displays the main inbox menu for the current user. Users can view a list of
     * their contacts, view messages from their contacts and reply to messages.
     */
    public void inboxSelectionMenu() {
        Scanner sc = new Scanner(System.in);
        displayContacts();
        IntegerAnswerCriteria inputCriteria = new IntegerAnswerCriteria(0, conversations.size());
        String input_string = prompt(sc,
                "Please select a contact by typing a number," + " or type '0' to go back to the main menu.",
                inputCriteria);
        int input = Integer.parseInt(input_string);

        if (input != 0) {
            displayMessages(input);
        }
    }
}
