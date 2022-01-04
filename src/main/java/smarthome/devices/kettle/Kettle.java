package smarthome.devices.kettle;

import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class Kettle extends StateMachine<KettleState, KettleEvent> implements Device<KettleData> {

    private KettleData kettleData = new KettleData();
    private String id;
    private int targetTemperature = 0;

    public Kettle() {

        super(KettleState.OFF);

        addTransition(new Transition<>(KettleState.OFF, KettleState.ON, KettleEvent.TURN_ON));
        addTransition(new Transition<>(KettleState.ON, KettleState.OFF, KettleEvent.TURN_OFF));
        addTransition(new Transition<>(KettleState.OFF, KettleState.Timer_Mode, KettleEvent.Timer_Mode));
        addTransition(new Transition<>(KettleState.Timer_Mode, KettleState.OFF, KettleEvent.TURN_OFF));
        addTransition(new Transition<>(KettleState.OFF, KettleState.Temperature_Maintenance, KettleEvent.Temperature_Maintenance));
        addTransition(new Transition<>(KettleState.Temperature_Maintenance, KettleState.OFF, KettleEvent.TURN_OFF));

    }

    @Override
    protected void onEnter(KettleState currentState) {

    }

    @Override
    public String getId() { return this.id; }

    @Override
    public KettleData getData() {
        return this.kettleData;
    }

    @Override
    public String getDoc() {
        return "Kettle";
    }

    @Override
    public void setId(String id) { this.id = id; }

    @Override
    public void setData(KettleData data) { this.kettleData = data; }

}