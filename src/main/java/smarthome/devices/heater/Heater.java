package smarthome.devices.heater;

import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;


public class Heater extends StateMachine<HeaterState, HeaterEvent> implements Device<HeaterData> {
    private String id;
    private int targetTemperature = 0;
    private HeaterData heaterData = new HeaterData();

    public Heater() {
        super(HeaterState.OFF);
        addTransition(new Transition<>(HeaterState.OFF, HeaterState.ON, HeaterEvent.TURN_ON));
        addTransition(new Transition<>(HeaterState.ON, HeaterState.OFF, HeaterEvent.TURN_OFF));
    }

    @Override
    public boolean isVital() {
        return true;
    }

    @Override
    protected void onEnter(HeaterState currentState) {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public HeaterEvent toEvent(String name) {
        return HeaterEvent.valueOf(name);
    }

    @Override
    public HeaterData getData() {
        return heaterData;
    }

    @Override
    public String getDoc() {
        return "heater";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(HeaterData data) {
        heaterData = data;
    }

    @Override
    public String toString() {
        return "Heater{" +
                "id='" + id + '\'' +
                '}';
    }
}
