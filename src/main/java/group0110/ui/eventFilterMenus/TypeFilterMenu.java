package group0110.ui.eventFilterMenus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import group0110.eventSystem.EventType;
import group0110.eventSystem.filters.EventFilter;
import group0110.eventSystem.filters.FilterByEventType;

public class TypeFilterMenu implements EventFilterMenu {

    Scanner sc;
    List<EventType> eventTypes = new ArrayList<>();

    public TypeFilterMenu(Scanner sc) {
        this.sc = sc;
        this.eventTypes.add(EventType.TALK);
        this.eventTypes.add(EventType.DISCUSSION);
        this.eventTypes.add(EventType.PARTY);
        this.eventTypes.add(EventType.VIP);
    }

    private List<EventType> getValidTypes() {
        List<EventType> chosenTypes = new ArrayList<>();
        boolean validInput = false;
        while (!validInput) {
            chosenTypes = new ArrayList<>();
            String input = sc.next();
            String[] choices = input.split(",");
            try {
                validInput = true;
                for (String choice : choices) {
                    int choiceNum = Integer.parseInt(choice.trim());
                    assert choiceNum < eventTypes.size();
                    if (choiceNum - 1 >= eventTypes.size()) {
                        validInput = false;
                        System.out.println("Invalid input. Please make sure your choices are numbers and separated by comma.");
                        break;
                    }
                    chosenTypes.add(eventTypes.get(choiceNum - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please make sure your choices are numbers and separated by comma.");
                validInput = false;
            }
        }
        return chosenTypes;
    }

    @Override
    public EventFilter getEventFilter() {
        System.out.println("Which which types of events to show.");
        System.out.println("You can choose multiple by adding a comma between each choice of number.");
        int i = 0;
        for (EventType t : eventTypes) {
            i++;
            System.out.printf("%s. %s%n", i, t.toString());
        }
        return new FilterByEventType(getValidTypes());
    }
}
