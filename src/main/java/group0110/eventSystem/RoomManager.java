package group0110.eventSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group0110.eventSystem.exceptions.RoomNotFound;

/**
 * Stores the {@link Room} objects.
 *
 * @author Ha Young Kim
 */
class RoomManager {

    /**
     * Stores all room objects in the system. Stored as map of key: id value: room
     * object.
     */
    private final Map<String, Room> roomIdToRoom;

    public RoomManager() {
        this.roomIdToRoom = new HashMap<>();
    }

    /**
     * Create a room manager and add the rooms from {@code roomList}
     *
     * @param roomList the list of rooms to manage
     */
    public RoomManager(List<Room> roomList) {
        this.roomIdToRoom = new HashMap<>();
        for (Room r : roomList) {
            this.roomIdToRoom.put(r.getId(), r);
        }
    }

    /**
     * Determines if an room with {@code roomId} exists.
     *
     * @param roomId a String representing the ID for the room to check
     *
     * @return Returns {@code true} if the room exists and otherwise returns
     *         {@code false}
     */
    public boolean hasRoom(String roomId) {
        return this.roomIdToRoom.containsKey(roomId);
    }

    /**
     * Get a list of all of the rooms. *
     *
     * @return The list of all the rooms.
     */
    public List<Room> getAllRooms() {
        return new ArrayList<>(roomIdToRoom.values());
    }

    /**
     * Retrieve the room with {@code id}.
     *
     * @param roomId room id.
     * @return The room with {@code id}.
     * @throws RoomNotFound If room with {@code id} is not found.
     */
    public Room getRoomById(String roomId) {
        Room r = roomIdToRoom.get(roomId);
        if (r == null) {
            throw new RoomNotFound(String.format("The room with %s does not exist.", roomId));
        }
        return r;
    }

    /**
     * Add a room to the manager.
     *
     * @param room The room to add to the manager.
     * @see TimeInterval
     * @see Room
     */
    public void addRoom(Room room) {
        this.roomIdToRoom.put(room.getId(), room);
    }

    /**
     * Removes a room from the manager. The room must exist in the manager.
     *
     * @param roomId The id of the room to be removed.
     */
    public void removeRoom(String roomId) {
        this.roomIdToRoom.remove(roomId);
    }
}
