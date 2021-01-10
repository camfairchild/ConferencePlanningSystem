package group0110.ui.gui.exceptions;

/**
 * Exception is thrown when the username field is empty during signup or login.
 */
public class EmptyUsernameException extends EmptyFieldException {
    public EmptyUsernameException() {
        super("The username field is empty. Please enter a username.");
    }
}
