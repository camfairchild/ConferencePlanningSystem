package group0110.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.filters.EventFilter;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.ui.eventFilterMenus.DateFilterMenu;
import group0110.ui.eventFilterMenus.EventFilterMenu;
import group0110.ui.eventFilterMenus.TypeFilterMenu;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu for displaying events and information about events.
 */

public class AllEventsMenu extends Menu {

    private User user;
    private List<Event> events;
    private List<EventFilter> curFilters = new ArrayList<>();

    /**
     * Creates a menu with list of events and information about each event.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */

    public AllEventsMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
        updateEventsList();
    }

    /**
     * Updates the current events list from the event system.
     */
    private void updateEventsList() {
        this.events = es.getAllEvents();
    }

    /**
     * Applies the filters in the current filter list to events.
     */
    private void applyFilters() {
        for (EventFilter ef : curFilters) {
            this.events = ef.filter(this.events);
        }
    }

    private void FilterSelectionMenu(Scanner sc) {
        clearScreen();
        System.out.println("1. Filter by start and date date.");
        System.out.println("2. Filter by event types.");
        String input = this.prompt(sc, "Select which filter to apply or press 0 to quit.",
                new IntegerAnswerCriteria(0, 2));
        EventFilterMenu filterMenu = new DateFilterMenu(sc);
        if (input.equals("0")) {
            return;
        }

        if (input.equals("1")) {
            filterMenu = new DateFilterMenu(sc);
        }

        if (input.equals("2")) {
            filterMenu = new TypeFilterMenu(sc);
        }

        curFilters.add(filterMenu.getEventFilter());
        applyFilters();
        System.out.println("Successfully applied the filter.");
        sc.nextLine();
        sc.nextLine();
    }

    private void FilterRemovalMenu(Scanner sc) {
        clearScreen();
        System.out.println("Remove which filter?");
        int i = 1;
        for (EventFilter ef : curFilters) {
            System.out.printf("%s. %s%n", i, ef);
            i++;
        }
        System.out.printf("%s. Remove all filters%n", i);
        String input = this.prompt(sc, "Select which filter to remove or press 0 to quit.",
                new IntegerAnswerCriteria(0, curFilters.size() + 1));
        if (input.equals("0")) {
            return;
        }
        if (input.equals(Integer.toString(i))) {
            updateEventsList();
            curFilters = new ArrayList<>();
            System.out.println("Removed all filters.");
        }
        else {
            curFilters.remove(Integer.parseInt(input) - 1);
            updateEventsList();
            applyFilters();
            System.out.println("Successfully removed the filter.");
        }
        sc.nextLine();
        sc.nextLine();
    }

    private void FilterActionMenu(Scanner sc) {
        while (true) {
            clearScreen();
            if (curFilters.isEmpty()) {
                System.out.println("No filters applied.");
            } else {
                System.out.println("Current Applied Filters:");
                for (EventFilter f : curFilters) {
                    System.out.println(f);
                }
            }
            System.out.println("========================================");
            System.out.println("1. Add a filter");
            System.out.println("2. Remove a filter");
            String input = prompt(sc, "Choose an action or enter 0 go back:", new IntegerAnswerCriteria(0, 2));
            if (input.equals("0")) {
                return;
            }
            if (input.equals("1")) {
                FilterSelectionMenu(sc);
            }
            if (input.equals("2")) {
                FilterRemovalMenu(sc);
            }
        }
    }

    /**
     * Prompts the user to choose an event from a list of events. Displays
     * information about the chosen event.
     */
    public void AllEventsSelectionMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            clearScreen();
            displayAllEvents(events);
            System.out.println("Type the number following the Event number. Or, type '0' to go back to the main menu.");
            System.out.println("To filter the events, type 'filter'.");
            String input = scanner.next();
            while (!input.equals("filter") && !new IntegerAnswerCriteria(0, events.size()).check(input)) {
                System.out.println("Invalid input. Try again.");
                input = scanner.next();
            }
            if (input.equals("0")) {
                return;
            }
            if (input.equals("filter")) {
                FilterActionMenu(scanner);
            } else {
                Event event = events.get(Integer.parseInt(input) - 1);
                SingleEventMenu singleEventMenu = new SingleEventMenu(user, um, mm, es);
                singleEventMenu.UserEventPrompts(event);
            }
        }
    }

    /**
     * Displays events.
     */
    private void displayAllEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("There are no events.");
            return;
        }
        int i = 0;
        for (Event event : events) {
            i++;
            System.out.printf("%s. %s (%s)%n", i, event.getTitle(), event.getEventType());
        }
    }
}
