package group0110.eventSystem.schedules;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * A ScheduleStrategy that sorts events alphabetically by their title
 */
public class ViewAlphabeticallyByTitle implements ScheduleStrategy {
    private EventSystem es;

    /**
     * Creates a ScheduleStrategy that sorts alphabetically by title
     * @param es an eventsystem that contains all the events
     */
    public ViewAlphabeticallyByTitle(EventSystem es){
        this.es = es;
    }

    /**
     * Creates a map mapping letters to events
     * @return a map that features single letters as keys, and events where their title starts with that letter
     * as values.
     */
    public Map<String, List<Event>> sort(){
        Map<String, List<Event>> FirstLetterOfTitleToEvent = new HashMap<>();
        List<Event> eventList = es.getAllEvents();
        for(Event e : eventList){
            String firstLetter = (e.getTitle().substring(0,1));
            if(FirstLetterOfTitleToEvent.containsKey(firstLetter)){
                FirstLetterOfTitleToEvent.get(firstLetter).add(e);
            }
            else {
                List<Event> events = new ArrayList<>();
                events.add(e);
                FirstLetterOfTitleToEvent.put(firstLetter,events);
            }
        }
        return FirstLetterOfTitleToEvent;
    }

}
