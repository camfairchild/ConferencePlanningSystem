package group0110.ui;

import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.ui.criteria.IntegerAnswerCriteria;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * A menu that allows users to view schedules of all the events. Available to everyone
 */
public class ScheduleMenu extends Menu{
    private User user;

    /**
     * Creates a new menu to view schedules
     *
     * @param um a UserManager.
     * @param mm a MessageManager.
     * @param es a EventSystem.
     */
    ScheduleMenu(User user, UserManager um, MessageManager mm, EventSystem es) {
        super(um, mm, es);
        this.user = user;
    }

    /**
     * Displays the options, prompts the user to select the schedule they would like to see
     */
    public void displayMenu(){
        System.out.println("Here is a list of schedules you can view. Each schedule is sorted by different criteria. " +
                "You can choose which schedule you want by selecting the criteria through which you want to view the " +
                "list of events");
        Scanner sc = new Scanner(System.in);
        IntegerAnswerCriteria choice = new IntegerAnswerCriteria(1, 5);
        String choice_str = prompt(sc,"1. Day \n" + "2. Event Type \n" + "3. Time \n" +
                "4. By title alphabetically\n" + "5. Go back to main menu", choice);
        int input = Integer.parseInt(choice_str);
        createSchedule(input);
    }

    /**
     * Enters the correct feature based on choice into formatSchedule()
     * @param choice the option chosen in displayMenu()
     */
    private void createSchedule(int choice) {

        if (choice !=5){
            Map<String, List<Event>> schedule;
            if(choice == 1) {
                schedule = es.getSchedule("day");
            } else if(choice == 2){
                schedule = es.getSchedule("type");
            }else if(choice == 3) {
                schedule = es.getSchedule("time");
            }else {
                schedule = es.getSchedule("title");
            }
            formatSchedule(schedule);
        }
    }

    /**
     * formats a map for viewing
     * @param schedule the map which should be formatted
     */
    private void formatSchedule(Map<String, List<Event>> schedule) {
        Set<String> keys = schedule.keySet();
        for(String key : keys){
            System.out.println(key + ":");
            List<Event> events = schedule.get(key);
            for (Event e : events){
                System.out.println(e);
            }
        }
    }

}
