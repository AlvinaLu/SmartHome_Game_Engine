import smarthome.devices.lamp.Lamp;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.lamp.LampState;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.refrigerator.RefrigeratorState;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;

import java.util.Collections;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        StateMachine<RefrigeratorState, RefrigeratorEvent> refrigerator = new Refrigerator();
        refrigerator.onMessage(new Message<>(RefrigeratorEvent.TURN_ON, Collections.emptyMap()));
        refrigerator.onMessage(new Message<>(RefrigeratorEvent.CHANGE_TEMPERATURE, Map.of("target", 4)));

    }
}
