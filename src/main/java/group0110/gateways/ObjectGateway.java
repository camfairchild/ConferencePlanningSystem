package group0110.gateways;

abstract class ObjectGateway<T> {
    abstract boolean save(T obj);

    abstract T read();

}
