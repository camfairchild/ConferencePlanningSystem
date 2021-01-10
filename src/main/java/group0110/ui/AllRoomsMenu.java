package group0110.ui;

import java.util.List;
import java.util.Scanner;

import group0110.entities.User;
import group0110.eventSystem.DateTimeInterval;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.Room;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu for displaying rooms and information about rooms.
 */

public class AllRoomsMenu extends Menu {

    private User user;

    /**
     * Creates a menu with a list of rooms that have been created.
     *
     * @param user a User object containing the current logged-in user's
     *             information.
     * @param um   a UserManager
     * @param mm   a MessageManager
     * @param es   an EventSystem
     */

    public AllRoomsMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays all rooms and prompts user to select a room or go to log in menu.
     */

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        List<Room> allRooms = es.getAllRooms();
        displayAllRooms(allRooms);
        System.out.println("Enter any key to go back to the main menu.");
        scanner.nextLine();
    }

    /**
     * Displays all rooms that have been created.
     */

    private void displayAllRooms(List<Room> rooms) {
        int i = 0;
        for (Room room : rooms) {
            i++;
            System.out.printf("%s. %s%n", i, room.getId());
            System.out.printf("Available from %s to %s.%n", room.getAvailableTimeInterval().getStartTime().toString(),
                    room.getAvailableTimeInterval().getEndTime().toString());
            System.out.printf("Maximum Capacity: %s%n", room.getMaxCapacity());
            System.out.println("Booked times: ");
            for (DateTimeInterval t : room.getBookedTimeIntervals()) {
                System.out.printf("%s: %s to %s%n", t.getDate(), t.getStartTime().toString(), t.getEndTime().toString());
            }
            System.out.println("--------------------------");
        }
    }
}
