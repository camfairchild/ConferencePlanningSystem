package group0110.login;

import group0110.entities.Role;
import group0110.usecases.user.UserManager;


public class CreateAccount {
    /**
     * Creates a new account
     */
    private final String username;
    private final String password;
    private final Role userType;
    private final UserManager uM;

    /**
     * Create a new event to take place at the conference.
     * The event must have a speaker, a room to take place in,
     * and a date and time.
     *
     * @param inputUsername the username the user is wants to use for the new account
     * @param inputPassword the password for this account
     * @param userType      the type of user (Speaker/ Organizer/ Attendee)
     * @param inputUM UserManager of the current UserManager
     */
    public CreateAccount(String inputUsername, String inputPassword, Role userType, UserManager inputUM) {
        this.username = inputUsername;
        this.password = inputPassword;
        this.userType = userType;
        this.uM = inputUM;

    }

    private boolean UsernameAvailable() {
        return uM.getUser(username) == null;
    }


    private boolean AcceptablePassword() {
        boolean digitCheck = false;
        boolean symbolCheck = false;
        boolean capitalLetterCheck = false;

        for (int i = 0; i < this.password.length(); i++) {
            char currentChar = this.password.charAt(i);

            if (Character.isDigit(currentChar)) {
                digitCheck = true;
            }
            if (Character.isUpperCase(currentChar)) {
                capitalLetterCheck = true;
            }
            if (!Character.isLetter(currentChar) && !Character.isDigit(currentChar) && !(currentChar == ' ')) {
                symbolCheck = true;
            }
        }
        return (symbolCheck && capitalLetterCheck && digitCheck);
    }

    /**Creates the Account if the Password and the Username are both acceptable
     *
     */
    public void CreateNewAccount() {
        if (AcceptablePassword() && UsernameAvailable()) {
            this.uM.addUser(username, password, userType);
        }
    }
}
