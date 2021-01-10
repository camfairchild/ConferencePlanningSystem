package group0110.ui;


import group0110.entities.User;
import group0110.entities.UserRequestCategory;
import group0110.eventSystem.EventSystem;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.Scanner;

public class UserRequestCategoryMenu extends Menu {
    /**
     * A menu for displaying all User Requests and their status
     */

    private User user;

    /**
     * Creates a menu with a list of all of the user requests
     *
     * @param user a User
     * @param um   a UserManager
     * @param mm   a MessageManager
     * @param es   an EventSystem
     *
     */
    public UserRequestCategoryMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }
    /**
     * Prompts user to choose from a list of categories in order to see the requests within that category
     *
     */
    public void RequestsTypeSelectionMenu() {
        Scanner scanner = new Scanner(System.in);

        int i = 0;
        for (UserRequestCategory uC : UserRequestCategory.values()) {
            i++;
            System.out.printf("%s. %s%n", i, uC.toString());
        }
        int choice = scanner.nextInt();
        System.out.printf("%s. %s%n", i + 1, "Return Main Menu");

        if (choice == 1){
            CategoryUserRequestsMenu categoryUserRequestsScreen = new
                    CategoryUserRequestsMenu(UserRequestCategory.DIET, this.user, um, mm, es);
                            categoryUserRequestsScreen.categoryRequestsSelectionMenu();
        } else if (choice == 2){
            CategoryUserRequestsMenu categoryUserRequestsScreen = new
                    CategoryUserRequestsMenu(UserRequestCategory.ACCESSIBILITY, this.user, um, mm, es);
            categoryUserRequestsScreen.categoryRequestsSelectionMenu();
        } else if (choice == 3){
            CategoryUserRequestsMenu categoryUserRequestsScreen = new
                    CategoryUserRequestsMenu(UserRequestCategory.SAFETY, this.user, um, mm, es);
            categoryUserRequestsScreen.categoryRequestsSelectionMenu();
        } else if (choice == 4){
            CategoryUserRequestsMenu categoryUserRequestsScreen = new
                    CategoryUserRequestsMenu(UserRequestCategory.OTHER, this.user, um, mm, es);
            categoryUserRequestsScreen.categoryRequestsSelectionMenu();
        } else if (choice == 5){
            LogInMenu logInScreen = new LogInMenu(um, mm, es);
            logInScreen.logInMenu(user);
        } else {
            System.out.println("Please enter one of the choices above");
        }

    }

}
