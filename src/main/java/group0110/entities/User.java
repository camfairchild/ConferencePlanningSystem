package group0110.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A user of the convention planning program.
 */
public class User implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 8463897149287160483L;
    private String username;
    private String password;
    private ArrayList<String> events;
    private Role role;
    private Inbox inbox;
    private Outbox outbox;
    private ArrayList<User> contacts;
    private ArrayList<UserRequest> userRequests;

    /**
     * Creates a new user that has a username, password, a list of contacts, a list
     * of events, and role
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role     the role of the user. A user can be an attendee, an organizer
     *                 or a speaker.
     */

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.events = new ArrayList<>();
        this.role = role;
        this.inbox = new Inbox();
        this.outbox = new Outbox();
        this.contacts = new ArrayList<>();
        this.userRequests = new ArrayList<>();
    }

    /**
     * Gets the username of the user
     *
     * @return the string representing the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Changes the user's username
     *
     * @param username the string representing the username
     */
    public void setName(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user
     *
     * @return the string representing the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Changes the user's password
     *
     * @param password the string representing the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the list of the event IDs the user signed up for
     *
     * @return the list of event IDs
     */
    public ArrayList<String> getEvents() {
        return this.events;
    }

    /**
     * Gets the role of the user.
     *
     * @return Returns the role of the user
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Changes the role of the user
     *
     * @param role the role of the user. A user can be an attendee, an organizer or
     *             a speaker.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the inbox of the user
     *
     * @return the user's inbox
     */
    public Inbox getInbox() {
        return this.inbox;
    }

    /**
     * Gets the outbox of the user
     *
     * @return the user's outbox
     */
    public Outbox getOutbox() {
        return this.outbox;
    }

    /**
     * Gets the contacts list of the user
     *
     * @return the user's contacts list
     */
    public ArrayList<User> getContacts() {
        return this.contacts;
    }

    /**
     * Gets the requests of the user
     * @return the user's requests list
     */
    public ArrayList<UserRequest> getUserRequests() {
        return this.userRequests;
    }

    /**
     * Adds a user request to the list of user requests
     * @param request The request to add.
     */
    public void addUserRequestToUser(UserRequest request){
        this.userRequests.add(request);
    }

    /**
     * Removes user request from user request list
     * @param request The request to remove.
     */
    public void removeUserRequestFromUser(UserRequest request){
        this.userRequests.remove(request);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, events, role, inbox, outbox);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getRole().name(), getUsername());
    }
}
