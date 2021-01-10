package group0110.ui;

import java.util.Scanner;

import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.RegexAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu for displaying the events that the current user will be speaking in.
 * Only accessible to speakers.
 */
public class SpeakerEventsMenu extends Menu {

    private User user;

    /**
     * Creates a new menu with information about the events the user will be
     * speaking in.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager.
     * @param mm   a MessageManager.
     * @param es   a EventSystem.
     */
    public SpeakerEventsMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays each event in the user's list of speaking events and information
     * related to it, including the title, event ID, room ID, current capacity,
     * start time, end time, and availability.
     */
    public void showSpeakingEvents() {
        Scanner sc = new Scanner(System.in);
        for (Event event : es.getUserEvents(user)) {
            System.out.println(event);
        }

        RegexAnswerCriteria choiceCriteria = new RegexAnswerCriteria("");
        prompt(sc, "Type anything to go back to the main menu.", choiceCriteria);
    }
}
