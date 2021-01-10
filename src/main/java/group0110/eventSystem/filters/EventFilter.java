package group0110.eventSystem.filters;

import java.util.List;

import group0110.eventSystem.Event;

public interface EventFilter {
    /**
     * Filters the input list of events.
     *
     * @param events The list of events to filter.
     * @return Returns the filtered list.
     */
    public abstract List<Event> filter(List<Event> events);
}
