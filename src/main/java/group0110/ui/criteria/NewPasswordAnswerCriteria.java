package group0110.ui.criteria;

/**
 * NewPasswordAnswerCriteria is a RegexAnswerCriteria that checks
 * if the answer given matches the requirements for a new password.
 * @author Cameron
 */
public class NewPasswordAnswerCriteria extends RegexAnswerCriteria {
    /**
     * Creates a NewPasswordAnswerCriteria.
     */
    public NewPasswordAnswerCriteria() {
        // Contains at least: 1 Capital, 1 Digit, and 1 Symbol [#?!@$%^&*-]
        super("^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).*$");
    }

    /**
     * Checks if the given password meets the password requirements.
     * @param password String matching the password to check.
     * @return true if the password meets the requirements.
     */
    @Override
    public boolean check(String password) {
        return super.check(password);
    }
}
