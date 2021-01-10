package group0110.ui;


import group0110.entities.User;
import group0110.entities.UserRequest;
import group0110.entities.UserRequestCategory;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.RegexAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.List;
import java.util.Scanner;

public class UserRequestMenu extends Menu {
    /**
     * A menu for displaying the requests of the user and their status
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
    public UserRequestMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Display all of user requests
     */

    public void UserRequestsSelectionMenu() {
        Scanner scanner = new Scanner(System.in);
        displayAllRequests();
        System.out.println("Enter any key to go back to the main menu.");
        scanner.next();
        LogInMenu logInScreen = new LogInMenu(um, mm, es);
        logInScreen.logInMenu(user);
    }

    /**
     * Checks if there are any user requests, if there is not the display says that, if there is it returns all of them
     *
     */

    private void displayAllRequests() {
        List<UserRequest> allUserRequests = um.getUserRequestList(this.user);
        if (allUserRequests.isEmpty()) {
            System.out.println("You currently have no requests");
        }
        int i = 0;
        for (UserRequest uR : allUserRequests) {
            i++;
            System.out.println(i + ".");
            System.out.printf("%s ----> %s%n", uR.getRequest(), uR.getStatus());
            System.out.println("--------------------------");
        }
    }

    /**
     * Prompts user to enter their request and the type of request, only if there are enoughe categories for the user
     * to choose from
     */
    public void createUserRequestScreen() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your request");
        String request = sc.next();
        Scanner sca = new Scanner(System.in);
        System.out.println("Enter the type of request:");
        int i = 0;
        for (UserRequestCategory uC : UserRequestCategory.values()) {
            i++;
            System.out.printf("%s. %s%n", i, uC.toString());
        }
        int choice = sca.nextInt();
        if (choice == 1){
            UserRequest userRequest = new UserRequest(request, UserRequestCategory.DIET);
            um.addUserRequestToUser(this.user, userRequest);
        } else if (choice == 2){
            UserRequest userRequest = new UserRequest(request, UserRequestCategory.ACCESSIBILITY);
            um.addUserRequestToUser(this.user, userRequest);
        } else if (choice == 3){
            UserRequest userRequest = new UserRequest(request, UserRequestCategory.SAFETY);
            um.addUserRequestToUser(this.user, userRequest);
        } else if (choice == 4){
            UserRequest userRequest = new UserRequest(request, UserRequestCategory.OTHER);
            um.addUserRequestToUser(this.user, userRequest);
        } else {
            System.out.println("Please enter one of the choices above");
        }
        System.out.println("You successfully entered your request!");
        System.out.println("1. Enter Another");
        System.out.println("2. Return to Main Menu");
        int choice2 = sc.nextInt();

        if (choice2 == 1) {
            createUserRequestScreen();
        } else if (choice2 == 2) {
            LogInMenu logInScreen = new LogInMenu(um, mm, es);
            logInScreen.logInMenu(user);
        } else {
            System.out.println("Please enter 1 or 2");
        }
    }
    /**
     * If there are any requests, prompt the user to input the one they want to remove, if not notify the user
     */
    public void removeUserRequestScreen() {
        Scanner sc = new Scanner(System.in);

        if (um.getUserRequestList(this.user).isEmpty()){

            System.out.println("There are no requests!");
            System.out.println();
            RegexAnswerCriteria choiceCriteria = new RegexAnswerCriteria("");
            prompt(sc, "Type anything to go back to the main menu.", choiceCriteria);
            LogInMenu logInScreen = new LogInMenu(um, mm, es);
            logInScreen.logInMenu(user);
        }else {
            displayAllRequests();
            System.out.println("Enter the number corresponding to the request you would like to remove");
            int choice = sc.nextInt();

            if (choice > 0 && choice < um.getUserRequestList(this.user).size()) {
                this.user.removeUserRequestFromUser(um.getUserRequestList(this.user).get(choice - 1));
            }

            System.out.println("You successfully removed your request!");
            System.out.println("1. Remove Another");
            System.out.println("2. Return to Main Menu");
            int choice2 = sc.nextInt();

            if (choice2 == 1) {
                removeUserRequestScreen();
            } else if (choice2 == 2) {
                LogInMenu logInScreen = new LogInMenu(um, mm, es);
                logInScreen.logInMenu(user);
            } else {
                System.out.println("Please enter 1 or 2");
            }
        }
    }
}
