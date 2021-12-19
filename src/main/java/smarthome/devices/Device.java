package smarthome.devices;

public interface Device<T> {
    String getId();

    T getData();

    String getDoc();

    void setId(String id);
}
