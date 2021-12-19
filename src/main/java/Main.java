import smarthome.Dispatcher;
import smarthome.devices.DeviceFactory;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.lamp.LampState;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.refrigerator.RefrigeratorState;
import smarthome.location.Location;
import smarthome.servises.DeviceLog;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(1000);

        Scheduler.getInstance().schedule(()-> DeviceLog.getInstance().report(),100, TimeUnit.MINUTES);

        Location house = new Location("House");
        house.setDevices(Set.of(df.createDevice(Refrigerator.class)));
        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(house));


        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "House"));
        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.CHANGE_TEMPERATURE, "Refrigerator#1", Map.of("target", 4)));
    }
}
