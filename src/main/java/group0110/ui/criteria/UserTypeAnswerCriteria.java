package group0110.ui.criteria;

/**
 * UserTypeAnswerCriteria is a RegexAnswerCriteria that checks
 * if the give answer matches one of a few UserType options.
 * The match is case-insensitive.
 * @author Cameron
 */
public class UserTypeAnswerCriteria extends RegexAnswerCriteria {
    /**
     * Creates a UserTypeAnswerCriteria
     */
    public UserTypeAnswerCriteria() {
        super("^(SPEAKER)|(ORGANIZER)|(ATTENDEE)|(VIP)$", true);
    }

    /**
     * Checks if the role answered matches one of the options for signup.
     * @param role String matching the role answered.
     * @return true if the role matches one of the options.
     */
    @Override
    public boolean check(String role) {
        return super.check(role);
    }
}
