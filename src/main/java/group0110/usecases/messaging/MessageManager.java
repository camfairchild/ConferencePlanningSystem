package group0110.usecases.messaging;


import java.util.List;

import group0110.entities.Inbox;
import group0110.entities.Message;
import group0110.entities.Outbox;
import group0110.entities.User;
import group0110.usecases.user.UserManager;

/**
 * A message manager.
 */

public class MessageManager {

    private UserManager allUsers;
    private Inbox userInbox;
    private Outbox userOutbox;

    /**
     * creates a MessageManager that contains a UserManager, a new inbox, and a new outbox
     * @param allUsers a UserManager that should contain a list of users
     */

    public MessageManager(UserManager allUsers){
        this.userInbox = new Inbox();
        this.userOutbox = new Outbox();
        this.allUsers = allUsers;
    }


    private void setUserInbox(Inbox userInbox){
        this.userInbox = userInbox;
    }

    private void setUserOutbox(Outbox userOutbox) {
        this.userOutbox = userOutbox;
    }

    private void setAllUsers(UserManager allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * Gets the user manager contained in the message manager
     * @return the user manager that contains a list of users
     */
    public UserManager getAllUsers() {
        return allUsers;
    }

    /**
     *
     * @return The inbox
     */
    public Inbox getUserInbox() {
        return userInbox;
    }

    /**
     *
     * @return The outbox
     */
    public Outbox getUserOutbox() {
        return userOutbox;
    }

    /**
     * sends a message from the sender in message to the receiver in message
     * @param message a message
     */
    public void sendMessage(Message message){
        String receiver = message.getReceiver();
        String sender = message.getSender();
        this.userInbox.addMessage(message);
        this.userOutbox.addMessage(message);
        this.allUsers.updateUserOutbox(this.allUsers.getUser(sender), message);
        this.allUsers.updateUserInbox(this.allUsers.getUser(receiver), message);
    }

    /**
     * sends a message to a list of users
     * @param users an array list of users
     * @param message a message
     */
    public void sendMassMessage(List<User> users, Message message){
        for(User user : users){
            if (!user.getUsername().equals(message.getSender())){
                Message messageWithReceiverName = new Message(message.getMessageContent(), user.getUsername(), message.getSender());
                sendMessage(messageWithReceiverName);
            }

        }
    }

    /**
     * Removes a message from both the recipient's and the sender's inbox and outbox
     * @param message The message that needs to be removed
     */
    public void deleteMessage(Message message){
        String personsInbox = message.getReceiver();
        String sendersOutbox = message.getSender();
        Inbox theInbox = this.allUsers.getUser(personsInbox).getInbox();
        Outbox theOutbox = this.allUsers.getUser(sendersOutbox).getOutbox();
        theInbox.deleteInboxMessage(message);
        theOutbox.deleteOutboxMessage(message);
    }
}
