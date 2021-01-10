package group0110.eventSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import group0110.eventSystem.exceptions.InvalidIntervalTimes;

/**
 * Represents an interval/period of time on a particular date.
 */

public class DateTimeInterval extends TimeInterval implements Comparable<DateTimeInterval> {

    protected LocalDate date;

    /**
     * Create a new date time interval. The starting time is inclusive and ending
     * time is exclusive in this interval. The {@code startDateTime} must occur
     * before {@code endTime}
     *
     * @param date        The date of the event.
     * @param startTime   The starting time of the interval. This time is inclusive.
     * @param endTime The ending time of the event. This time is exclusive.
     * @throws InvalidIntervalTimes If {@code startTime} is not before
     *                              {@code endTime}.
     * @see LocalDate
     * @see LocalTime
     */
    public DateTimeInterval(LocalDate date, LocalTime startTime, LocalTime endTime) throws InvalidIntervalTimes {
        super(startTime, endTime);
        this.date = date;
    }

    /**
     * Set the date of the event.
     *
     * @param date The new date of the event.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get the date of the event.
     *
     * @return Returns the date of the event.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the timeInterval of this DateTimeInterval.
     * @return Returns a timeInterval between startTime and endTime
     */
    public TimeInterval getTimeInterval(){
        try{
            return new TimeInterval(startTime, endTime);
        }
        catch(InvalidIntervalTimes e){
            return null;
        }
    }
    public boolean overlaps(DateTimeInterval o) {
        return super.overlaps(o) && this.date.equals(o.date);
    }

    public boolean contains(DateTimeInterval o) {
        return super.contains(o) && this.date.equals(o.date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateTimeInterval other = (DateTimeInterval) o;
        return startTime.equals(other.startTime) && endTime.equals(other.endTime) && date.equals(other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, duration, date);
    }

    @Override
    public String toString() {
        return String.format("On %s\n%s", getDate().toString(), getTimeInterval().toString());
    }

    @Override
    public int compareTo(DateTimeInterval o) {
        int compareDate = getDate().compareTo(o.getDate());
        if (compareDate == 0) {
            return getStartTime().compareTo(o.getStartTime());
        }
        return compareDate;
    }
}
