package group0110.ui.criteria;

import java.util.List;

import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.Room;

/**
 * ExistingEventAnswerCriteria is a criteria for checking if string input
 * matches the ID of an already existing event in the list of events and rooms.
 *
 * @author Cameron
 */
public class ExistingEventAnswerCriteria implements AnswerCriteria {
    private List<Room> listOfRooms;
    private List<Event> listOfEvents;

    /**
     * Creates a new EventAnswer criteria
     * @param listOfEvents a List of events to consider.
     * @param listOfRooms a List of rooms to consider.
     */
    public ExistingEventAnswerCriteria(List<Event> listOfEvents, List<Room> listOfRooms) {
        this.listOfEvents = listOfEvents;
        this.listOfRooms = listOfRooms;
    }

    /**
     *
     *
     * @param eventId String matching the eventID in question.
     * @return true if the eventID is an existing event in
     * the list of events and rooms. Or equals "0".
     */
    @Override
    public boolean check(String eventId) {
        EventSystem es = new EventSystem(listOfEvents, listOfRooms);
        for (Event e : es.getAllEvents()) {
            if (e.getId().equals(eventId) || eventId.equals("0")) {
                return true;
            }
        }
        return false;
    }
}
