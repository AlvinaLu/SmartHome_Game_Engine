package smarthome.devices.tv;

public class TVData {
    private int currentVolume = 20;
    private int currentСhannel = 1;
    private int electricityConsumption;
    private int electricityPerHour;
    private int resourceHours;


    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public int getCurrentСhannel() {
        return currentСhannel;
    }

    public void setCurrentСhannel(int currentСhannel) {
        this.currentСhannel = currentСhannel;
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


}
