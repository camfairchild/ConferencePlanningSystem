package group0110.eventSystem;

import group0110.entities.Role;
import group0110.entities.User;

/**
 * Represents an event that has one speaker and attendees. The maximum capacity
 * of this event is determined by the number of attendees.
 *
 * @author Ha Young Kim
 */
public class Talk extends Event {

    /**
     * Generated serializable UID.
     */
    private static final long serialVersionUID = -6319591537342338651L;

    /**
     * @param dateTimeInterval The time interval which the event occurs in.
     * @param room             The id of the room that this event happens in.
     * @param title            The title/description of this event.
     * @param maxCapacity      The maximum number of users who can attend the event.
     *                         Must be a positive number.
     *
     * @see DateTimeInterval
     * @throws IllegalArgumentException If {@code speaker} is not a speaker.
     */
    public Talk(DateTimeInterval dateTimeInterval, String room, String title, int maxCapacity) {
        super(dateTimeInterval, room, title, maxCapacity);
    }

    @Override
    public EventType getEventType() {
        return EventType.TALK;
    }

    @Override
    public Event clone() {
        Event newEvent = new Talk(dateTimeInterval, this.room, this.title, this.maxCapacity);
        newEvent.users = this.users.clone();
        newEvent.id = this.id;
        return newEvent;
    }

    @Override
    public String toString() {
        String output = "=========TALK==========\n";
        output += String.format("Title: %s%n", this.title);
        output += String.format("Speaker: %s%n", this.users.getUserCount(Role.SPEAKER) == 0 ? "N/A"
                : this.users.getUsers(Role.SPEAKER).get(0).getUsername());
        output += String.format("Room: %s%n", this.room);
        output += String.format("Capacity: %s/%s%n",
                this.users.getAllUserCount(), this.maxCapacity);
        output += String.format("Date: %s%n", this.dateTimeInterval.getDate());
        output += String.format("Time: From %s to %s%n", this.dateTimeInterval.getStartTime(),
                this.dateTimeInterval.getEndTime());

        return output;
    }

    /**
     * Checks there is space for the given use to join and makes sure it is not a speaker
     * @param user The user to determine
     * @return if the given user can join
     */
    @Override
    public boolean canJoin(User user) {
        if (user.getRole() == Role.SPEAKER && this.users.getUserCount(Role.SPEAKER) == 0) {
            return true;
        }
        return user.getRole() != Role.SPEAKER;
    }
}
