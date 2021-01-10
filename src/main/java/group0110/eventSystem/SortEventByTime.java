package group0110.eventSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;


/**
 * Sorts events by increasing time.
 * @author Ha Young Kim
 */
class SortEventByTime implements Comparator<Event>{

    /**
     * Defines the compare method in the context of Event time
     * @param e1 The event being compared
     * @param e2 The other Event being compared
     * @return Positive, negative, or 0
     */
    @Override
    public int compare(Event e1, Event e2) {
        LocalDate e1Date = e1.getDateTimeInterval().getDate();
        LocalDate e2Date = e2.getDateTimeInterval().getDate();
        LocalTime e1StartTime = e1.getDateTimeInterval().getStartTime();
        LocalTime e2StartTime = e2.getDateTimeInterval().getStartTime();

        int res = e1Date.compareTo(e2Date);
        if (res == 0) {
            return e1StartTime.compareTo(e2StartTime);
        }
        return res;
    }
}
