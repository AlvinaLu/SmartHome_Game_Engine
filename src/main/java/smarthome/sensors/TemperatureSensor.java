package smarthome.sensors;

import smarthome.devices.Device;

import java.util.EventListener;

public class TemperatureSensor extends Sensor implements Device<SensorData> {
    private String id;
    private SensorData sensorData = new SensorData();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public SensorData getData() {
        return sensorData;
    }

    @Override
    public String getDoc() {
        return "temperature sensor";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(SensorData data) {
        this.sensorData = data;
    }
}
