package group0110.eventSystem.exceptions;

/**
 * An exception when the event does not exist.
 */
public class EventNotFound extends RuntimeException {
    /**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -3593201519216191338L;

	public EventNotFound(String message) {
        super(message);
    }
}
