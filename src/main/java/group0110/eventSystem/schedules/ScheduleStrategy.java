package group0110.eventSystem.schedules;
import group0110.eventSystem.Event;
import java.util.Map;
import java.util.List;

/**
 * An strategy for how a schedule is organized
 */
public interface ScheduleStrategy {

    /**
     * Sorts all the events currently in the system by a certain feature.
     * @return A sorted map of all events, with whatever feature
     * that differentiates the events located in the key, and the list of
     * events that contain that feature as a value.
     */
    Map<String, List<Event>> sort();

}
