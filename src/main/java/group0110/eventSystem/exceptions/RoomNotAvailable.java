package group0110.eventSystem.exceptions;

/**
 * This exception is when a {@link group0110.eventSystem.Room} is unavailable at a
 * certain {@link group0110.eventSystem.TimeInterval}.
 */
public class RoomNotAvailable extends Exception {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 1L;

    public RoomNotAvailable(String message) {
        super(message);
    }
}
