package group0110.eventSystem.schedules;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import java.util.List;

/**
 * A ScheduleStrategy that sorts events based on the day that they occur.
 */
public class ViewByDay implements ScheduleStrategy {
    private EventSystem es;

    /**
     * Creates a ScheduleStrategy that sorts by day
     * @param es an eventsystem that contains all the events
     */
    public ViewByDay(EventSystem es){
        this.es = es;
    }

    /**
     * Creates a map mapping dates to events
     * @return a map that features dates as keys, and events that occur on that date
     * as values.
     */
    public Map<String, List<Event>> sort(){
        Map<String, List<Event>> DayToEvent = new HashMap<>();
        List<Event> eventList = es.getAllEvents();
        for(Event e:eventList){
            String date = e.getDateTimeInterval().getDate().toString();
            if(DayToEvent.containsKey(date)){
                DayToEvent.get(date).add(e);
            }
            else{
                List<Event> events = new ArrayList<>();
                events.add(e);
                DayToEvent.put(date, events);
            }
        }

        return DayToEvent;

    }
}
