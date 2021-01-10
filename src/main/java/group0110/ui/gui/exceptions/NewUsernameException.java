package group0110.ui.gui.exceptions;

public class NewUsernameException extends UserLoginException {
    public NewUsernameException() {
        super("Sorry! Username is taken. Please try again.");
    }
}
