package group0110.eventSystem.filters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import group0110.eventSystem.Event;

public class FilterByDates implements EventFilter {

    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Creates an event filter where events are filtered by a start and end date.
     * The end date must not be before the start date.
     *
     * @param startDate Events happen on or after this date. (inclusive)
     * @param endDate   Events happen on or before this date. (inclusive)
     */
    public FilterByDates(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public List<Event> filter(List<Event> events) {
        List<Event> filtered = new ArrayList<>();
        for (Event e : events) {
            LocalDate curDate = e.getDateTimeInterval().getDate();
            if (!curDate.isBefore(startDate) && !curDate.isAfter(endDate)) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    @Override
    public String toString() {
        return String.format("Displaying events from %s to %s", startDate, endDate);
    }
}
