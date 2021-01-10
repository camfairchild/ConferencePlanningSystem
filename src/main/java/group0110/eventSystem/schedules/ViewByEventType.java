package group0110.eventSystem.schedules;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.EventType;

import java.util.List;

/**
 * A ScheduleStrategy that sorts events based on their type
 */
public class ViewByEventType implements ScheduleStrategy {
    private EventSystem es;

    /**
     * Creates a ScheduleStrategy that sorts by event type
     * @param es an eventsystem that contains all the events
     */
    public ViewByEventType(EventSystem es){
        this.es = es;
    }

    /**
     * Creates a map mapping event types to events
     * @return a map that features event types as keys, and events which match that type
     * as values.
     */
    public Map<String, List<Event>> sort(){
        Map<String, List<Event>> EventTypeToEvent = new HashMap<>();
        List<Event> eventList = es.getAllEvents();
        for(Event e:eventList) {
            String eventType = formatEventType(e);
            if (EventTypeToEvent.containsKey(eventType)){
                EventTypeToEvent.get(eventType).add(e);
            }
            else{
                List<Event> events = new ArrayList<>();
                events.add(e);
                EventTypeToEvent.put(eventType, events);
            }
        }
        return EventTypeToEvent;
    }

    /**
     * Formats the type of event into a plural/nicer format
     * @param e The event that we want to format the type for
     * @return a string conataining the type of event in plural form
     */
    private String formatEventType(Event e) {
        EventType type = e.getEventType();
        switch (type) {
            case TALK:
                return "Talks";
            case PARTY:
                return "Parties";
            case DISCUSSION:
                return "Discussions";
            case VIP:
                return "VIP Events";
            default:
                return "Unknown";
        }
    }
}
