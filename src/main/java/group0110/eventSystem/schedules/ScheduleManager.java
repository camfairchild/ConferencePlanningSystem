package group0110.eventSystem.schedules;
import group0110.eventSystem.EventSystem;
import java.util.List;
import java.util.Map;
import group0110.eventSystem.Event;

/**
 * Creates schedules of events based on different criteria
 */
public class ScheduleManager {
    private ScheduleStrategy scheduleStrategy;

    /**
     * Creates a new scheduleManager
     * @param es an eventSystem
     * @param type the feature which the schedule should sort the events based on
     */
    public ScheduleManager(EventSystem es, String type){
        ScheduleStrategyFactory ssf = new ScheduleStrategyFactory(es);
        this.scheduleStrategy = ssf.getScheduleStrategy(type);
    }

    /**
     * Creates a map that differentiates events based on a certain criteria
     * @return a map where a certain criteria are the keys, and a list of events that fit
     * those criteria are the values
     */
    public Map<String, List<Event>> createSchedule(){
        return scheduleStrategy.sort();
    }


}
