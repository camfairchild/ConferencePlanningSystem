package group0110.ui.criteria;

import group0110.usecases.user.UserManager;

/**
 * NewUsernameAnswerCriteria an AnswerCriteria that checks if the username is available.
 * @author Cameron
 */
public class NewUsernameAnswerCriteria implements AnswerCriteria {
    private UserManager userManager;

    /**
     * Creates a NewUsernameAnswerCriteria with a given UserManager.
     * @param userManager UserManager matching the userManager to check.
     */
    public NewUsernameAnswerCriteria(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     *
     * @param username String matching the username in question.
     * @return true if the username is available.
     */
    @Override
    public boolean check(String username) {
        return userManager.getUser(username) == null;
    }
}
