package smarthome.devices.lamp;

import smarthome.devices.Device;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.lamp.LampState;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;

public class Lamp extends StateMachine<LampState, LampEvent> implements Device {
    public Lamp(State<LampState, LampEvent> currentState) {
        super(new State<>(LampState.OFF));
    }
}
