package smarthome.devices.lamp;

import smarthome.devices.Device;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.lamp.LampState;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class Lamp extends StateMachine<LampState, LampEvent> implements Device {
    public Lamp(State<LampState, LampEvent> currentState) {
        super(new State<>(LampState.OFF));
    }

    @Override
    protected void onTransition(Transition<LampState, LampEvent> transition, Message<LampEvent> message) {

    }
}
