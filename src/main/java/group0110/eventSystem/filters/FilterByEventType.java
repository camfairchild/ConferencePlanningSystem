package group0110.eventSystem.filters;

import java.util.ArrayList;
import java.util.List;

import group0110.eventSystem.Event;
import group0110.eventSystem.EventType;

public class FilterByEventType implements EventFilter {

    private List<EventType> types;

    /**
     * Creates an event filter where events are filtered by event types.
     * @param types The types that should be shown in the events list.
     */
    public FilterByEventType(List<EventType> types) {
        this.types = types;
    }

    @Override
    public List<Event> filter(List<Event> events) {
        List<Event> filtered = new ArrayList<>();
        for (Event e : events) {
            if (types.contains(e.getEventType())) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    @Override
    public String toString() {
        String visibleTypes = "";
        for (EventType type : types) {
            visibleTypes += String.format("%s, ", type);
        }
        return String.format("Displaying events that are %s", visibleTypes);
    }
}
