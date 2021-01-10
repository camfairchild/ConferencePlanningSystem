package group0110.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An instance of this class represents the inbox of a user
 */

public class Inbox implements Serializable {

    Map<String, ArrayList<Message>> userInbox;

    /**
     * Creates the inbox of a user where the key is the username of the sender and the arraylist of messages is all the
     * messages that that user has sent to this user who is the receiver
     */

    public Inbox(){
        userInbox = new HashMap<>();
    }

    /**
     * Adds a message to the inbox
     * @param message the message needs to be added
     */
    public void addMessage(Message message){
        String sender = message.getSender();
        ArrayList<Message> tempList = new ArrayList<>();
        tempList.add(message);

        if(userInbox.containsKey(sender)){
            ArrayList<Message> tempMessages = userInbox.get(sender);
            tempMessages.add(message);
            userInbox.replace(sender, tempMessages);
        } else {
            userInbox.put(sender, tempList);
        }
    }

    /**
     *
     * @return the user's inbox of messages
     */
    public Map<String, ArrayList<Message>> getUserInbox(){
        return userInbox;
    }

    /**
     * Defines the equals method in the context of given current inbox and a user's inbox
     * @param o given inbox being compared
     * @return boolean for
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inbox inbox = (Inbox) o;
        return Objects.equals(userInbox, inbox.userInbox);
    }

    /**
     *
     * @return the hashcode of this inbox
     */
    @Override
    public int hashCode() {
        return Objects.hash(userInbox);
    }

    /**
     * Removes message from inbox
     * @param message the message that needs to be deleted
     */
    public void deleteInboxMessage(Message message){
        String senderId = message.getSender();
        ArrayList<Message> messages = this.userInbox.get(senderId);
        messages.remove(message);
    }
}
