package group0110.ui.gui.exceptions;

/**
 * Exception is thrown when the role field is empty during signup or login.
 */
public class EmptyRoleException extends EmptyFieldException {
    public EmptyRoleException() {
        super("The role field is empty. Please choose a role.");
    }
}
