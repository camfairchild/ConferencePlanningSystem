package group0110.eventSystem.schedules;
import group0110.eventSystem.EventSystem;

/**
 * Factory to create different classes that implement ScheduleStrategy
 */
public class ScheduleStrategyFactory {
    private EventSystem es;

    /**
     * Creates a new SchedulesStrategyFactory
     * @param es the eventsystem to be passed into the ScheduleStrategys
     */
    public ScheduleStrategyFactory(EventSystem es){
        this.es = es;
    }

    /**
     * Creates a certain ScheduleStrategy based on the given strategy
     * @param strategy the feature that the schedule strategy should differentiate by
     * @return the ScheduleStrategy that differentiates events based on strategy
     */
    public ScheduleStrategy getScheduleStrategy(String strategy){
        if(strategy.equalsIgnoreCase("type")){
            return new ViewByEventType(es);
        }
        else if(strategy.equalsIgnoreCase("time")){
            return new ViewByTime(es);
        }
        else if (strategy.equalsIgnoreCase("title")){
            return new ViewAlphabeticallyByTitle(es);
        }
        else{
            return new ViewByDay(es);
        }
    }
}
