package group0110.eventSystem;

import group0110.entities.Role;
import group0110.entities.User;

/**
 * Represents an event that only attendees. The maximum capacity of this event
 * is determined by the number of attendees.
 *
 * @author Ha Young Kim
 */
public class Party extends Event {

    /**
     * Generated serializable UID.
     */
    private static final long serialVersionUID = -4437365426241017663L;

    /**
     * @param dateTimeInterval The time interval which the event occurs in.
     * @param room             The id of the room that this event happens in.
     * @param title            The title/description of this event.
     * @param maxCapacity      The maximum number of users who can attend the event.
     *                         Must be a positive number.
     *
     * @see DateTimeInterval
     * @throws IllegalArgumentException If the maximum capacity is not greater than
     *                                  0.
     */
    public Party(DateTimeInterval dateTimeInterval, String room, String title, int maxCapacity) {
        super(dateTimeInterval, room, title, maxCapacity);
    }

    /**
     * Defines EventType in the context of a Party
     * @return Event type as PARTY
     */
    @Override
    public EventType getEventType() {
        return EventType.PARTY;
    }

    /**
     * Checks an attendee can attend the party
     * @param user The user to determine
     * @return If the user can join
     */
    @Override
    public boolean canJoin(User user) {
        return user.getRole() != Role.SPEAKER;
    }

    /**
     *
     * @return a copy of this Party
     */
    @Override
    public Event clone() {
        Event newEvent = new Party(dateTimeInterval, this.room, this.title, this.maxCapacity);
        newEvent.users = this.users.clone();
        newEvent.id = this.id;
        return newEvent;
    }

    /**
     *
     * @return A string representation of this party's information
     */
    @Override
    public String toString() {
        String attendees = "Attendees: \n";
        for (User attendee : this.users.getUsers(Role.ATTENDEE)) {
            attendees += "    " + attendee.getUsername() + "\n";
        }
        String output = "=========PARTY==========\n";
        output += String.format("Title: %s%n", this.title);
        output += String.format("Room: %s%n", this.room);
        output += attendees;
        output += String.format("Capacity: %s/%s%n", this.users.getAllUserCount(),
                this.maxCapacity);
        output += String.format("Date: %s%n", this.dateTimeInterval.getDate());
        output += String.format("Time: From %s to %s%n", this.dateTimeInterval.getStartTime(),
                this.dateTimeInterval.getEndTime());

        return output;
    }

}
