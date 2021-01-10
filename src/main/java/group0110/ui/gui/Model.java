package group0110.ui.gui;

import group0110.entities.Role;
import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.EventSystem;
import group0110.eventSystem.Room;
import group0110.gateways.GatewaySystem;
import group0110.login.CreateAccount;
import group0110.usecases.messaging.MessageManager;
import group0110.usecases.user.UserManager;

import java.util.List;

class Model {
    private List<User> userList;
    private List<Event> eventList;
    private List<Room> roomList;
    private GatewaySystem gatewaySystem;
    public UserManager um;
    private EventSystem es;
    public MessageManager mm;
    private User loggedInUser;

    public Model(GatewaySystem gatewaySystem) {
        this.gatewaySystem = gatewaySystem;
    }

    void init() {
        gatewaySystem.init();
        userList = gatewaySystem.getUserList();
        eventList = gatewaySystem.getEventList();
        roomList = gatewaySystem.getRoomList();

        um = new UserManager(userList);
        mm = new MessageManager(um);
        es = new EventSystem(eventList, roomList);
    }

    void createAccount(String username, String password, String role) {
        Role role_ = Role.valueOf(role);
        CreateAccount createAccount = new CreateAccount(username, password, role_, um);
        createAccount.CreateNewAccount();
    }

    void loginUser(String username) {
        User user = um.getUser(username);
        this.loggedInUser = user;
    }

    void logoutUser() {
        this.loggedInUser = null;
    }

    User getLoggedInUser() {
        return loggedInUser;
    }

    User getUser(String username) {
        return um.getUser(username);
    }

    EventSystem getEventSystem() { return es; }

    void save() {
        gatewaySystem.setUserList(um.getUserList());
        gatewaySystem.setRoomList(es.getAllRooms());
        gatewaySystem.setEventList(es.getAllEvents());

        // call save() on exit of program
        gatewaySystem.save();
    }

    /**
     *
     * @return boolean indicating if the loggedInUser can mass message
     */
    public boolean canMassMessage() {
        return loggedInUser.getRole().equals(Role.ORGANIZER) || loggedInUser.getRole().equals(Role.SPEAKER);
    }
}
