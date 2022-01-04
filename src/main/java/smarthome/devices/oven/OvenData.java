package smarthome.devices.oven;

public class OvenData {

    private int currentTemperature;
    private int electricityConsumption; //потребление электроэнергии
    private int electricityPerHour;
    private int resourceHours;
    private OvenMode mode;

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(int electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public int getElectricityPerHour() {
        return electricityPerHour;
    }

    public void setElectricityPerHour(int electricityPerHour) {
        this.electricityPerHour = electricityPerHour;
    }

    public int getResourceHours() {
        return resourceHours;
    }

    public void setResourceHours(int resourceHours) {
        this.resourceHours = resourceHours;
    }

    public OvenMode getMode() {
        return mode;
    }

    public void setMode(OvenMode mode) {
        this.mode = mode;
    }


}
