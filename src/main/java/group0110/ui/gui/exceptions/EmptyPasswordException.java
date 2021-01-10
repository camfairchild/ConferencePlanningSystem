package group0110.ui.gui.exceptions;

/**
 * Exception is thrown when the password field is empty during signup or login.
 */
public class EmptyPasswordException extends EmptyFieldException {
    public EmptyPasswordException() {
        super("The password field is empty. Please enter a password.");
    }
}
