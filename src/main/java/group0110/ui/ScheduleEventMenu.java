package group0110.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import group0110.entities.Role;
import group0110.entities.User;
import group0110.eventSystem.DateTimeInterval;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.EventType;
import group0110.eventSystem.Room;
import group0110.eventSystem.TimeInterval;
import group0110.eventSystem.exceptions.ExceedingRoomMaxCapacity;
import group0110.eventSystem.exceptions.InvalidIntervalTimes;
import group0110.eventSystem.exceptions.RoomIDNotFound;
import group0110.eventSystem.exceptions.RoomNotAvailable;
import group0110.ui.criteria.DateCriteria;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

/**
 * A menu for scheduling events. Only accessible to organizers.
 */
public class ScheduleEventMenu extends Menu {

    /**
     * Creates a new menu for Organizers to schedule events.
     *
     * @param um a UserManager.
     * @param mm a MessageManager.
     * @param es a EventSystem.
     */
    public ScheduleEventMenu(UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
    }

    /**
     * Takes in information about the event type, date and time interval, room ID,
     * title, and maximum capacity of the event to schedule a new event. Displays a
     * message to the user indicating whether the process has been completed.
     *
     * @param eventType        The type of event to create.
     * @param dateTimeInterval a DateTimeInterval representation of the time at
     *                         which the event will begin and end.
     * @param roomId           a String of sequence of letters and numbers that
     *                         comprise a room ID.
     * @param title            a String representation of the title of the event.
     * @param maxCapacity      an integer indicating the maximum possible capacity
     *                         of attendees at the event.
     */
    public void scheduleEvent(EventType eventType, DateTimeInterval dateTimeInterval, String roomId, String title,
            int maxCapacity) {
        try {
            es.createEvent(eventType, dateTimeInterval, roomId, title, maxCapacity);
            System.out.println("Scheduling successful!");
        } catch (RoomNotAvailable e) {
            System.out.println("Scheduling unsuccessful. Room is booked at the date time interval.");
        } catch (RoomIDNotFound e) {
            System.out.println("Scheduling unsuccessful. " + roomId + " is not an existing room.");
		} catch (ExceedingRoomMaxCapacity e) {
            System.out.println(e.getMessage());
		}
    }

    /**
     * Displays a list of rooms and prompts user to select a room for an event.
     *
     * @param dateTimeInterval a DateTimeInterval representation of the time at
     *                         which the event will begin and end.
     * @param sc               The input scanner to use.
     * @return Returns the room the user selects.
     */
    private Room getRoomInput(DateTimeInterval dateTimeInterval, Scanner sc) {
        List<Room> rooms = new ArrayList<>();
        List<Room> allRooms = es.getAllRooms();
        for (Room room : allRooms) {
            if (room.canBook(dateTimeInterval)) {
                rooms.add(room);
            }
        }
        if (rooms.isEmpty()) {
            System.out.println("There are no available rooms at the given time interval.");
            return null;
        }

        IntegerAnswerCriteria integerAnswerCriteria = new IntegerAnswerCriteria(0, rooms.size());
        int i = 1;
        for (Room room : rooms) {
            System.out.printf("%s. %s Maximum Capacity: %s%n", i, room.getId(), room.getMaxCapacity());
            i++;
        }
        String choice = prompt(sc, "Enter the number of the room you wish to schedule the event in",
                integerAnswerCriteria);

        return rooms.get(Integer.parseInt(choice) - 1);
    }

    /**
     * Prompts the user to input a valid time.
     *
     * @param dateFormat a SimpleDateFormat representation of a time.
     * @param sc         The input scanner to use.
     * @return Returns the local time representation of the user input.
     * @see LocalTime
     */
    private LocalTime getValidTime(SimpleDateFormat dateFormat, Scanner sc) {
        dateFormat.setLenient(false);
        LocalTime output;

        while (true) {
            try {
                String input = sc.next();
                Date date = dateFormat.parse(input);
                output = LocalTime.of(date.getHours(), date.getMinutes());
                break;
            } catch (ParseException e) {
                System.out.println("Please enter a valid time.");
            }
        }
        return output;
    }

    /**
     * Prompts the user to input a valid date.
     *
     * @param sc THe input scanner to use.
     *
     * @return Returns a LocalDate of the user input.
     * @see LocalDate
     */
    private LocalDate getValidDate(Scanner sc) {
        System.out.println("Enter the date (yyyyMMdd) for the event.");
        String input = sc.next();
        while (!new DateCriteria().check(input)) {
            System.out.println("Invalid form of date. Please try again.");
        }

        int year = Integer.parseInt(input.substring(0, 4));
        int month = Integer.parseInt(input.substring(4, 6));
        int day = Integer.parseInt(input.substring(6));
        return LocalDate.of(year, month, day);
    }

    /**
     * Prompts the user to input a valid time interval
     *
     * @param sc The input scanner to use.
     * @return Returns a time interval of the user's input.
     * @see TimeInterval
     */
    private TimeInterval getValidTimeInterval(Scanner sc) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        LocalTime startTime;
        LocalTime endTime;
        TimeInterval output;
        while (true) {
            try {
                System.out.println("Enter the start time.");
                System.out.println("The input should be in HH:MM 24-hour format with leading zeros.");
                startTime = this.getValidTime(timeFormat, sc);
                System.out.println("Enter the end time.");
                System.out.println("The input should be in HH:MM 24-hour format with leading zeros.");
                endTime = this.getValidTime(timeFormat, sc);
                output = new TimeInterval(startTime, endTime);
                break;
            } catch (InvalidIntervalTimes e) {
                System.out.println("Start time must be before end time. Please try again.");
            }
        }
        return output;
    }

    /**
     * A user interface for getting a valid maximum capacity that is an integer
     * greater than 0. The method does not return until the user has given a valid
     * input.
     *
     * @return A valid maximum capacity that is greater than 0.
     */
    private int getValidMaximumCapacity(Scanner sc) {
        System.out.println("Enter the maximum capacity.");
        int maxCapacity = 0;
        while (maxCapacity <= 0) {
            System.out.println("It should be greater than 0.");
            while (true) {
                try {
                    maxCapacity = Integer.parseInt(sc.next());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            }
        }
        return maxCapacity;
    }

    /**
     * Prompts the user for a valid event type and returns it.
     */
    private EventType getEventType(Scanner sc) {
        IntegerAnswerCriteria criteria = new IntegerAnswerCriteria(1, 4);
        int choice;
        EventType output;
        while (true) {
            choice = Integer.parseInt(prompt(sc,
                    "Choose one of the following event types: \n 1. Talk\n 2. Discussion\n 3. Party\n 4. VIP\n",
                    criteria));
            switch (choice) {
                case 1:
                    output = EventType.TALK;
                    break;
                case 2:
                    output = EventType.DISCUSSION;
                    break;
                case 3:
                    output = EventType.PARTY;
                    break;
                case 4:
                    output = EventType.VIP;
                    break;
                default:
                    output = EventType.PARTY;
            }
            return output;
        }
    }

    /**
     * A screen that prompts the user to enter information about the name of the
     * speaker, start hour, start minute, end hour, end minute, room ID, title, and
     * maximum capacity of the event. The user can choose to schedule another event
     * or return to the main menu after.
     */
    public void scheduleEventScreen() {
        IntegerAnswerCriteria integerAnswerCriteria = new IntegerAnswerCriteria(1, 2);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the event title:");
        String title = sc.next();
        EventType eventType = this.getEventType(sc);
        LocalDate date = this.getValidDate(sc);
        TimeInterval timeInterval = this.getValidTimeInterval(sc);
        DateTimeInterval dateTimeInterval;
        try {
            dateTimeInterval = new DateTimeInterval(date, timeInterval.getStartTime(), timeInterval.getEndTime());
        } catch (Exception e) {
            System.out.println("The date time is invalid.");
            return;
        }

        Room room = this.getRoomInput(dateTimeInterval, sc);
        if (room == null) {
            System.out.println("No rooms are available. Please create a room.");
            return;
        }
        int maxCapacity = this.getValidMaximumCapacity(sc);
        if (maxCapacity > room.getMaxCapacity()) {
            System.out.println("Maximum capacity exceeds the room capacity limit.");
            return;
        }
        System.out.println("Please verify the information: ");
        System.out.printf("Title: %s%n", title);
        System.out.printf("Event type: %s%n", eventType.toString());
        System.out.printf("Date: %s%n", dateTimeInterval.getDate().toString());
        System.out.printf("Start time: %s%n", dateTimeInterval.getStartTime().toString());
        System.out.printf("End time: %s%n", dateTimeInterval.getEndTime().toString());
        System.out.printf("Room: %s%n", room.getId());
        System.out.printf("Max Capacity: %s%n", maxCapacity);
        String createEventChoice = prompt(sc, "1. Create event\n" + "2. Cancel", integerAnswerCriteria);
        if (createEventChoice.equals("1")) {
            this.scheduleEvent(eventType, dateTimeInterval, room.getId(), title, maxCapacity);
        }
    }

    /**
     * A screen for creating a room that prompts the user to enter the opening hour,
     * opening minute, closing hour, and closing minute of the room, then creates
     * the new room. The user can choose to create another room or return to the
     * main menu after.
     */
    public void createRoomScreen() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a unique roomID.");
            String roomId = sc.next();
            TimeInterval timeInterval = this.getValidTimeInterval(sc);
            int maxCapacity = this.getValidMaximumCapacity(sc);

            boolean result = es.createRoom(roomId, timeInterval, maxCapacity);
            if (result) {
                System.out.println("Successfully created a room.");
            } else {
                System.out.println("ID is taken. Please try again.");
            }
            System.out.println("1. Create Another Room");
            System.out.println("2. Return to Main Menu");
            int choice = sc.nextInt();
            if (choice == 2) {
                return;
            }
        }
    }
}
