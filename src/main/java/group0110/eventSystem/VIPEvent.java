package group0110.eventSystem;

import group0110.entities.Role;
import group0110.entities.User;

/**
 * An event available to only VIPs. Organizers can also attend.
 */
public class VIPEvent extends Event {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 9092135206801501526L;

    /**
     * @param dateTimeInterval The time interval which the event occurs in.
     * @param room             The id of the room that this event happens in.
     * @param title            The title/description of this event.
     * @param maxCapacity      The maximum number of users who can attend the event.
     *                         Must be a positive number.
     *
     * @see DateTimeInterval
     */
    public VIPEvent(DateTimeInterval dateTimeInterval, String room, String title, int maxCapacity) {
        super(dateTimeInterval, room, title, maxCapacity);
    }

    /**
     * Checks if a given user can join this VIP event
     * @param user The user
     * @return if they can join
     */
    @Override
    public boolean canJoin(User user) {
        if (user.getRole() != Role.VIP && user.getRole() != Role.ORGANIZER) {
            return false;
        }
        if (users.getUserCount(Role.VIP, Role.ORGANIZER) < maxCapacity) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return The type of this VIP event
     */
    @Override
    public EventType getEventType() {
        return EventType.VIP;
    }

    /**
     *
     * @return a copy of this event
     */
    @Override
    public Event clone() {
        Event newEvent = new VIPEvent(dateTimeInterval, this.room, this.title, this.maxCapacity);
        newEvent.users = this.users.clone();
        newEvent.id = this.id;
        return newEvent;
    }

    /**
     * All of the information contained in this event
     * @return A string representation of this event
     */
    @Override
    public String toString() {
        String output = "=========VIP EVENT==========\n";
        output += String.format("Title: %s%n", this.title);
        output += String.format("Room: %s%n", this.room);
        output += String.format("Capacity: %s/%s%n", this.getUsers().getAllUserCount(), this.maxCapacity);
        output += String.format("Date: %s%n", this.dateTimeInterval.getDate());
        output += String.format("Time: From %s to %s%n", this.dateTimeInterval.getStartTime(),
                this.dateTimeInterval.getEndTime());

        return output;
    }

}
