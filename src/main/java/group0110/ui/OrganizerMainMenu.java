package group0110.ui;

import java.util.Scanner;

import group0110.entities.User;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A user interface menu for organizers.
 */

public class OrganizerMainMenu extends Menu {

    private User user;

    /**
     * Creates a new main menu for Organizers.
     *
     * @param user a User object containing the current logged-in organizer's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */
    public OrganizerMainMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;

    }

    /**
     * Displays a main menu with user interface specific to organizers. Organizers
     * can view message inbox/outbox, view their own attending events, view all
     * events, create a new room, schedule an event, create a new speaker account,
     * view user requests, and log out.
     */
    public void organizerMainMenu() {
        int choice = this.getInput();
        while (choice != 13) {
            if (choice == 1) {
                InboxMenu inboxScreen = new InboxMenu(user, um, mm, es);
                inboxScreen.inboxSelectionMenu();
            } else if (choice == 2) {
                OutboxMenu outboxScreen = new OutboxMenu(user, um, mm, es);
                outboxScreen.outboxSelectionMenu();
            } else if (choice == 3) {
                EnrolledEventsMenu enrolledEventsScreen = new EnrolledEventsMenu(user, um, mm, es);
                enrolledEventsScreen.EnrolledEventsSelectionMenu();
            } else if (choice == 4) {
                UserSuggestionMenu userRecommendationScreen = new UserSuggestionMenu(user, um, mm, es);
                userRecommendationScreen.userSuggestionMenu();
            } else if (choice == 5) {
                AllEventsMenu allEventsScreen = new AllEventsMenu(user, um, mm, es);
                allEventsScreen.AllEventsSelectionMenu();
            } else if (choice == 6) {
                AllRoomsMenu allRoomsMenu = new AllRoomsMenu(user, um, mm, es);
                allRoomsMenu.displayMenu();
            } else if (choice == 7) {
                ScheduleEventMenu scheduleEventScreen = new ScheduleEventMenu(um, mm, es);
                scheduleEventScreen.createRoomScreen();
            } else if (choice == 8) {
                ScheduleEventMenu scheduleEventScreen = new ScheduleEventMenu(um, mm, es);
                scheduleEventScreen.scheduleEventScreen();
            } else if (choice == 9) {
                AccountCreationMenu accountCreationScreen = new AccountCreationMenu(um, mm, es);
                accountCreationScreen.createAccount();
            } else if (choice == 10) {
                UserRequestMenu userRequestScreen = new UserRequestMenu(user, um, mm, es);
                userRequestScreen.UserRequestsSelectionMenu();
            } else if (choice == 11) {
                AllRequestsMenu allRequestsScreen = new AllRequestsMenu(user, um, mm, es);
                allRequestsScreen.AllRequestsSelectionMenu();
            } else if (choice == 12) {
                ScheduleMenu scheduleMenu = new ScheduleMenu(user, um, mm, es);
                scheduleMenu.displayMenu();
            }
            choice = this.getInput();
        }
        System.out.println("You have been successfully logged out");
    }

    private int getInput() {
        Scanner scanner = new Scanner(System.in);
        IntegerAnswerCriteria choiceCriteria = new IntegerAnswerCriteria(1, 13);
        String choice_string = prompt(scanner,
                "1. View Message Inbox\n" + "2. View Message Outbox\n" + "3. View My Events\n"
                        + "4. View People You May Know\n" + "5. View List of Events\n" + "6. View List of Rooms\n"
                        + "7. Create New Room\n" + "8. Schedule Event\n" + "9. Create New Account\n"
                        + "10. View My Requests\n" + "11. View All User Requests\n" + "12. View Schedules\n" +
                        "13. Log Out\n",
                choiceCriteria);
        return Integer.parseInt(choice_string);
    }
}
