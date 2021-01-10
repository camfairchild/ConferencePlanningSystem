package group0110.eventSystem.exceptions;

/**
 * An exception when the {@link group0110.eventSystem.Event}'s maximums capacity
 * is not a positive number.
 */
public class EventMaximumCapacityNotPositive extends RuntimeException {
    /**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -2221870232427880620L;

	public EventMaximumCapacityNotPositive(String message) {
        super(message);
    }
}
