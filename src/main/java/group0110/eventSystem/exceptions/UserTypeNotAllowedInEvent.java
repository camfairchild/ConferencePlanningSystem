package group0110.eventSystem.exceptions;

/**
 * This exception is when the user's type is not allowed in the event.
 */
public class UserTypeNotAllowedInEvent extends Exception {

    /**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	public UserTypeNotAllowedInEvent(String message) {
        super(message);
    }
}
