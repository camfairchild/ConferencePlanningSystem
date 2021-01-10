package group0110.eventSystem;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import group0110.entities.User;
import group0110.eventSystem.exceptions.EventMaximumCapacityNotPositive;
import group0110.eventSystem.exceptions.UserTypeNotAllowedInEvent;

/**
 * Represents an event that takes place at the conference. Users can join an
 * event as long as the maximum capacity is not exceeded and they are permitted
 * to join the event. An event has a date and time, and a room that it takes
 * place in.
 *
 * @author Ha Young Kim
 */

public abstract class Event implements Serializable {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The unique id of the event.
     */
    String id;

    /**
     * The ids of users who attend this event.
     */
    UserGroup users;

    /**
     * The id of the room that the event occurs in.
     */
    String room;

    /**
     * The title/description of the event.
     */
    String title;

    /**
     * The maximum number of users who can attend the event. Must be a positive
     * number.
     */
    int maxCapacity;

    /**
     * The time interval in which the event occurs in.
     */
    DateTimeInterval dateTimeInterval;

    /**
     * @param dateTimeInterval The time interval which the event occurs in.
     * @param room             The id of the room that this event happens in.
     * @param title            The title/description of this event.
     * @param maxCapacity      The maximum number of users who can attend the event.
     *                         Must be a positive number.
     *
     * @see DateTimeInterval
     * @throws EventMaximumCapacityNotPositive If the {@code maxCapacity} is not a
     *                                         positive number.
     *
     */
    public Event(DateTimeInterval dateTimeInterval, String room, String title, int maxCapacity)
            throws EventMaximumCapacityNotPositive {
        if (maxCapacity <= 0) {
            throw new EventMaximumCapacityNotPositive("The maximum capacity for an event should be positive.");
        }
        this.id = UUID.randomUUID().toString();
        this.users = new UserGroup();
        this.room = room;
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.dateTimeInterval = dateTimeInterval;
    }

    /**
     * Add a user to this event. An user can be added only if they
     * {@link #canJoin(User)} the event.
     *
     * @param user The user to be added to this event.
     * @throws UserTypeNotAllowedInEvent
     */
    void addUser(User user) throws UserTypeNotAllowedInEvent {
        if (!this.canJoin(user)) {
            throw new UserTypeNotAllowedInEvent(
                    String.format("User of type %s is not allowed to attend this event.", user.getRole().toString()));
        }

        this.users.addUser(user);
    }

    /**
     * Remove a user from this event. The user must already be attending the event.
     *
     * @param user The user to be removed.
     */
    void removeUser(User user) {
        this.users.removeUser(user);
    }

    /**
     * Determines if a user can be added to the event without exceeding the maximum
     * capacity limit.
     *
     * @param user The user to determine
     * @return Returns {@code true} if space is available in the event.
     */
    public abstract boolean canJoin(User user);

    /**
     * @return Returns the id of the event.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The time interval interval in which this event occurs in.
     * @see DateTimeInterval
     */
    public DateTimeInterval getDateTimeInterval() {
        return this.dateTimeInterval;
    }

    /**
     * @return A list of the users in this event.
     */
    public UserGroup getUsers() {
        return this.users.clone();
    }

    /**
     * @return Returns the id of the room in which this event occurs in.
     */
    public String getRoom() {
        return room;
    }

    /**
     * @return The title/description of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The maximum number of users who can attend this event.
     */
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    /**
     * @param roomId The id of the new room for this event {@link #getRoom()}
     */
    void setRoom(String roomId) {
        this.room = roomId;
    }

    void setDateTimeInterval(DateTimeInterval dateTimeInterval) {
        this.dateTimeInterval = dateTimeInterval;
    }

    /**
     * Gets the specific type/subclass of event
     *
     * @return Returns a type of event.
     */
    public abstract EventType getEventType();

    public abstract Event clone();

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Event that = (Event) o;
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    void setMaxCapacity(int newCap) {
        maxCapacity = newCap;
    };
}
