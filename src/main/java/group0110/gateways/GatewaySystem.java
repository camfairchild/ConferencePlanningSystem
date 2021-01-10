package group0110.gateways;

import java.util.List;

import group0110.entities.User;
import group0110.eventSystem.Event;
import group0110.eventSystem.Room;

/**
 * The GatewaySystem class. This handles the Object persistence/storage of the
 * program.
 *
 * @author fairchi5
 */
public class GatewaySystem {
    private final UserGateway userGateway;
    private final EventGateway eventGateway;
    private final RoomGateway roomGateway;
    private List<User> userList;
    private List<Event> eventList;
    private List<Room> roomList;

    /**
     * Creates a new GatewaySystem. Must call init() before attempting to get any
     * stored Objects.
     *
     * @param userFilePath  a String of the file path of the file to store the
     *                      userList
     * @param eventFilePath a String of the file path of the file to store the
     *                      eventList
     * @param roomFilePath a String of the file path of the file to store the
     *                     roomList
     */
    public GatewaySystem(String userFilePath, String eventFilePath, String roomFilePath) {
        userGateway = new UserGateway(userFilePath);
        eventGateway = new EventGateway(eventFilePath);
        roomGateway = new RoomGateway(roomFilePath);
    }

    /**
     * Initializes the GatewaySystem. Must call this before attempting to get any
     * stored Objects.
     */
    public void init() {
        userList = userGateway.read();
        eventList = eventGateway.read();
        roomList = roomGateway.read();
    }

    /**
     * Saves the information stored in the GatewaySystem.
     *
     * @return boolean indicating if the Objects were saved
     */
    public boolean save() {
        return userGateway.save(userList) && eventGateway.save(eventList) && roomGateway.save(roomList);
    }

    /**
     * Gets the userList stored by the Gateway. Must call init() before attempting
     * to get this userList.
     *
     * @return the stored userList
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Gets the eventList stored by the Gateway. Must call init() before attempting
     * to get this eventList.
     *
     * @return the stored eventList
     */
    public List<Event> getEventList() {
        return eventList;
    }

    /**
     * Gets the eventList stored by the Gateway. Must call init() before attempting
     * to get this eventList.
     *
     * @return the stored eventList
     */
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setUserList(List<User> updatedList) {
        this.userList = updatedList;
    }

    public void setEventList(List<Event> updatedList) {
        this.eventList = updatedList;
    }

    public void setRoomList(List<Room> updatedList) {
        this.roomList = updatedList;
    }
}
