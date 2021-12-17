import smarthome.Dispatcher;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.lamp.LampState;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.refrigerator.RefrigeratorState;
import smarthome.location.Location;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Location house = new Location("House");
        house.setDevices(Set.of(new Lamp(), new Refrigerator()));
        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(house));


        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "House"));
    }
}
