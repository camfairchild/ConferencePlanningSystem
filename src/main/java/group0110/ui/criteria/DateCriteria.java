package group0110.ui.criteria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Ensures that the input string is of a valid for
 */
public class DateCriteria implements AnswerCriteria {

	@Override
	public boolean check(String answer) {
        DateTimeFormatter dtf = DateTimeFormatter.BASIC_ISO_DATE;
        try {
            LocalDate.parse(answer, dtf);
        } catch(DateTimeParseException e){
            return false;
        }

        return true;
	}

}
