package group0110.gateways;

import java.util.ArrayList;
import java.util.List;

import group0110.entities.User;

class UserGateway extends ObjectGateway<List<User>> {
    private final Gateway<List<User>> gateway;

    UserGateway(String filePath) {
        gateway = new Gateway<>(filePath);
    }

    @Override
    boolean save(List<User> userList) {
        return gateway.serializeObject(userList);
    }

    @Override
    List<User> read() {
        List<User> result = gateway.deserializeObject();
        if (result == null) {
            return new ArrayList<>();
        } else {
            return result;
        }
    }
}
