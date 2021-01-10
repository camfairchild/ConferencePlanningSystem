package group0110.ui;

import java.util.Scanner;

import group0110.entities.User;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A main menu screen for speaker users only.
 */
public class SpeakerMainMenu extends Menu {
    private User user;

    /**
     * Creates a new main menu for speakers.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */
    public SpeakerMainMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays a main menu with user interface specific to speakers. Speakers can
     * view message inbox/outbox, view their own speaking events, view all events,
     * and log out.
     */
    public void speakerMainMenu() {
        Scanner scanner = new Scanner(System.in);
        IntegerAnswerCriteria choiceCriteria = new IntegerAnswerCriteria(1, 7);
        String choice_string;
        int choice;
        while (true) {
            choice_string = prompt(scanner, "Please choose an option:\n" + "1. View Message Inbox\n"
                    + "2. View Message Outbox\n" + "3. View My Events\n" + "4. View List of Events\n"
                            + "5. View My Requests\n" + "6. View Schedules\n" + "7. Log Out\n",
                    choiceCriteria);
            choice = Integer.parseInt(choice_string);

            if (choice == 1) {
                InboxMenu inboxScreen = new InboxMenu(user, um, mm, es);
                inboxScreen.inboxSelectionMenu();
            } else if (choice == 2) {
                OutboxMenu outboxScreen = new OutboxMenu(user, um, mm, es);
                outboxScreen.outboxSelectionMenu();
            } else if (choice == 3) {
                SpeakerEventsMenu speakerEventsScreen = new SpeakerEventsMenu(user, um, mm, es);
                speakerEventsScreen.showSpeakingEvents();
            } else if (choice == 4) {
                AllEventsMenu allEventsScreen = new AllEventsMenu(user, um, mm, es);
                allEventsScreen.AllEventsSelectionMenu();
            } else if (choice == 5) {
                UserRequestMenu userRequestScreen = new UserRequestMenu(user, um, mm, es);
                userRequestScreen.UserRequestsSelectionMenu();
            } else if (choice == 6) {
                ScheduleMenu scheduleMenu = new ScheduleMenu(user, um, mm, es);
                scheduleMenu.displayMenu();
            } else if (choice == 7) {
                System.out.println("You have been successfully logged out");
                return;
            }

        }
    }
}
