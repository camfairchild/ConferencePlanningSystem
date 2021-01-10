package group0110.eventSystem;

/**
 * A factory for creating events of {@code EventType} given general event
 * information.
 */
public class EventFactory {
    /**
     * Creates an event given general information of the corresponding event type.
     *
     * @param eventType        The type of event to create.
     * @param dateTimeInterval Date and time interval of when the event should
     *                         happen.
     * @param room             The ID of the room the event happens in.
     * @param title            The title of the event
     * @param maxCapacity      The maximum number of users that can join the event.
     *
     * @return A new event with type of {@code eventType}
     */
    public static Event create(EventType eventType, DateTimeInterval dateTimeInterval, String room, String title,
            int maxCapacity) {
        Event newEvent;
        switch (eventType) {
            case TALK:
                newEvent = new Talk(dateTimeInterval, room, title, maxCapacity);
                break;
            case DISCUSSION:
                newEvent = new Discussion(dateTimeInterval, room, title, maxCapacity);
                break;
            case PARTY:
                newEvent = new Party(dateTimeInterval, room, title, maxCapacity);
                break;
            case VIP:
                newEvent = new VIPEvent(dateTimeInterval, room, title, maxCapacity);
                break;
            default:
                newEvent = new Party(dateTimeInterval, room, title, maxCapacity);
        }
        return newEvent;
    }

}
