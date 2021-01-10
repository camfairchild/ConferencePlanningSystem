package group0110.ui.gui.exceptions;

/**
 * Exception is thrown when given text is not within a valid time interval.
 */
public class InvalidTimeException extends Exception{
    public InvalidTimeException(String errorMessage) { super(errorMessage); }
}
