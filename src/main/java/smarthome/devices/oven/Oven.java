package smarthome.devices.oven;

import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class Oven extends StateMachine<OvenState, OvenEvent> implements Device<OvenData> {

    private String id;
    OvenData ovenData = new OvenData();

    public Oven() {

        super(OvenState.OFF);
        addTransition(new Transition<>(OvenState.OFF, OvenState.ON, OvenEvent.TURN_ON));
        addTransition(new Transition<>(OvenState.ON, OvenState.OFF, OvenEvent.TURN_OFF));
        addTransition(new Transition<>(OvenState.ON, OvenState.CHANGE_TEMPERATURE, OvenEvent.CHANGE_TEMPERATURE));
        addTransition(new Transition<>(OvenState.CHANGE_TEMPERATURE, OvenState.ON, OvenEvent.TURN_ON));
        addTransition(new Transition<>(OvenState.ON, OvenState.SET_MODE, OvenEvent.SET_MODE));
        addTransition(new Transition<>(OvenState.SET_MODE, OvenState.ON, OvenEvent.TURN_ON));
    }

    @Override
    protected void onEnter(OvenState currentState) {

    }

    @Override
    public String getId() { return this.id; }

    @Override
    public OvenEvent toEvent(String name) {
        return OvenEvent.valueOf(name);
    }

    @Override
    public OvenData getData() {
        return this.ovenData;
    }

    @Override
    public String getDoc() {
        return "OVEN";
    }

    @Override
    public void setId(String id) { this.id = id; }

    @Override
    public void setData(OvenData data) { this.ovenData = data; }

}
