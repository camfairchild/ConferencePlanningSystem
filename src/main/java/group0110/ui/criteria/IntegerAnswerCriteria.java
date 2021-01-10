package group0110.ui.criteria;

/**
 * IntegerAnswerCriteria is a RegexAnswerCriteria that checks
 * if the given answer is an Integer in a range.
 * @author Cameron
 */
public class IntegerAnswerCriteria extends RegexAnswerCriteria {
    private final int start;
    private final int end;

    /**
     * Creates an IntegerAnswerCriteria in the given range of start to end.
     *
     * @param start int representing the start of the range.
     * @param end int representing the end (inclusive) of the range.
     */
    public IntegerAnswerCriteria(int start, int end) {
        super("^(-?[1-9]+[0-9]*)|0$");
        this.start = start;
        this.end = end;
    }

    /**
     * Checks if the answer is an int in the range of start to end inclusive.
     * @param answer String matching the answer in question.
     * @return true if the answer is an integer in the range.
     */
    @Override
    public boolean check(String answer) {
         if (!super.check(answer)) {
             return false;
         } else {
             int num = Integer.parseInt(answer);
             return start <= num && num <= end;
         }
    }
}
