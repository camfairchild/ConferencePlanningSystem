package group0110.ui;

import java.util.Scanner;

import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.AnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A new menu for the current logged-in user.
 */

public class Menu {
    protected UserManager um;
    protected MessageManager mm;
    protected EventSystem es;

    /**
     * Creates a new menu with the given {@link UserManager}, {@link MessageManager}, and
     * {@link EventSystem} information.
     *
     * @param um a UserManager.
     * @param mm a MessageManager.
     * @param es a EventSystem.
     */
    Menu(UserManager um, MessageManager mm, EventSystem es) {
        this.um = um;
        this.mm = mm;
        this.es = es;
    }

    /**
     * Clears the console screen.
     */
    void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    String prompt(Scanner scanner, String promptText, AnswerCriteria criteria, String... optionalErrorText) {
        System.out.println(promptText);
        String answer = scanner.next();
        if (criteria.check(answer)) {
            // Answer met criteria
            return answer;
        } else {
            String errorText = "Sorry, that was not a valid answer. Please try again:";
            if (optionalErrorText.length != 0) {
                errorText = optionalErrorText[0];
            }
            System.out.println(errorText);
            return prompt(scanner, promptText, criteria, optionalErrorText);
        }
    }
}
