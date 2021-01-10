package group0110.gateways;

import java.util.ArrayList;
import java.util.List;

import group0110.eventSystem.Room;

class RoomGateway extends ObjectGateway<List<Room>> {
    private final Gateway<List<Room>> gateway;

    RoomGateway(String filePath) {
        this.gateway = new Gateway<>(filePath);
    }

    @Override
    boolean save(List<Room> obj) {
        return gateway.serializeObject(obj);
    }

    @Override
    List<Room> read() {
        List<Room> result = gateway.deserializeObject();
        if (result == null) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }
}
