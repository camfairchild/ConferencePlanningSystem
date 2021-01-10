package group0110.eventSystem;

/**
 * Types of events
 */
public enum EventType {
    /**
     * One speaker, many attendees
     */
    TALK,
    /**
     * Many speakers, many attendees
     */
    DISCUSSION,
    /**
     * Only attendees
     */
    PARTY,
    /**
     * Only VIPs can attend.
     */
    VIP
}
