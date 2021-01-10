package group0110.ui.criteria;

/**
 * Classes implementing AnswerCriteria implement a method check() to
 * indicate whether a String input matches a given criteria.
 * @author Cameron
 */
public interface AnswerCriteria {

    /**
     * Checks if the answer meets the criteria.
     * @param answer String matching the answer in question.
     * @return boolean indicating if the answer meets the criteria.
     */
    boolean check(String answer);
}
