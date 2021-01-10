package group0110.ui.eventFilterMenus;

import java.time.LocalDate;
import java.util.Scanner;

import group0110.eventSystem.filters.EventFilter;
import group0110.eventSystem.filters.FilterByDates;
import group0110.ui.criteria.DateCriteria;

public class DateFilterMenu implements EventFilterMenu {

    Scanner sc;

    public DateFilterMenu(Scanner sc) {
        this.sc = sc;
    }

    private LocalDate getValidDate() {
        String input = sc.next();
        while (!new DateCriteria().check(input)) {
            System.out.println("Invalid form of date. Please try again.");
            input = sc.next();
        }

        int year = Integer.parseInt(input.substring(0, 4));
        int month = Integer.parseInt(input.substring(4, 6));
        int day = Integer.parseInt(input.substring(6));
        return LocalDate.of(year, month, day);
    }

    @Override
    public EventFilter getEventFilter() {
        System.out.println("Enter the starting date (yyyyMMdd) to search for.");
        LocalDate start = getValidDate();
        System.out.println("Enter the ending date (yyyyMMdd) to search for.");
        System.out.println("The ending date must not be before the starting date.");
        System.out.println("You can enter the same date as the starting date to search for one day.");
        LocalDate end = getValidDate();
        while (end.isBefore(start)) {
            System.out.println("The ending date MUST NOT be before the starting date.");
            System.out.println("Enter the starting date (yyyyMMdd) to search for.");
            start = getValidDate();
            System.out.println("Enter the ending date (yyyyMMdd) to search for.");
            System.out.println("The ending date must not be before the starting date.");
            System.out.println("You can enter the same date as the starting date to search for one day.");
            end = getValidDate();
        }

        return new FilterByDates(start, end);
    }
}
