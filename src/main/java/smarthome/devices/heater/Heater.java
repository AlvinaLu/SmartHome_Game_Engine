package smarthome.devices.heater;

import smarthome.devices.Device;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.UUID;

public class Heater extends StateMachine<HeaterState, HeaterEvent> implements Device<HeaterData> {
    private String id = UUID.randomUUID().toString();
    private int targetTemperature = 0;
    private HeaterData heaterData = new HeaterData();

    public Heater(){
        super(new State<>(HeaterState.OFF));
        addTransition(new Transition<>(HeaterState.OFF, HeaterState.ON, HeaterEvent.TURN_ON));
        addTransition(new Transition<>(HeaterState.ON, HeaterState.OFF, HeaterEvent.TURN_OFF));
    }


    @Override
    public String getId() {
        return id;
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
    protected void onTransition(Transition<HeaterState, HeaterEvent> transition, Message<HeaterEvent> message) {

    }
}
