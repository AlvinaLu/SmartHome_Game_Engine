package smarthome.devices;

public interface Device<T> {
    T getData();

    String getDoc();
}
