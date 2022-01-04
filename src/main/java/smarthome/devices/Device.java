package smarthome.devices;

public interface Device<T> {

    String getId();

    T getData();

    String getDoc();

    void setId(String id);

    void setData(T data);

    default boolean isVital() {
        return false;
    }
}
