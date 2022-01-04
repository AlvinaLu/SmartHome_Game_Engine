package smarthome.sensors;

import smarthome.devices.Device;

public class ElectricitySensor extends Sensor implements Device<SensorData> {
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
        return "Electricity sensor";
    }

    @Override
    public void setId(String id) {
        this.id = id;

    }

    @Override
    public void setData(SensorData data) {
        sensorData = data;
    }
}
