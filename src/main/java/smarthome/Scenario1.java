package smarthome;

import smarthome.devices.Device;
import smarthome.devices.DeviceFactory;
import smarthome.devices.heater.Heater;
import smarthome.devices.heater.HeaterEvent;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.location.Location;
import smarthome.sensors.TemperatureSensor;
import smarthome.servises.DeviceLog;
import smarthome.servises.LocationConfiguration;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Scenario1 {
    public static void main(String[] args) throws IOException {


        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(1000);


//Configuratiion
        if (LocationConfiguration.getInstance().isFileExist()) {
            LocationConfiguration.getInstance().load();
        } else {
            Location house = new Location("House");
            Set<Device> deviceSet = new HashSet<>();
            deviceSet.add(df.createDevice(Refrigerator.class));
            deviceSet.add(df.createDevice(TemperatureSensor.class));
            deviceSet.add(df.createDevice(Heater.class));
            Dispatcher.getInstance().getSensor("fvdff");


            house.setDevices(deviceSet);
            LocationConfiguration.getInstance().setLocation(house);

        }


        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(LocationConfiguration.getInstance().getLocation()));


        dispatcher.getSensor("TemperatureSensor#1").addListener(value -> {
            if(value < 21){
            Dispatcher.getInstance().sendMessage(Message.toDevice(HeaterEvent.TURN_ON, "Heater#1"));
            }else if(value >= 21){
                Dispatcher.getInstance().sendMessage(Message.toDevice(HeaterEvent.TURN_OFF, "Heater#1"));
            }
        });

//Scenario
        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "House"));
        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.CHANGE_TEMPERATURE, "Refrigerator#1", Map.of("target", 4)));
        dispatcher.getSensor("TemperatureSensor#1").setValue(12D);


        Scheduler.getInstance().schedule(()->{},5, TimeUnit.MINUTES);
        Scheduler.getInstance().schedule(()->{},6, TimeUnit.MINUTES);

        Scheduler.getInstance().schedule(() -> DeviceLog.getInstance().report(), 100, TimeUnit.MINUTES);

    }

}
