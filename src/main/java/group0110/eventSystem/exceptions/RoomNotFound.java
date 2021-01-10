package group0110.eventSystem.exceptions;

/**
 * An exception when the room does not exist.
 */
public class RoomNotFound extends RuntimeException {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -9121345162170253972L;

    public RoomNotFound(String message) {
        super(message);
    }
}
