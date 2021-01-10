package group0110.ui;

import group0110.entities.User;
import group0110.entities.UserRequest;
import group0110.entities.UserRequestCategory;
import group0110.eventSystem.EventSystem;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryUserRequestsMenu extends Menu{
    /**
     * A menu for displaying all User Requests and their status within a category
     */

    private User user;
    private ArrayList<UserRequest> categoryRequests;
    private UserRequestCategory uC;

    /**
     * @param uC The chosen category
     * @param user The current User
     * @param um The UserManager
     * @param mm The MessageManager
     * @param es The EventSystem
     */
    public CategoryUserRequestsMenu(UserRequestCategory uC, User user, UserManager um, MessageManager mm,
                                    EventSystem es) {
        super(um, mm, es);
        this.user = user;
        this.categoryRequests = new ArrayList<>();
        this.uC = uC;
    }

    /**
     * Prompts the user to update status, category, and create new categories
     */
    public void categoryRequestsSelectionMenu() {
        Scanner scanner = new Scanner(System.in);
        displayCategoryRequests();

        System.out.println("1. Update Status\n" + "2. Update Category\n" + "3. Return to Main Menu");

        int choice = scanner.nextInt();
        if (choice == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Choose number of the request you would like to update");
            int ch = sc.nextInt();
            if (ch > 0 && ch < this.categoryRequests.size()) {
                this.categoryRequests.get(ch - 1).updateStatus();
            }

        } else if (choice == 2){

            Scanner sca = new Scanner(System.in);
            System.out.println("Choose number of the request you would like to update");
            int ch = sca.nextInt();

            Scanner scan = new Scanner(System.in);
            System.out.println("Choose the category you would like to change it to:");

            int i = 0;
            for (UserRequestCategory uC: UserRequestCategory.values()){
                i++;
                System.out.printf("%s. %s%n", i, uC.toString());
            }

            int ch2 = scan.nextInt();

            if ( 0 < ch2 && ch2 < UserRequestCategory.values().length) {
                this.categoryRequests.get(ch2 - 1).updateCategory(this.uC);
            } else{
                System.out.println("Please choose out of the given categories");
            }

        } else if (choice == 3) {
            LogInMenu logInScreen = new LogInMenu(um, mm, es);
            logInScreen.logInMenu(user);
        } else {
            System.out.println("Please choose out of the given categories");
        }

    }

    private void displayCategoryRequests() {
        List<User> allUsers = um.getUserList();
        for (User user : allUsers) {
            List<UserRequest>allUserRequests = um.getUserRequestList(user);
            int i = 0;
            for (UserRequest uR : allUserRequests) {
                if (uR.getCategory().equals(uC)) {
                    i++;
                    System.out.printf("%s. %s%n", i, user.getUsername());
                    System.out.printf("%s: /t %s%n", uR.getRequest(), uR.getStatus());
                    System.out.print("--------------------");
                    this.categoryRequests.add(uR);
                }
            }
        }
    }
}
