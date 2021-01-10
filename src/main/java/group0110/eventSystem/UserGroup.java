package group0110.eventSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import group0110.entities.Role;
import group0110.entities.User;

/**
 * A user group. Contains the ID of users. There are two types of users in this
 * group: speakers and attendees.
 */
public class UserGroup implements Serializable {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -7060746928129540379L;
    private List<User> users = new ArrayList<>();

    public UserGroup() {
        super();
    }

    public UserGroup(List<User> inputUsers) {
        for (User user : inputUsers) {
            users.add(user);
        }
    }

    /**
     * Get a list of all users in this group.
     *
     * @return A list of users.
     */
    public List<User> getAllUsers() {
        List<User> output = new ArrayList<>();
        for (User user : this.users) {
            output.add(user);
        }
        return output;
    }

    /**
     * Get a list of users with {@code role} in this group.
     *
     * @param role The role of users to search for
     *
     * @return A list of users.
     */
    public List<User> getUsers(Role... role) {
        List<User> usersOfRole = new ArrayList<>();
        for (User user : this.users) {
            if (Arrays.asList(role).contains(user.getRole())) {
                usersOfRole.add(user);
            }
        }
        return usersOfRole;
    }

    /**
     * Add user to the group.
     *
     * @param user The user to add to the group.
     */
    void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Remove a user from the group.
     *
     * @param user The ID of the user to remove.
     */
    boolean removeUser(User user) {
        return this.users.remove(user);
    }

    /**
     * @return Returns the total number of users in this group.
     */
    public int getAllUserCount() {
        return this.users.size();
    }

    /**
     * Get the number of users of type {@code role} in this group.
     *
     * @param role The role of users to search for.
     * @return Returns the number of users.
     */
    public int getUserCount(Role... role) {
        int count = 0;
        for (User user : this.users) {
            if (Arrays.asList(role).contains(user.getRole())) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param user The user to check for.
     * @return Return {@code true} if and only if the user is part of this group.
     */
    public boolean hasUser(User user) {
        return this.users.contains(user);
    }

    @Override
    public UserGroup clone() {
        UserGroup clonedGroup = new UserGroup(this.users);
        return clonedGroup;
    }
}
