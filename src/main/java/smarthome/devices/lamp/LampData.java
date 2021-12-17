package smarthome.devices.lamp;

public class LampData {

    private int electricityConsumption;
    private int electricityPerHour;
    private int resourceHours;

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
}
