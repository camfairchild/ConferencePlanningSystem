package group0110.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import group0110.entities.Role;
import group0110.entities.User;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.ExistingPasswordAnswerCriteria;
import group0110.ui.criteria.ExistingUsernameAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu for prompting the user to sign into their account.
 */

public class LogInMenu extends Menu {

    /**
     * Creates a menu screen to login the given {@link User}.
     *
     * @param um a UserManager.
     * @param mm a MessageManager.
     * @param es a EventSystem.
     */

    public LogInMenu(UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
    }

    private boolean verifyUsername(Scanner sc, Map<String, String> userLoginInfo) {
        ExistingUsernameAnswerCriteria usernameAnswerCriteria = new ExistingUsernameAnswerCriteria(um);
        System.out.println("Enter your username or enter 0 to go back:");
        String input = sc.next();
        while (!input.equals("0")) {
            if (!usernameAnswerCriteria.check(input)) {
                System.out.println("Username does not exist. Please try again.");
            } else {
                userLoginInfo.put("USERNAME", input);
                return true;
            }
            System.out.println("Enter your username or enter 0 to go back:");
            input = sc.next();
        }
        return false;
    }

    private boolean verifyPassword(Scanner sc, Map<String, String> userLoginInfo) {
        ExistingPasswordAnswerCriteria passwordCriteria = new ExistingPasswordAnswerCriteria(
                userLoginInfo.get("USERNAME"), um);
        System.out.println("Enter your password or enter 0 to go back:");
        String input = sc.next();
        while (!input.equals("0")) {
            if (!passwordCriteria.check(input)) {
                System.out.println("Incorrect password. Please try again.");
            } else {
                userLoginInfo.put("PASSWORD", input);
                return true;
            }
            System.out.println("Enter your password or enter 0 to go back:");
            input = sc.next();
        }
        return false;
    }

    /**
     * Prompts the user to enter username and password. If password or username is
     * wrong, this menu is displayed again.
     */

    public void logIn() {
        Scanner sc = new Scanner(System.in);
        Map<String, String> userInfo = new HashMap<>();

        if (!verifyUsername(sc, userInfo) || !verifyPassword(sc, userInfo)) {
            return;
        }
        logInMenu(um.getUser(userInfo.get("USERNAME")));
    }

    /**
     * Checks the role of this user and displays the appropriate main menu.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     */

    public void logInMenu(User user) {
        if (user.getRole() == Role.ATTENDEE) {
            AttendeeMainMenu attendeeMainScreen = new AttendeeMainMenu(user, um, mm, es);
            attendeeMainScreen.attendeeMainMenu();
        } else if (user.getRole() == Role.ORGANIZER) {
            OrganizerMainMenu organizerMainScreen = new OrganizerMainMenu(user, um, mm, es);
            organizerMainScreen.organizerMainMenu();
        } else if (user.getRole() == Role.SPEAKER) {
            SpeakerMainMenu speakerMainScreen = new SpeakerMainMenu(user, um, mm, es);
            speakerMainScreen.speakerMainMenu();
        } else if (user.getRole() == Role.VIP) {
            VipMainMenu vipMainScreen = new VipMainMenu(user, um, mm, es);
            vipMainScreen.vipMainMenu();
        }
    }
}
