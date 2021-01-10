package group0110.gateways;

import java.util.ArrayList;
import java.util.List;

import group0110.eventSystem.Event;

class EventGateway extends ObjectGateway<List<Event>> {
    private final Gateway<List<Event>> gateway;

    EventGateway(String filePath) {
        this.gateway = new Gateway<>(filePath);
    }

    @Override
    boolean save(List<Event> obj) {
        return gateway.serializeObject(obj);
    }

    @Override
    List<Event> read() {
        List<Event> result = gateway.deserializeObject();
        if (result == null) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }
}
