package group0110.eventSystem.exceptions;

/**
 * This exception happens when the interval of time in
 * {@link group0110.eventSystem.TimeInterval} is not valid.
 */
public class InvalidIntervalTimes extends Exception {
    /**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -3876696275097282212L;

	public InvalidIntervalTimes(String message) {
        super(message);
    }
}
