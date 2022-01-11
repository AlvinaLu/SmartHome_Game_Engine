package smarthome.devices;

import smarthome.servises.IdGenerator;

/**
 * Devise factory makes devices with unique ID
 */

public class DeviceFactory {
    private static DeviceFactory instance;

    /**
     * Return instance of factory
     */

    public static DeviceFactory getInstance() {
        if (instance == null) {
            instance = new DeviceFactory();
        }
        return instance;
    }

    /**
     * Devise factory makes devices with unique ID
     * @param tClass required device class
     * @return device with unique ID
     */
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
