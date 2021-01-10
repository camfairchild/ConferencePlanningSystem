package group0110.eventSystem;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import group0110.eventSystem.exceptions.InvalidIntervalTimes;

/**
 * Represents an interval/period of time.
 *
 * @author Ha Young Kim
 */
public class TimeInterval implements Serializable {

    protected LocalTime startTime;
    protected LocalTime endTime;
    protected int duration;

    /**
     * Create a new time interval. The {@code startTime} must occur before
     * {@code endTime}
     *
     * @param startTime The starting time of the interval. This time is inclusive.
     * @param endTime   The ending time of the interval. This time is exclusive.
     * @throws InvalidIntervalTimes If {@code startTime} is after
     *                                  {@code endTime}.
     * @see LocalTime
     */
    public TimeInterval(LocalTime startTime, LocalTime endTime) throws InvalidIntervalTimes {
        if (!startTime.isBefore(endTime)) {
            throw new InvalidIntervalTimes("Start time must be before end time");
        }

        int startMinutes = 0;
        int endMinutes = 0;

        startMinutes += startTime.getHour() * 60 + startTime.getMinute();
        endMinutes += endTime.getHour() * 60 + endTime.getMinute();

        this.duration = endMinutes - startMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return The start time of the interval.
     * @see LocalTime
     */
    public LocalTime getStartTime() {
        return this.startTime;
    }

    /**
     * @return The end time of the interval.
     * @see LocalTime
     */
    public LocalTime getEndTime() {
        return this.endTime;
    }

    /**
     * @return The duration of the interval in minutes.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Determines whether or not this time interval overlaps with {@code other} time
     * interval.
     *
     * @param other The other time interval to be compared to.
     * @return Returns {@code true} if this time interval overlaps with
     *         {@code other} time interval. Otherwise, returns {@code false}.
     */
    public boolean overlaps(TimeInterval other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    /**
     * Determines whether or not this time interval contains {@code other} time
     * interval.
     *
     * @param other The other time interval to be compared to.
     * @return Returns {@code true} if this time interval contains {@code other}
     *         time interval. Otherwise, returns {@code false}.
     */
    public boolean contains(TimeInterval other) {
        return !this.startTime.isAfter(other.startTime) && !this.endTime.isBefore(other.endTime);
    }

    /**
     * Defines the equal method in the context of the time interval
     * @param o The given time interval
     * @return If the given time interval is this time interval
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TimeInterval that = (TimeInterval) o;
        return startTime.equals(that.startTime) && endTime.equals(that.endTime);
    }

    /**
     *
     * @return The hashcode of this time interval
     */
    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, duration);
    }

    /**
     *
     * @return A string representation of this time interval
     */
    @Override
    public String toString(){
        return "From "+ startTime + " to " + endTime;
    }
}
