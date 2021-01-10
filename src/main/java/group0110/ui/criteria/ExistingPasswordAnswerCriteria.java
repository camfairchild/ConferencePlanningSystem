package group0110.ui.criteria;

import group0110.login.ValidatePassword;
import group0110.usecases.user.UserManager;

/**
 * ExistingPasswordAnswerCriteria checks if the password submitted is the correct password for the user.
 * @author Cameron
 */
public class ExistingPasswordAnswerCriteria implements AnswerCriteria {
    private String username;
    private UserManager userManager;

    /**
     * Creates an ExistingPasswordAnswerCriteria for a given username and userManager.
     * @param username String matching the username for the user in question.
     * @param userManager UserManager matching the UserManager for the given user.
     */
    public ExistingPasswordAnswerCriteria(String username, UserManager userManager) {
        this.username = username;
        this.userManager = userManager;
    }

    /**
     * Checks if the password is the correct password for the user.
     * @param password String matching the password in question.
     * @return true if the password matches the user's password.
     */
    @Override
    public boolean check(String password) {
        ValidatePassword userInfo = new ValidatePassword(password, username, userManager);
        return userInfo.validate();
    }
}
