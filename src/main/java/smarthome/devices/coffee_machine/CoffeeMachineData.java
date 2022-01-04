package smarthome.devices.coffee_machine;

public class CoffeeMachineData {

    private int electricityConsumption;
    private int electricityPerHour;
    private int resourceHours;
    private Coffee coffee;

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

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }
}
