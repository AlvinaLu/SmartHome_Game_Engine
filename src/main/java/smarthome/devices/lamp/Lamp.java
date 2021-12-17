package smarthome.devices.lamp;

import smarthome.devices.Device;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.UUID;

public class Lamp extends StateMachine<LampState, LampEvent> implements Device<LampData> {
    private String id = UUID.randomUUID().toString();
    private LampData lampData = new LampData();

    public Lamp() {
        super(new State<>(LampState.OFF));
        addTransition(new Transition<>(LampState.OFF, LampState.ON, LampEvent.TURN_ON));
        addTransition(new Transition<>(LampState.ON, LampState.OFF, LampEvent.TURN_OFF));
    }



    @Override
    protected void onTransition(Transition<LampState, LampEvent> transition, Message<LampEvent> message) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LampData getData() {
        return null;
    }

    @Override
    public String getDoc() {
        return null;
    }

    @Override
    public String toString() {
        return "Lamp{" +
                "id='" + id + '\'' +
                '}';
    }
}
