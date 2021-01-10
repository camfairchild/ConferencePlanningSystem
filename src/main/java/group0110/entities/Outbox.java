package group0110.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An instance of this class represents the outbox of a user
 */

public class Outbox implements Serializable {

    Map<String, ArrayList<Message>> userOutbox;

    /**
     * Creates the outbox of a user where the key is the username of the receiver and the arraylist of messages is all
     * the messages that this user, the sender, has sent to that user
     */

    public Outbox(){
        userOutbox = new HashMap<>();
    }

    public void addMessage(Message message){
        String receiver = message.getReceiver();
        ArrayList<Message> tempList = new ArrayList<>();
        tempList.add(message);
        if(userOutbox.containsKey(receiver)){
            ArrayList<Message> tempMessages = userOutbox.get(receiver);
            tempMessages.add(message);
            userOutbox.replace(receiver, tempMessages);
        } else {
            userOutbox.put(receiver, tempList);
        }
    }

    /**
     *
     * @return the user's outgoing messages
     */
    public Map<String, ArrayList<Message>> getUserOutbox(){
        return userOutbox;
    }

    /**
     * Defines equals within the context of the outbox
     * @param o the given outbox
     * @return whether the given outbox is this same as this outbox
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outbox outbox = (Outbox) o;
        return Objects.equals(userOutbox, outbox.userOutbox);
    }

    /**
     *
     * @return the hashcode of this outbox
     */
    @Override
    public int hashCode() {
        return Objects.hash(userOutbox);
    }

    /**
     * Removes message from the outbox
     * @param message the message that needs to be removed
     */
    public void deleteOutboxMessage(Message message){
        String receiverId = message.getReceiver();
        ArrayList<Message> messages = this.userOutbox.get(receiverId);
        messages.remove(message);
    }
}
