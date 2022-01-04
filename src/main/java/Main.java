import smarthome.Dispatcher;
import smarthome.devices.DeviceFactory;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.location.Location;
import smarthome.sensors.TemperatureSensor;
import smarthome.servises.DeviceLog;
import smarthome.servises.LocationConfiguration;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {


        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(1000);

        Scheduler.getInstance().schedule(() -> DeviceLog.getInstance().report(), 100, TimeUnit.MINUTES);


        if (LocationConfiguration.getInstance().isFileExist()) {
            LocationConfiguration.getInstance().load();
        } else {
            Location house = new Location("House");
            Refrigerator refrigerator = df.createDevice(Refrigerator.class);
            TemperatureSensor temperatureSensor = df.createDevice(TemperatureSensor.class);
            house.setDevices(Set.of(refrigerator));
            LocationConfiguration.getInstance().setLocation(house);

        }


        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(LocationConfiguration.getInstance().getLocation()));



        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "House"));
        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.CHANGE_TEMPERATURE, "Refrigerator#1", Map.of("target", 4)));


    }
}
