package group0110.eventSystem.exceptions;

/**
 * An exception that happens when creating an event with a maximum capacity that
 * is greater than the room's maximum capacity.
 */
public class ExceedingRoomMaxCapacity extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 1L;

    public ExceedingRoomMaxCapacity(String message) {
        super(message);
    }
}
