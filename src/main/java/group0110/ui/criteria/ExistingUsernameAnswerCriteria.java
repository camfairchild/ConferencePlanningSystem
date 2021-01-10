package group0110.ui.criteria;

import group0110.usecases.user.UserManager;

/**
 * ExistingUsernameAnswerCriteria is an AnswerCriteria checking
 * if a user with a given username exists.
 */
public class ExistingUsernameAnswerCriteria implements AnswerCriteria {
    private UserManager userManager;

    /**
     * Creates an ExistingUsernameAnswerCriteria from the given userManager.
     * @param userManager UserManager matching the UserManager to be checked.
     */
    public ExistingUsernameAnswerCriteria(UserManager userManager) {this.userManager = userManager;
    }

    /**
     *
     * @param username String matching the username in question.
     * @return true if a user exists matching username.
     */
    @Override
    public boolean check(String username) {
        return userManager.getUser(username) != null;
    }
}
