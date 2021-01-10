package group0110.ui.gui.exceptions;

public class NewPasswordException extends UserLoginException {
    public NewPasswordException() {
        super("Password must contain at least one capital letter, symbol, and number. Please try again.");
    }
}
