package group0110.eventSystem.exceptions;

/**
 * This exception is when the room's maximum capacity is reached and cannot
 * accept more users.
 */
public class RoomIsFull extends Exception {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 1L;

    public RoomIsFull(String message) {
        super(message);
    }
}
