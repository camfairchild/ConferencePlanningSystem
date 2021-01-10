package group0110.eventSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import group0110.entities.User;
import group0110.eventSystem.exceptions.ExceedingRoomMaxCapacity;
import group0110.eventSystem.exceptions.RoomIDNotFound;
import group0110.eventSystem.exceptions.RoomIsFull;
import group0110.eventSystem.exceptions.RoomNotAvailable;
import group0110.eventSystem.exceptions.UserNotAvailable;
import group0110.eventSystem.exceptions.UserTypeNotAllowedInEvent;
import group0110.eventSystem.schedules.ScheduleManager;

/**
 * The parent management system of events and rooms.
 *
 * Actions such as signing up a user for an event or scheduling/creation an
 * event is done by the system. Also provides information about events.
 *
 * Also acts as a facade that just provides the client interface for the basic
 * tasks, hiding the inner mechanisms.
 */
public class EventSystem {

    private EventManager em;
    private RoomManager rm;

    public EventSystem() {
        this.rm = new RoomManager();
        this.em = new EventManager();
    }

    /**
     * Creates a new event system with existing event and room data.
     *
     * @param eventList The list of events to add to the system.
     * @param roomList  The list of rooms to add to the system.
     */
    public EventSystem(List<Event> eventList, List<Room> roomList) {
        this.rm = new RoomManager(roomList);
        this.em = new EventManager(eventList);
    }

    /**
     * Retrieve the event with {@code id}.
     *
     * @param eventId event id.
     * @return The event with {@code id}.
     */
    public Event getEventById(String eventId) {
        return em.getEventById(eventId);
    }

    /**
     * Get all events sorted by increasing time.
     *
     * @return Returns a list of events
     */
    public List<Event> getAllEvents() {
        List<Event> allEvents = new ArrayList<>();

        for (Event event : em.getAllEvents()) {
            allEvents.add(event);
        }

        allEvents.sort(new SortEventByTime());

        return allEvents;
    }

    /**
     * Get all rooms.
     *
     * @return Returns a list of rooms
     */
    public List<Room> getAllRooms() {
        List<Room> allRooms = new ArrayList<>();

        for (Room room : rm.getAllRooms()) {
            allRooms.add(room);
        }

        return allRooms;
    }

    /**
     * Get all events that the user joined.
     *
     * @param user The id of the user.
     * @return Returns a list of events that the user joined.
     */
    public List<Event> getUserEvents(User user) {
        return em.getUserEvents(user);
    }

    /**
     * Determines if a user is available at {@code dateTimeInterval}. This means
     * that they are not attending other events at {@code dateTimeInterval}.
     *
     * @param user             The id of the user.
     * @param dateTimeInterval The time interval to check for.
     * @return Returns {@code true} if and only if the user is available at the
     *         given date and time interval
     */
    public boolean isUserAvailable(User user, DateTimeInterval dateTimeInterval) {
        return em.isUserAvailable(user, dateTimeInterval);
    }

    /**
     * Creates, schedules, and books an event given the following information.
     *
     * @param eventType        The type of event to create.
     * @param dateTimeInterval Date and time interval of when the event should
     *                         happen.
     * @param roomId           The ID of the room the event happens in.
     * @param title            The title of the event
     * @param maxCapacity      The maximum number of users that can join the event.
     *                         This value should be positive.
     *
     * @throws RoomNotAvailable         If room is not available in
     *                                  {@code dateTimeInterval};
     * @throws RoomIDNotFound           If {@code roomId} does not belong to an
     *                                  existing room.
     * @throws ExceedingRoomMaxCapacity If {@code maxCapacity} of the event exceeds
     *                                  the maximum capacity of the room.
     */
    public void createEvent(EventType eventType, DateTimeInterval dateTimeInterval, String roomId, String title,
            int maxCapacity) throws RoomNotAvailable, RoomIDNotFound, ExceedingRoomMaxCapacity {

        if (!rm.hasRoom(roomId)) {
            throw new RoomIDNotFound(String.format("%s does not exist.", roomId));
        }
        Room room = rm.getRoomById(roomId);

        if (!room.canBook(dateTimeInterval)) {
            throw new RoomNotAvailable(String.format("Room is not available from %s to %s on %s.",
                    dateTimeInterval.getStartTime(), dateTimeInterval.getEndTime(), dateTimeInterval.getDate()));
        }

        if (room.getMaxCapacity() < maxCapacity) {
            throw new ExceedingRoomMaxCapacity(
                    String.format("Event's maximum capacity cannot exceed the room's maximum capacity (%s).",
                            Integer.toString(room.getMaxCapacity())));

        }

        room.bookTime(dateTimeInterval);
        Event newEvent = EventFactory.create(eventType, dateTimeInterval, roomId, title, maxCapacity);
        em.addEvent(newEvent);
    }

    /**
     * Removes an event and un-books it from the system. The event must exist.
     *
     * @param eventId The id of the event to be removed.
     */
    public void removeEvent(String eventId) {
        Event event = em.getEventById(eventId);
        Room room = rm.getRoomById(event.getRoom());

        room.unbookTime(event.getDateTimeInterval());
        this.em.removeEvent(eventId);
    }

    /**
     * Reschedule an event to the room with {@code roomId} at a new
     * dateTimeInterval.
     *
     * @param eventId          The id of the event to reschedule.
     * @param dateTimeInterval The dateTimeInterval to reschedule to.
     * @param roomId           The id of the room to reschedule to.
     *
     * @throws ExceedingRoomMaxCapacity If {@code maxCapacity} of the event exceeds
     *                                  the maximum capacity of the room.
     * @throws RoomNotAvailable         If room is not available in
     *                                  {@code dateTimeInterval};
     */
    public void rescheduleEvent(String eventId, DateTimeInterval dateTimeInterval, String roomId)
            throws ExceedingRoomMaxCapacity, RoomNotAvailable {
        Event event = em.getEventById(eventId);
        Room room = rm.getRoomById(event.getRoom());

        if (!room.canBook(dateTimeInterval) && !event.getDateTimeInterval().contains(dateTimeInterval)) {
            throw new RoomNotAvailable(String.format("Room is not available from %s to %s on %s.",
                    dateTimeInterval.getStartTime(), dateTimeInterval.getEndTime(), dateTimeInterval.getDate()));
        }

        if (room.getMaxCapacity() < event.maxCapacity) {
            throw new ExceedingRoomMaxCapacity(
                    String.format("Event's maximum capacity cannot exceed the room's maximum capacity (%s).",
                            Integer.toString(room.getMaxCapacity())));
        }

        room.unbookTime(event.getDateTimeInterval());
        if (room.getId().equals(roomId)) {
            room.bookTime(dateTimeInterval);
        } else {
            event.setRoom(roomId);
            Room newRoom = rm.getRoomById(roomId);
            newRoom.bookTime(dateTimeInterval);
        }
        event.setDateTimeInterval(dateTimeInterval);
    }

    /**
     * Create a room that is available in {@code availableTimeInterval} with a
     * maximum capacity of {@code maxCapacity}.
     *
     * @param roomId                The id for the room. Can be the room number.
     * @param availableTimeInterval The time interval that the room is available
     *                              for.
     * @param maxCapacity           The maximum number of users the room can contain
     *                              at any given time.
     * @return Returns {@code true} if and only if the creation of the room was
     *         successful
     * @see DateTimeInterval
     */
    public boolean createRoom(String roomId, TimeInterval availableTimeInterval, int maxCapacity) {
        if (rm.hasRoom(roomId)) {
            return false;
        }
        Room newRoom = new Room(roomId, availableTimeInterval, maxCapacity);
        rm.addRoom(newRoom);
        return true;
    }

    /**
     * Allows a user to reserve a spot in an event with id eventId. This user will
     * now contribute to filling the room's capacity.
     *
     * @param user    The user to add to the event.
     * @param eventId The id of the event that the user wants to sign up for
     * @throws UserNotAvailable          If the user is not available at the time
     *                                   interval.
     * @throws RoomIsFull                If the room of the event's maximum capacity
     *                                   is reached and cannot accept the user.
     * @throws UserTypeNotAllowedInEvent If the user's role is not permitted to
     *                                   enrol in the event.
     */
    public void signUserUp(User user, String eventId) throws UserNotAvailable, RoomIsFull, UserTypeNotAllowedInEvent {
        Event event = em.getEventById(eventId);
        DateTimeInterval eventDTI = event.getDateTimeInterval();

        // user is not free at this time or they are already attending.
        if (!em.isUserAvailable(user, event.getDateTimeInterval()) || event.users.hasUser(user)) {
            throw new UserNotAvailable(String.format("%s is not available from %s to %s.", user.getUsername(),
                    eventDTI.getStartTime(), eventDTI.getEndTime()));
        }

        // exceeds room capacity
        Room room = rm.getRoomById(event.room);
        if (room.maxCapacity == event.users.getAllUserCount()) {
            throw new RoomIsFull(String.format("%s is full and user cannot join the event..", room.getId()));
        }
        event.addUser(user);
    }

    /**
     * Allows a user to cancel their reservation in an event with id eventId.
     *
     * @param user    The user to unregister from the event.
     * @param eventId the id of the event that the user wants to cancel their
     *                registration for
     */
    public void cancelUserRegistration(User user, String eventId) {
        em.getEventById(eventId).removeUser(user);
    }

    /**
     * Gets a list of other users that are enrolled in the same events that the user
     * is enrolled in.
     *
     * @param user the user
     * @return Returns a list of users, excluding the {@code user} who enrolled in
     *         the same events as {@code user}.
     */
    public List<User> getUserRecommendations(User user) {
        List<User> listOfFriends = new ArrayList<>();
        for (Event event : getUserEvents(user)) {
            for (User otherUser : event.getUsers().getAllUsers()) {
                if (!otherUser.equals(user) && !listOfFriends.contains(otherUser)) {
                    listOfFriends.add(otherUser);
                }
            }
        }
        return listOfFriends;
    }

    /**
     * Get a list of event titles that two users commonly attend.
     *
     * @param user1 The first user.
     * @param user2 The second user.
     *
     * @return Returns a list of event titles.
     */
    public List<String> getCommonEvents(User user1, User user2) {
        List<String> commonEvents = new ArrayList<>();
        for (Event event : em.getUserEvents(user1)) {
            if (em.getUserEvents(user2).contains(event) && !commonEvents.contains(event.getTitle())) {
                commonEvents.add(event.getTitle());
            }
        }
        return commonEvents;
    }

    public Map<String, List<Event>> getSchedule(String type) {
        ScheduleManager sm = new ScheduleManager(this, type);
        return sm.createSchedule();
    }

    /**
     * Checks if the User is enrolled in the Event.
     *
     * @param event the Event to check
     * @param user  the User to check
     * @return true if the User is enrolled in the Event.
     */
    public boolean isUserEnrolled(Event event, User user) {
        return em.isUserEnrolled(event, user);
    }

    /**
     * Checks if user can join the event
     *
     * @param event the Event to check.
     * @param user  the User to check.
     * @return true if the user can join.
     */
    public boolean canUserSignup(Event event, User user) {
        return em.canJoin(event, user);
    }

    /**
     * Gets Room from given RoomID
     *
     * @param roomID the RoomID
     * @return Room matching the roomID
     */
    public Room getRoomById(String roomID) {
        return rm.getRoomById(roomID);
    }

    /**
     * Changes the event's max capacity.
     * @param event the Event to modify
     * @param newCap the new capacity. Must be less than or equal to the Room's capacity.
     */
    public void setEventCapacity(Event event, int newCap) {
        if (getRoomById(event.getRoom()).getMaxCapacity() >= newCap) {
            event.setMaxCapacity(newCap);
        }
    }
}
