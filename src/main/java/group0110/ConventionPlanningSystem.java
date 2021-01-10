package group0110;
import java.util.List;

import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.Room;
import group0110.gateways.GatewaySystem;
import group0110.ui.MainMenu;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

public class ConventionPlanningSystem implements Runnable {
    public List<User> userList;
    public List<Event> eventList;
    public List<Room> roomList;
    private GatewaySystem gatewaySystem;

    public void run() {
        // Initialize GatewaySystem and load-in the userList
        // Specifies save directory for each
        gatewaySystem = new GatewaySystem("users.ser", "events.ser", "rooms.ser");
        gatewaySystem.init();
        userList = gatewaySystem.getUserList();
        eventList = gatewaySystem.getEventList();
        roomList = gatewaySystem.getRoomList();

        UserManager um = new UserManager(userList);
        MessageManager mm = new MessageManager(um);
        EventSystem es = new EventSystem(eventList, roomList);

        MainMenu mainMenu = new MainMenu(um, mm, es);
        mainMenu.mainMenu();

        gatewaySystem.setUserList(um.getUserList());
        gatewaySystem.setRoomList(es.getAllRooms());
        gatewaySystem.setEventList(es.getAllEvents());

        // call save() on exit of program
        gatewaySystem.save();
    }
}
