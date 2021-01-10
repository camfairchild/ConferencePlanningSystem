package group0110.entities;

import java.io.Serializable;

/**
 * An instance of this class represents a user request
 */

public class UserRequest implements Serializable {
    private String request;
    private UserRequestStatus status;
    private UserRequestCategory category;

    /**
     * Create a new user request that contains the request string and the type of
     * request
     *
     * @param request  the content of the request
     * @param category a UserRequestCategory of the type of request
     *
     */
    public UserRequest(String request, UserRequestCategory category) {
        this.request = request;
        this.status = UserRequestStatus.PENDING;
        this.category = category;
    }

    /**
     * Create a new user request that contains the request string, request status,
     * and the type of request
     *
     * @param request  the content of the request
     * @param category a UserRequestCategory of the type of request
     * @param status   the UserRequestStatus of the request
     *
     */
    public UserRequest(String request, UserRequestCategory category, UserRequestStatus status) {
        this.request = request;
        this.status = status;
        this.category = category;
    }

    /**
     * Return the content of the request.
     *
     * @return The content of the request.
     **/
    public String getRequest() {
        return this.request;
    }

    /**
     * @return The cateory of this user request.
     **/
    public UserRequestCategory getCategory() {
        return this.category;
    }

    /**
     * @param category The new user request category to update to.
     **/
    public void updateCategory(UserRequestCategory category) {
        this.category = category;
    }

    /**
     * @return Returns the status of this user request.
     **/
    public UserRequestStatus getStatus() {
        return this.status;
    }

    /**
     * Change the status to reflect the updated nature of the request
     *
     **/
    public void updateStatus() {
        this.status = UserRequestStatus.ADDRESSED;
    }

}
