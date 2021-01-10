package group0110.eventSystem.exceptions;

/**
 * This exception is when a {@link group0110.entities.User} is unavailable at a
 * certain {@link group0110.eventSystem.TimeInterval}.
 */
public class UserNotAvailable extends Exception {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 1L;

    public UserNotAvailable(String message) {
        super(message);
    }
}
