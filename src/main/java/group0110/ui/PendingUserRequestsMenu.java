package group0110.ui;

import group0110.entities.User;
import group0110.entities.UserRequest;
import group0110.entities.UserRequestStatus;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PendingUserRequestsMenu extends Menu {
    /**
     * A menu for displaying all User Requests and their status
     */

    private User user;
    private ArrayList<UserRequest> pendingUserRequests;
    /**
     * Creates a menu with a list of all of the user requests
     *
     * @param user a User
     * @param um   a UserManager
     * @param mm   a MessageManager
     * @param es   an EventSystem
     *
     */

    public PendingUserRequestsMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
        this.pendingUserRequests = new ArrayList<>();
    }

    /**
     * Displays the pending requests and provides the user with the option to update the status of
     *
     */
    public void PendingRequestsSelectionMenu() {
        Scanner scanner = new Scanner(System.in);
        displayPendingRequests();
        System.out.println("Enter 0 to go back to the main menu.");
        scanner.next();

        IntegerAnswerCriteria choiceCriteria = new IntegerAnswerCriteria(1, this.pendingUserRequests.size());
        String choice_string = prompt(scanner,
                "Choose the number corresponding to the user status you would like to update",
                choiceCriteria);
        int choice = Integer.parseInt(choice_string);
        if (choice == 0){
            LogInMenu logInScreen = new LogInMenu(um, mm, es);
            logInScreen.logInMenu(user);
        } else {
            this.pendingUserRequests.get(choice - 1).updateStatus();
            System.out.println("You have successfully updated the status of ," + this.pendingUserRequests.get(choice - 1) +
                    " to Addressed");
            this.pendingUserRequests.remove(choice - 1);

        }


    }

    /**
     * Checks the status of the user requests and presents only the ones that are pending
     */
    private void displayPendingRequests() {
        List<User> allUsers = um.getUserList();
        for (User user : allUsers) {
            List<UserRequest>allUserRequests = um.getUserRequestList(user);
            int i = 0;
            for (UserRequest uR : allUserRequests) {
                if (uR.getStatus().equals(UserRequestStatus.PENDING)) {
                    i++;
                    pendingUserRequests.add(uR);
                    System.out.printf("%s. /t %s%n", i, user.getUsername());
                    System.out.println(uR.getRequest());
                    System.out.println("--------------------------");
                }
            }
        }
    }
}

