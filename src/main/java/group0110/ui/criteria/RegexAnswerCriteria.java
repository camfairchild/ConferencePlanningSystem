package group0110.ui.criteria;

import java.util.regex.Pattern;

/**
 * RegexAnswerCriteria is a subset of AnswerCriteria that match a regex pattern.
 * @author Cameron
 */
public class RegexAnswerCriteria implements AnswerCriteria {
    private final Pattern pattern;

    /**
     * Creates a new RegexAnswerCriteria.
     * @param pattern String matching a regex pattern.
     * @param case_insensitive boolean indicating if the regex should match case-insensitive.
     */
    public RegexAnswerCriteria(String pattern, boolean case_insensitive) {
        if (case_insensitive) {
            this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        } else {
            this.pattern = Pattern.compile(pattern);
        }
    }

    /**
     * Creates a RegexAnswerCriteria with a given pattern.
     * Assumes the pattern should be case-sensitive.
     * @param pattern String matching a regex pattern.
     */
    public RegexAnswerCriteria(String pattern) {
        this(pattern, false);
    }

    /**
     *
     * @param answer String matching the answer in question.
     * @return true if the answer matches the pattern.
     */
    @Override
    public boolean check(String answer) {
        return pattern.matcher(answer).find();
    }
}
