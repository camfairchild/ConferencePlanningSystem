package group0110.eventSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group0110.entities.Role;
import group0110.entities.User;
import group0110.eventSystem.exceptions.EventNotFound;

/**
 * Stores the {@link Event} objects.
 *
 * @author Ha Young Kim
 */
class EventManager {

    /**
     * Stores all event objects in the system. Stored as map of key: id value: event
     * object.
     */
    private Map<String, Event> eventIdToEvent;

    public EventManager() {
        this.eventIdToEvent = new HashMap<>();
    }

    /**
     * Create a event manager and add the events from {@code eventList}
     *
     * @param eventList the List of Events to manage
     */
    public EventManager(List<Event> eventList) {
        this.eventIdToEvent = new HashMap<>();
        for (Event e : eventList) {
            this.eventIdToEvent.put(e.getId(), e);
        }
    }

    /**
     * Determines if an event with {@code eventId} exists.
     *
     * @param eventId a String representing the ID of the Event to check
     *
     * @return Returns {@code true} if the event exists and otherwise returns
     *         {@code false}
     */
    public boolean hasEvent(String eventId) {
        return this.eventIdToEvent.containsKey(eventId);
    }

    /**
     * Get a list of all of the events. *
     *
     * @return The list of all the events.
     */
    public List<Event> getAllEvents() {
        ArrayList<Event> allEvents = new ArrayList<>();

        for (Event currEvent : eventIdToEvent.values()) {
            allEvents.add(currEvent);
        }

        return allEvents;
    }

    /**
     * Retrieves the list of events that the user joined.
     *
     * @param user the user.
     * @return Returns a list of events that the user joined.
     */
    public List<Event> getUserEvents(User user) {
        List<Event> userEvents = new ArrayList<>();

        for (Event currEvent : eventIdToEvent.values()) {
            if (currEvent.getUsers().hasUser(user)) {
                userEvents.add(currEvent);
            }
        }

        return userEvents;
    }

    /**
     * Retrieve the event with {@code id}.
     *
     * @param eventId event id.
     * @return The event with {@code id}.
     * @throws EventNotFound If event with {@code id} is not found.
     */
    public Event getEventById(String eventId) {
        Event e = eventIdToEvent.get(eventId);
        if (e == null) {
            throw new EventNotFound(String.format("The event with %s does not exist.", eventId));
        }
        return e;
    }

    /**
     * Add an event to the manager.
     *
     * @param e The event to add to the manager.
     */
    public void addEvent(Event e) {
        this.eventIdToEvent.put(e.getId(), e);
    }

    /**
     * Removes an event from the manager. The event must exist in the manager.
     *
     * @param eventId The id of the event to be removed.
     */
    public void removeEvent(String eventId) {
        this.eventIdToEvent.remove(eventId);
    }

    /**
     * Determines if a user with {@code id} is available at {@code dateTimeInterval}
     *
     * @param user             The user.
     * @param dateTimeInterval The time interval to check for.
     * @return Returns {@code true} if and only if the user is available at the
     *         given date and time interval
     */
    public boolean isUserAvailable(User user, DateTimeInterval dateTimeInterval) {
        List<Event> userEvents = this.getUserEvents(user);
        for (Event e : userEvents) {
            if (e.getDateTimeInterval().overlaps(dateTimeInterval)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the User is enrolled in the Event.
     * @param event the Event to check
     * @param user the User to check
     * @return true if the User is enrolled in the Event.
     */
    boolean isUserEnrolled(Event event, User user) {
        return event.getUsers().hasUser(user);
    }

    /**
     * Checks if user can join the event
     * @param event the Event to check.
     * @param user the User to check.
     * @return true if the user can join.
     */
    boolean canJoin(Event event, User user) {
        // TODO: maybe this should be in UserManager?
        return event.canJoin(user);
    }
}
