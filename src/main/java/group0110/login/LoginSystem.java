package group0110.login;

import group0110.entities.User;
import group0110.usecases.user.UserManager;

public class LoginSystem {
    /**
     * Where users can log into their account
     */
    private String username;
    private final String password;
    private final UserManager uM;


    /**
     * Allow a user access to their menu if their username and password are correct
     *
     * @param username the username of the user attempting to login
     * @param password password of same user
     * @param inputUserManager UserManager of the current UserManager
     */

    public LoginSystem(String username, String password, UserManager inputUserManager) {
        this.username = username;
        this.password = password;
        this.uM = inputUserManager;
    }

    private boolean validation() {
        ValidatePassword vP = new ValidatePassword(password, username, uM);
        return vP.validate();
    }

    /**
     * Returns the User if the password is correct
     * @return User for User if the password is correct, else null.
     */
    public User loggedIn() {
        if (validation()) {
            return this.uM.getUser(username);
        }
        return null;
    }
}
