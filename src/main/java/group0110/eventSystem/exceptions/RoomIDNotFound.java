package group0110.eventSystem.exceptions;

/**
 * An exception when the room ID does not belong to any existing room.
 */
public class RoomIDNotFound extends Exception {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -9121345162170253972L;

    public RoomIDNotFound(String message) {
        super(message);
    }
}
