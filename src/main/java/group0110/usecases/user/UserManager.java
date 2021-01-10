package group0110.usecases.user;

import group0110.entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A user manager.
 */
public class UserManager {
    private List<User> userList;

    /**
     * Creates a UserManager that contains a list of users in the convention
     * planning program.
     *
     * @param userList the list of users
     */

    public UserManager(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Gets the list of users in the user manager
     *
     * @return the list containing all the users in the user manager
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Gets the user with the given username.
     *
     * @param username the username of the user
     * @return the user with the given username if the user is in the list of all
     *         users.
     */
    public User getUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a user request to the list of user requests
     *
     * @param request the request
     * @param user    the user of the request
     */
    public void addUserRequestToUser(User user, UserRequest request) {
        user.addUserRequestToUser(request);
    }

    /**
     * Removes user request from the user's request list
     *
     * @param user    The user to remove the request from.
     * @param request The request to remove from the user.
     */
    public void removeUserRequestFromUser(User user, UserRequest request) {
        user.removeUserRequestFromUser(request);
    }

    /**
     * Gets a list of all the user requests of a single user
     *
     * @param user the user we want the user request of
     * @return Returns a list of all the user requests of {@code user}.
     */
    public List<UserRequest> getUserRequestList(User user) {
        return user.getUserRequests();
    }

    /**
     * Gets the status of a user request
     *
     * @param request the request we want the status of
     * @return Returns the status of {@code request}.
     */
    public UserRequestStatus getUserRequestStatus(UserRequest request) {
        return request.getStatus();
    }

    public ArrayList<UserRequestCategory> getAllUserRequestCategories(){
        ArrayList<UserRequestCategory> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(UserRequestCategory.values()));
        return temp;
    }

    /**
     * Gets a list of all the usernames of all users
     *
     * @return a list of usernames of all the users currently in the system
     */
    public ArrayList<String> getListOfUserIds() {
        ArrayList<String> uL = new ArrayList<>();
        for (User user : userList) {
            uL.add(user.getUsername());
        }
        return uL;
    }

    /**
     * Adds a new user with the given username, password and userType to the user
     * manager
     *
     * @param username the string representing the user's username
     * @param password the string representing the user's password
     * @param userType the role of the user. A user can be an attendee, an organizer
     *                 or a speaker.
     */
    public void addUser(String username, String password, Role userType) {
        User user = new User(username, password, userType);
        user.setRole(userType);
        userList.add(user);
    }

    /**
     * Removes eventId from the events of the user with the given username
     *
     * @param username the string representing the user's username
     * @param eventId  the string representing the eventId
     */
    public void removeEventFromUser(String username, String eventId) {
        User user = getUser(username);
        user.getEvents().remove(eventId);
    }

    /**
     * Updates the user's inbox with any new messages from other users
     *
     * @param user    the user who's inbox will be updated
     * @param message Message that should be added to inbox
     */
    public void updateUserInbox(User user, Message message) {
        user.getInbox().addMessage(message);
    }

    /**
     * Updates the user's outbox with any new messages sent by this user
     *
     * @param user    the user who's outbox will be updated
     * @param message Message that should be added to outbox
     */
    public void updateUserOutbox(User user, Message message) {
        user.getOutbox().addMessage(message);
    }

    /**
     * Updates the status of the User Request
     *
     * @param request The UserRequest to update
     */
    public void updateUserRequestStatus(UserRequest request) {
        request.updateStatus();
    }
}
