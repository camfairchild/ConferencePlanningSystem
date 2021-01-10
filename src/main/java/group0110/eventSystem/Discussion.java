package group0110.eventSystem;

import group0110.entities.Role;
import group0110.entities.User;

/**
 * Represents an event that has multiple speakers. There can be attendees who
 * watch the discussion. The maximum capacity of this event is determined by the
 * number of attendees.
 *
 * @author Ha Young Kim
 */
public class Discussion extends Event {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 4678845016783764100L;

    /**
     * @param dateTimeInterval The time interval which the event occurs in.
     * @param room             The id of the room that this event happens in.
     * @param title            The title/description of this event.
     * @param maxCapacity      The maximum number of users who can attend the event.
     *                         Must be a positive number.
     *
     * @see DateTimeInterval
     */
    public Discussion(DateTimeInterval dateTimeInterval, String room, String title, int maxCapacity) {
        super(dateTimeInterval, room, title, maxCapacity);
    }

    @Override
    public EventType getEventType() {
        return EventType.DISCUSSION;
    }

    @Override
    public boolean canJoin(User user) {
        return true;
    }

    @Override
    public Event clone() {
        Event newEvent = new Discussion(this.dateTimeInterval, this.room, this.title, this.maxCapacity);
        newEvent.users = this.users.clone();
        newEvent.id = this.id;
        return newEvent;
    }

    @Override
    public String toString() {
        String speakers = "Speakers: \n";
        for (User speaker : this.users.getUsers(Role.SPEAKER)) {
            speakers += "    " + speaker.getUsername() + "\n";
        }
        String output = "=========DISCUSSION==========\n";
        output += String.format("Title: %s%n", this.title);
        output += String.format("Room: %s%n", this.room);
        output += speakers;
        output += String.format("Capacity: %s/%s%n", this.users.getAllUserCount(), maxCapacity);
        output += String.format("Date: %s%n", this.dateTimeInterval.getDate());
        output += String.format("Time: From %s to %s%n", this.dateTimeInterval.getStartTime(),
                this.dateTimeInterval.getEndTime());

        return output;
    }

}
