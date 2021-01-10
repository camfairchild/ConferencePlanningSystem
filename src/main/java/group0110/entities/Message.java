package group0110.entities;

import java.io.Serializable;
import java.sql.Date;
/*import java.time.Instant;
import java.util.Comparator;
import java.util.Observable;
*/
/**
 * An instance of this class represents a message that can be sent between users
 */
public class Message implements Serializable, Comparable<Message> {

    private String messageContent;
    private String sender;
    private String receiver;
    private String personHolder;
    private String status;
    private Date date;

    /**
     * Create a new message that contains the message content, the sender, the receiver
     *
     * @param message the content of the message, ie what the message is
     * @param sender a string representation of the username of the person sending the message
     * @param receiver a string representation of the username of the person receiving the message
     * //@param status the status of a message, ie if the message is unread, read, or has been archived
     */

    public Message(String message, String receiver, String sender){
        this.messageContent = message;
        this.receiver = receiver;
        this.sender = sender;
        this.status = "unread";
        this.date = new Date(System.currentTimeMillis());
    }

    private void setMessageContent(String message){
        this.messageContent = message;
    }

    private void setSender(String sender){
        this.sender = sender;
    }

    private void setReceiver(String receiver){
        this.receiver = receiver;
    }

    /**
     * Gets the content of this message
     * @return the content of this message
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * Gets the username of the recipient
     * @return the name
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets the username of the sender
     * @return the name
     */
    public String getSender() {
        return sender;
    }

    /**
     * sets the status of the message as unread
     */
    public void setStatusUnread(){ this.status = "unread"; }

    /**
     * Sets the status as archived
     */
    public void setStatusArchived(){ this.status = "archived"; }

    /**
     * Sets the status as read
     */
    public void setStatusRead(){ this.status = "read"; }

    /**
     *
     * @return the status of the message
     */
    public String getStatus() { return status; }

    /**
     * Checks if the message is archived
     * @return true or false if the message is archived
     */
    public boolean isArchived(){
        return this.status.equals("archived");
    }

    private void setPersonHolder(String person) { this.personHolder = person; }

    public String getPersonHolder() { return personHolder; }


    public Message(String message){
        this.messageContent = message;
        this.status = "unread";

    }


    @Override
    public String toString() {
        return messageContent + "\n" + getStatus();
    }


    @Override
    public int compareTo(Message o) {
        return this.date.compareTo(o.date);
    }
}
