package smarthome.devices.lamp;

import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class Lamp extends StateMachine<LampState, LampEvent> implements Device<LampData> {
    private String id;
    private LampData lampData = new LampData();

    public Lamp() {
        super(LampState.OFF);
        addTransition(new Transition<>(LampState.OFF, LampState.ON, LampEvent.TURN_ON));
        addTransition(new Transition<>(LampState.ON, LampState.OFF, LampEvent.TURN_OFF));
    }

    @Override
    protected void onEnter(LampState currentState) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LampData getData() {
        return lampData;
    }

    @Override
    public String getDoc() {
        return null;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(LampData data) {
        this.lampData = data;
    }

    @Override
    public String toString() {
        return "Lamp{" +
                "id='" + id + '\'' +
                '}';
    }
}
