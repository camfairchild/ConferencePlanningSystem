package group0110.ui;

import group0110.entities.User;
import group0110.entities.UserRequest;
import group0110.entities.UserRequestCategory;
import group0110.eventSystem.EventSystem;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.List;
import java.util.Scanner;

public class AllRequestsMenu extends Menu {
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

    public AllRequestsMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Prompts the user to choose the format they would like to see the requests in
     *
     */

    public void AllRequestsSelectionMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. All Requests\n" + "2. All Pending\n" + "3. Sort by Category\n" + "4. Return to Main Menu");
        int choice = scanner.nextInt();
        if (choice == 1){
            displayAllRequests();
        }
        else if (choice == 2){
            PendingUserRequestsMenu pendingUserRequestsScreen = new PendingUserRequestsMenu(this.user, um, mm, es);
            pendingUserRequestsScreen.PendingRequestsSelectionMenu();
        }else if (choice == 3){
            UserRequestCategoryMenu userRequestCategoryScreen = new UserRequestCategoryMenu(this.user, um, mm, es);
            userRequestCategoryScreen.RequestsTypeSelectionMenu();
        }else if (choice == 4){
            LogInMenu logInScreen = new LogInMenu(um, mm, es);
            logInScreen.logInMenu(user);
        }else {
            System.out.println("Please use a valid input");
        }

    }

    /**
     * Returns all of the user requests
     *
     */
    private void displayAllRequests() {
        List<User> allUsers = um.getUserList();
        for (User user : allUsers) {
            List<UserRequest>allUserRequests = um.getUserRequestList(user);

            for (UserRequest uR : allUserRequests) {

                System.out.printf("%s:", user.getUsername());
                System.out.printf("%s ----> %s%n", uR.getRequest(), uR.getStatus());

                System.out.println("--------------------------");
            }
        }
    }
}

