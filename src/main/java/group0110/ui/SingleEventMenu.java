package group0110.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import group0110.entities.Role;
import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.exceptions.RoomIsFull;
import group0110.eventSystem.exceptions.UserNotAvailable;
import group0110.eventSystem.exceptions.UserTypeNotAllowedInEvent;
import group0110.ui.criteria.AnswerCriteria;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.ui.criteria.RegexAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu for displaying options for a user when an event is selected in "View
 * List of Events" from the main user menu.
 */

public class SingleEventMenu extends Menu {
    private User user;

    /**
     * Creates a new menu with prompts for actions on a single event.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */

    public SingleEventMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Takes an event ID and directs a display to be shown based on whether the user
     * is enrolled in the event or not.
     *
     * @param event a String of sequence of letters and numbers that comprise an
     *              event ID.
     */
    public void UserEventPrompts(Event event) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(event);
            String input = prompt(scanner, this.getUserPromptString(event), this.getUserCriteria());
            int inputNum = Integer.parseInt(input);
            if (inputNum == 0) {
                return;
            } else if (inputNum == 1 && !event.getUsers().hasUser(this.user)) {
                this.enrolEventMenu(event, scanner);
            } else if (inputNum == 1 && event.getUsers().hasUser(this.user)) {
                this.unenrolEventMenu(event, scanner);
            } else if (inputNum == 2) {
                this.enrolUserToEventMenu(event, scanner);
            } else if (inputNum == 3) {
                this.unenrolUserFromEvent(event, scanner);
            } else if (inputNum == 4) {
                this.deleteEventMenu(event, scanner);
                return;
            }
        }
    }

    /**
     * Get the answer criteria for the current user. This limits their inputs.
     *
     * @return Returns the answer criteria for the user.
     */
    private AnswerCriteria getUserCriteria() {
        if (this.user.getRole() == Role.ORGANIZER) {
            return new IntegerAnswerCriteria(0, 4);
        }
        return new IntegerAnswerCriteria(0, 1);
    }

    /**
     * Get the menu string for the current user based on their role.
     *
     * @return Returns the menu string.
     */
    private String getUserPromptString(Event event) {
        String promptText = "0. Go back\n";
        if (event.getUsers().hasUser(this.user)) {
            promptText += "1. Unenrol from event\n";
        } else {
            promptText += "1. Enrol event\n";
        }
        if (user.getRole() == Role.ORGANIZER) {
            promptText += "2. Enrol user to event\n";
            promptText += "3. Unenrol user from event\n";
            promptText += "4. Delete event\n";
        }
        return promptText;

    }

    /**
     * Takes an event ID and asks if the user wants to cancel the event only if they
     * are an organizer. The user is then prompted to press 0 to return to the list
     * of events.
     *
     * @param event a String of sequence of letters and numbers that comprise an
     *              event ID.
     */
    private void unenrolEventMenu(Event event, Scanner scanner) {
        System.out.println("Do you want to unenroll from this Event? Y/N");
        String cancelChoice = scanner.next();
        if (cancelChoice.toLowerCase().equals("y")) {
            es.cancelUserRegistration(user, event.getId());
            System.out.println("You have been successfully unenrolled.");
        } else {
            System.out.println("Action cancelled.");
        }
    }

    /**
     * Takes in an event ID and displays a menu which prompts the user to indicate
     * whether they want to enroll in the event selected, and displays if process is
     * successful. If the user is an organizer, asks if user wants to cancel the
     * event. The user is prompted to press 0 to return to the list of events after.
     *
     * @param event a String of sequence of letters and numbers that comprise an
     *              event ID.
     */
    private void enrolEventMenu(Event event, Scanner scanner) {
        RegexAnswerCriteria enrollChoiceCriteria = new RegexAnswerCriteria("^(Y|N){1}$", true);
        String enrollChoice = prompt(scanner, "Do you want to enroll in this Event? Y/N", enrollChoiceCriteria);
        if (enrollChoice.toLowerCase().equals("y")) {
            if (!event.canJoin(this.user)) {
                System.out.println("You cannot enrol in this event.");
            }
            try {
                es.signUserUp(this.user, event.getId());
                System.out.println("Enrollment Successful!");
            } catch (UserNotAvailable e) {
                System.out.println("This event time conflicts with another event you are attending.");
            } catch (RoomIsFull e) {
                System.out.println("The room of the event is full and cannot accept any more attendees.");
            } catch (UserTypeNotAllowedInEvent e) {
                System.out.println(e.getMessage());
			}
        }
    }

    /**
     * A menu that is only available to organizers where they can enrol a user who
     * is both available and can join the event.
     *
     * @param event   The event to enrol users to.
     * @param scanner The input scanner.
     */
    private void enrolUserToEventMenu(Event event, Scanner scanner) {
        List<User> availableUsers = new ArrayList<>();
        for (User user : um.getUserList()) {
            if (es.isUserAvailable(user, event.getDateTimeInterval()) && event.canJoin(user)) {
                availableUsers.add(user);
            }
        }
        int choice;
        IntegerAnswerCriteria criteria = new IntegerAnswerCriteria(0, availableUsers.size());
        for (int i = 0; i < availableUsers.size(); i++) {
            User cur = availableUsers.get(i);
            System.out.printf("%s. (%s) %s%n", i + 1, cur.getRole().toString(), cur.getUsername());
        }
        choice = Integer.parseInt(prompt(scanner, "Which user would you like to enrol? Enter 0 to go back.", criteria));
        if (choice == 0) {
            System.out.println("Action cancelled");
            return;
        }
        try {
			es.signUserUp(availableUsers.get(choice - 1), event.getId());
            System.out.println("User enrolled to event.");
		} catch (UserNotAvailable | RoomIsFull | UserTypeNotAllowedInEvent e) {
            System.out.println(e.getMessage());
		}
    }

    /**
     * A menu that is only available to organizers where they can unenrol a user who
     * is already attending the event.
     *
     * @param event   The event to unenrol users from.
     * @param scanner The input scanner.
     */
    private void unenrolUserFromEvent(Event event, Scanner scanner) {
        List<User> eventUsers = event.getUsers().getAllUsers();
        int choice;
        IntegerAnswerCriteria criteria = new IntegerAnswerCriteria(0, eventUsers.size());
        for (int i = 0; i < eventUsers.size(); i++) {
            User cur = eventUsers.get(i);
            System.out.printf("%s. (%s) %s%n", i + 1, cur.getRole().toString(), cur.getUsername());
        }
        choice = Integer
                .parseInt(prompt(scanner, "Which user would you like to unenrol? Enter 0 to go back.", criteria));
        if (choice == 0) {
            System.out.println("Action cancelled.");
            return;
        }
        es.cancelUserRegistration(eventUsers.get(choice - 1), event.getId());

        System.out.println("User unenrolled from event.");
    }

    /**
     * Takes in an event ID and displays a menu which prompts the user to indicate
     * whether they want to cancel the event. Only accessible by Organizer users.
     *
     * @param event a String of sequence of letters and numbers that comprise an
     *              event ID.
     */

    private void deleteEventMenu(Event event, Scanner scanner) {
        RegexAnswerCriteria deleteChoiceCriteria = new RegexAnswerCriteria("^(Y|N){1}$", true);
        String deleteChoice = prompt(scanner, "Do you want to delete this Event? Y/N", deleteChoiceCriteria);
        if (deleteChoice.toLowerCase().equals("y")) {
            um.removeEventFromUser(user.getUsername(), event.getId());
            es.removeEvent(event.getId());
            System.out.println("Event deleted successfully.");
        }
        System.out.println("Action cancelled.");
    }
}
