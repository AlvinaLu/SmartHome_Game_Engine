package smarthome.devices;

import smarthome.servises.IdGenerator;

import java.lang.reflect.InvocationTargetException;

public class DeviceFactory {
    private static DeviceFactory instance;

    public static DeviceFactory getInstance() {
        if (instance == null) {
            instance = new DeviceFactory();
        }
        return instance;
    }

    public <T extends Device<?>> T createDevice(Class<T> tClass){
        try {
            T device = tClass.getDeclaredConstructor().newInstance();
            device.setId(IdGenerator.getInstance().generateId(tClass));
            return device;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
