package group0110.ui;

import java.util.Scanner;

import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu that displays when the program is run.
 */

public class MainMenu extends Menu {

    /**
     * Creates a new main menu when the user runs the program.
     *
     * @param um a UserManager.
     * @param mm a MessageManager.
     * @param es a EventSystem.
     */

    public MainMenu(UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
    }

    /**
     * Displays main menu for user. User can log in or sign up.
     */
    public void mainMenu() {
        int choice = this.getInput();
        while (choice != 0) {
            if (choice == 1) {
                LogInMenu logInScreen = new LogInMenu(um, mm, es);
                logInScreen.logIn();
            }
            if (choice == 2) {
                AccountCreationMenu accountCreationMenu = new AccountCreationMenu(um, mm, es);
                accountCreationMenu.createAccount();
            }
            choice = this.getInput();
        }
        System.out.println("The program will now quit. Thank you for using ConventionPlanningSystem.");

    }

    private int getInput() {
        Scanner scanner = new Scanner(System.in);
        IntegerAnswerCriteria optionCriteria = new IntegerAnswerCriteria(0, 2);
        String option = prompt(scanner,
                "Hello! Please choose an option:\n" + "0. Quit Program\n" + "1. Log In\n" + "2. Sign Up\n",
                optionCriteria);
        return Integer.parseInt(option);

    }
}
