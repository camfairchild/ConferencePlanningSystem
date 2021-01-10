package group0110.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import group0110.entities.Role;
import group0110.eventSystem.EventSystem;
import group0110.login.CreateAccount;
import group0110.ui.criteria.AnswerCriteria;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.ui.criteria.NewPasswordAnswerCriteria;
import group0110.ui.criteria.NewUsernameAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu for creating an account.
 */

public class AccountCreationMenu extends Menu {


    Map<Integer, Role> numToRole = new HashMap<>();
    /**
     * Creates a menu for account creation.
     *
     * @param um a UserManager.
     * @param mm a MessageManager.
     * @param es a EventSystem.
     */
    public AccountCreationMenu(UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.numToRole.put(1, Role.ATTENDEE);
        this.numToRole.put(2, Role.SPEAKER);
        this.numToRole.put(3, Role.ORGANIZER);
        this.numToRole.put(4, Role.VIP);
    }

    /**
     * Displays menu for the user to enter username, role, and password. Displays whether the account
     * creation was successful or not.
     */
    public void createAccount() {
        Scanner scanner = new Scanner(System.in);

        AnswerCriteria criteria = new IntegerAnswerCriteria(1, 4);
        String userType = prompt(scanner, "Create an ATTENDEE (1), SPEAKER (2), ORGANIZER (3) or VIP (4)?",
                criteria);
        createAccountWithRole(scanner, this.numToRole.get(Integer.parseInt(userType)));
    }

    /**
     * Displays menu for user to create account for speaker. Only accessible to Organizers.
     * @param role String representing the role of the new account
     * @param scanner Scanner object to read input
     */
    public void createAccountWithRole(Scanner scanner, Role role) {
        NewUsernameAnswerCriteria newUsernameAnswerCriteria = new NewUsernameAnswerCriteria(um);
        String username = prompt(scanner, "Enter a username: ", newUsernameAnswerCriteria,
                "Sorry! Username is taken. Try again:");

        NewPasswordAnswerCriteria newPasswordAnswerCriteria = new NewPasswordAnswerCriteria();
        String password = prompt(scanner, "Enter a password: ", newPasswordAnswerCriteria,
                "Password must contain at least one capital letter, symbol, and number. Try again:");

        CreateAccount createAccount = new CreateAccount(username, password, role, um);
        createAccount.CreateNewAccount();
        System.out.println("Account Created!");
    }
}
