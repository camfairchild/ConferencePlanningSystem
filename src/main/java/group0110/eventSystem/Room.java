package group0110.eventSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a room that is available for a particular time interval and can be
 * booked within this time period.
 *
 * @author Ha Young Kim
 */
public class Room implements Serializable {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -3982238411970905615L;

    /**
     * The unique id of the room.
     */
    String id;

    /**
     * The date and time intervals that are booked in this room.
     */
    List<DateTimeInterval> bookedTimeIntervals;

    /**
     * The time interval that this room is available to be booked for.
     */
    TimeInterval availableTimeInterval;

    /**
     * The maximum number of users the room can contain.
     */
    int maxCapacity;

    /**
     * A new room. The room is available to be booked in
     * {@code availableTimeInterval}.
     *
     * @param id                    The unique id for the room. Must be a unique id
     *                              that does not belong to another room.
     * @param availableTimeInterval The time interval that this room is available to
     *                              be booked for.
     * @param maxCapacity           The maximum number of users the room can contain
     *                              at any given time.
     */
    public Room(String id, TimeInterval availableTimeInterval, int maxCapacity) {
        this.id = id;
        this.availableTimeInterval = availableTimeInterval;
        this.maxCapacity = maxCapacity;
        this.bookedTimeIntervals = new ArrayList<>();
    }

    /**
     * @return The unique id of this room.
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return Returns the maximum number of users the room can contain.
     */
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    /**
     * @return Returns the booked time intervals in this room.
     * @see TimeInterval
     */
    public List<DateTimeInterval> getBookedTimeIntervals() {
        List<DateTimeInterval> clone = new ArrayList<>();
        for (DateTimeInterval dti : this.bookedTimeIntervals) {
            clone.add(dti);
        }
        return clone;
    }

    /**
     * Book a time interval that is free and available in this room.
     *
     * @param dateTimeInterval The time interval to book in this room.
     * @throws IllegalArgumentException If {@code timeInterval} overlaps with an
     *                                  existing time interval or not part of the
     *                                  {@code availableTimeInterval}.
     */
    void bookTime(DateTimeInterval dateTimeInterval) {
        if (!canBook(dateTimeInterval)) {
            throw new IllegalArgumentException("Cannot book at this date time.");
        }
        this.bookedTimeIntervals.add(dateTimeInterval);
    }

    /**
     * Un-book all booked time intervals in the room that either overlaps
     * {@link TimeInterval#overlaps(TimeInterval)} or are contained by
     * {@link TimeInterval#contains(TimeInterval)} {@code timeInterval}
     *
     * @param dateTimeInterval The time interval to be unbooked.
     */
    void unbookTime(DateTimeInterval dateTimeInterval) {
        int i = this.bookedTimeIntervals.size() - 1;
        while (i >= 0) {
            DateTimeInterval currTI = this.bookedTimeIntervals.get(i);
            if (currTI.overlaps(dateTimeInterval) || dateTimeInterval.contains(currTI)) {
                this.bookedTimeIntervals.remove(i);
            }
            i--;
        }
    }

    /**
     * Determine if this room can be booked at {@code dateTimeInterval}.
     *
     * @param dateTimeInterval The date time interval {@link DateTimeInterval}.
     * @return Return {@code true} if the date time interval is available in this
     *         room. Otherwise returns {@code false}.
     */
    public boolean canBook(DateTimeInterval dateTimeInterval) {
        if (!this.availableTimeInterval.contains(dateTimeInterval)) {
            return false;
        }

        for (DateTimeInterval t : this.bookedTimeIntervals) {
            if (dateTimeInterval.overlaps(t)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return The time interval that this room is available to be booked for.
     */
    public TimeInterval getAvailableTimeInterval() {
        return availableTimeInterval;
    }

    /**
     * Defines the clone method in the context of this Room
     * @return a copy of this method
     */
    @Override
    public Room clone() {
        Room newRoom = new Room(this.id, this.availableTimeInterval, this.maxCapacity);
        for (DateTimeInterval bookedTimeInterval : this.bookedTimeIntervals) {
            newRoom.bookTime(bookedTimeInterval);
        }
        return newRoom;
    }

    /**
     * Defines the equals method in the context of Room
     * @param o The given object
     * @return If the given object is this Room
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    /**
     *
     * @return a hashcode of this room
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, bookedTimeIntervals, availableTimeInterval);
    }
}
