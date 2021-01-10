package group0110.ui;

import java.util.List;
import java.util.Scanner;

import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu that displays list of events that this user is enrolled in.
 */

public class EnrolledEventsMenu extends Menu {

    private User user;

    /**
     * Creates a new menu that displays list of events that the given {@link User}
     * is enrolled in.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */

    public EnrolledEventsMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays all events and information about the events that this user is
     * enrolled in.
     */
    public void EnrolledEventsSelectionMenu() {
        Scanner scanner = new Scanner(System.in);
        List<Event> events;
        IntegerAnswerCriteria eventCriteria = new IntegerAnswerCriteria(0, es.getAllEvents().size());
        while (true) {
            events = es.getUserEvents(this.user);
            displayAllEvents(events);
            String eventChoice = prompt(scanner,
                    "Type the number following the Event name for more information. Or, type '0' to go back to the main menu.",
                    eventCriteria);
            int eventChoiceNum = Integer.parseInt(eventChoice);
            if (eventChoiceNum == 0) {
                return;
            }
            Event event = events.get(Integer.parseInt(eventChoice) - 1);
            SingleEventMenu singleEventMenu = new SingleEventMenu(user, um, mm, es);
            singleEventMenu.UserEventPrompts(event);
        }
    }

    /**
     * Displays events.
     */
    private void displayAllEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("You are currently not enrolled in any events.");
            return;
        }
        int i = 0;
        for (Event event : events) {
            i++;
            System.out.printf("%s. %s (%s)%n", i, event.getTitle(), event.getEventType().toString());
        }
    }
}
