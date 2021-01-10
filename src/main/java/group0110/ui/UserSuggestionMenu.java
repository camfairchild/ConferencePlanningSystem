package group0110.ui;

import group0110.entities.User;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.Scanner;

public class UserSuggestionMenu extends Menu {

    private User user;

    /**
     * Creates a new menu with the given {@link UserManager}, {@link MessageManager}, and
     * {@link EventSystem} information.
     *
     * @param um a UserManager.
     * @param mm a MessageManager.
     * @param es a EventSystem.
     */
    UserSuggestionMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays all of the people this user may know or a string altering them that there are none
     */
    public void displayRecommendations() {
        int i = 0;

        if (es.getUserRecommendations(user).isEmpty()){
            System.out.println("You have no user suggestions.");
            return;
        }

        System.out.println("People You May Know:");
        for (User friend : es.getUserRecommendations(user)) {
            // turns list of common events into a string and removes the brackets
            String commonEvents = es.getCommonEvents(user, friend).toString();
            commonEvents = commonEvents.substring(1, commonEvents.length() - 1);

            i++;
            System.out.println(i + ". " + friend.getUsername() + " from " + commonEvents);
        }
    }

    /**
     * Displays the recommendations for this user and allows them to return to the main menu
     */
    public void userSuggestionMenu() {
        Scanner scanner = new Scanner(System.in);
        displayRecommendations();

        IntegerAnswerCriteria integerAnswerCriteria = new IntegerAnswerCriteria(0, 0);
        prompt(scanner, "Type '0' to go back to the Main Menu.", integerAnswerCriteria);
    }
}
