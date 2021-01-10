package group0110.ui.gui.exceptions;

/**
 * Thrown when the login credentials (username or password) or role are incorrect.
 */
public class UserLoginException extends Exception {
    public UserLoginException(String errorMessage) {
        super(errorMessage);
    }
}
