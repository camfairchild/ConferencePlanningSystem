package group0110.eventSystem.schedules;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * A ScheduleStrategy that sorts events based on the time that they occur
 */
public class ViewByTime implements ScheduleStrategy {
    private EventSystem es;

    /**
     * Creates a ScheduleStrategy that sorts by time interval
     * @param es an eventsystem that contains all the events
     */
    public ViewByTime(EventSystem es){
        this.es = es;
    }

    /**
     * Creates a map mapping time intervals to events
     * @return a map that features time intervals as keys, and events that occur in that time interval
     * as values.
     */
    public Map<String, List<Event>> sort(){
        Map<String, List<Event>> TimeToEvent = new HashMap<>();
        List<Event> eventList = es.getAllEvents();
        for(Event e : eventList){
            String time = e.getDateTimeInterval().getTimeInterval().toString();
            if (TimeToEvent.containsKey(time)){
                TimeToEvent.get(time).add(e);
            }
            else{
                List<Event> events = new ArrayList<>();
                events.add(e);
                TimeToEvent.put(time, events);
            }
        }
        return TimeToEvent;
    }
}
