import smarthome.Dispatcher;
import smarthome.devices.DeviceFactory;
import smarthome.devices.air_conditioner.AirConditioner;
import smarthome.devices.air_conditioner.AirConditionerEvent;
import smarthome.devices.alarmSystem.AlarmSystem;
import smarthome.devices.alarmSystem.AlarmSystemEvent;
import smarthome.devices.coffee_machine.Coffee;
import smarthome.devices.coffee_machine.CoffeeMachine;
import smarthome.devices.coffee_machine.CoffeeMachineEvent;
import smarthome.devices.dishwasher.Dishwasher;
import smarthome.devices.heater.Heater;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.washing_machine.WashingMachine;
import smarthome.devices.watercloset.WaterCloset;
import smarthome.devices.watercloset.WaterClosetEvent;
import smarthome.devices.waterconsumer.WaterConsumer;
import smarthome.devices.waterconsumer.WaterConsumerEvent;
import smarthome.location.House;
import smarthome.location.Location;
import smarthome.sensors.*;
import smarthome.servises.DeviceLog;
import smarthome.servises.LocationConfiguration;
import smarthome.servises.Scheduler;
import smarthome.skinbag.Skinbag;
import smarthome.statemachine.Message;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {


        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(100000);

        Scheduler.getInstance().schedule(() -> DeviceLog.getInstance().report(), 100, TimeUnit.MINUTES);

//        if (LocationConfiguration.getInstance().isFileExist()) {
//            LocationConfiguration.getInstance().load();
//        } else {
            Skinbag skinbag = new Skinbag("Dad", Set.of(
                    RefrigeratorEvent.TURN_OFF, RefrigeratorEvent.TURN_ON, RefrigeratorEvent.CHANGE_TEMPERATURE,
                    LampEvent.TURN_ON, LampEvent.TURN_OFF));
            Location house = new Location("House", Set.of(
                    df.createDevice(Refrigerator.class),
                    df.createDevice(Dishwasher.class),
                    df.createDevice(WashingMachine.class),
                    df.createDevice(TemperatureSensor.class),
                    df.createDevice(Heater.class),
                    df.createDevice(Heater.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(WaterConsumer.class),
                    df.createDevice(CoffeeMachine.class),
                    df.createDevice(WaterLeakSensor.class),
                    df.createDevice(AlarmSystem.class),
                    df.createDevice(HumanSensor.class)
            ));

            house.setLocations(Set.of(new Location("Kitchen", Set.of(
                    df.createDevice(Refrigerator.class),
                    df.createDevice(Dishwasher.class),
                    df.createDevice(WashingMachine.class),
                    df.createDevice(TemperatureSensor.class),
                    df.createDevice(Heater.class),
                    df.createDevice(Heater.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(WaterConsumer.class),
                    df.createDevice(CoffeeMachine.class),
                    df.createDevice(WaterLeakSensor.class),
                    df.createDevice(AlarmSystem.class),
                    df.createDevice(HumanSensor.class)
            ), new HashSet<>(Set.of(skinbag)))));



            Refrigerator refrigerator = df.createDevice(Refrigerator.class);
            CoffeeMachine coffeeMachine = df.createDevice(CoffeeMachine.class);
            TemperatureSensor temperatureSensor = df.createDevice(TemperatureSensor.class);
            WaterConsumer waterConsumer = df.createDevice(WaterConsumer.class);
            WaterCloset waterCloset = df.createDevice(WaterCloset.class);
            FireSensor fireSensor = df.createDevice(FireSensor.class);
            AlarmSystem alarmSystem1 = df.createDevice(AlarmSystem.class);
            AirConditioner airConditioner = df.createDevice(AirConditioner.class);
            house.setDevices(Set.of(refrigerator, coffeeMachine, temperatureSensor, waterConsumer, waterCloset, airConditioner, fireSensor, alarmSystem1));
            LocationConfiguration.getInstance().setLocation(house);

//        }


        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(LocationConfiguration.getInstance().getLocation()));



//        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "House"));
//        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.CHANGE_TEMPERATURE, "Refrigerator#1", Map.of("target", 4)));
//        dispatcher.sendMessage(Message.toDevice(CoffeeMachineEvent.TURN_ON, "CoffeeMachine#1"));
//        dispatcher.sendMessage(Message.toDevice(CoffeeMachineEvent.POURS_COFFEE, "CoffeeMachine#1", Map.of("coffee", "Americano")));
//        dispatcher.sendMessage(Message.toDevice(WaterConsumerEvent.TURN_ON_COLD_WATER, "WaterConsumer#1"));
//     dispatcher.sendMessage(Message.toDevice(WaterClosetEvent.TURN_ON, "WaterCloset#1"));
//        dispatcher.sendMessage(Message.toDevice(AirConditionerEvent.TURN_ON, "AirConditioner#1"));
//        dispatcher.sendMessage(Message.toDevice(AirConditionerEvent.CHANGE_TEMPERATURE, "AirConditioner#1", Map.of("target", 18)));

        for (Location location : dispatcher.getMapLocation().values()) {
            if(true){
                List<FireSensor> sensors = location.getDevicesByType(FireSensor.class);
                if (sensors.size() == 1) {
                    sensors.get(0).setValue(12D);
                }
            }

        }

        for (Location location : dispatcher.getMapLocation().values()) {
            if(true){
                List<FireSensor> sensors = location.getDevicesByType(FireSensor.class);
                if (sensors.size() == 1) {
                    sensors.get(0).setValue(12D);
                }
            }

        }
        Scheduler.getInstance().schedule(() -> {
            for (Location location : dispatcher.getMapLocation().values()) {
                if (true) {
                    List<WaterLeakSensor> sensors = location.getDevicesByType(WaterLeakSensor.class);
                    if (sensors.size() == 1) {
                        sensors.get(0).setValue(12D);
                    }
                }
            }
        }, 20, TimeUnit.MINUTES);



        Scheduler.getInstance().schedule(() -> {
            dispatcher.sendMessage(Message.toDevice(AirConditionerEvent.QUIET_MODE, "AirConditioner#1", Map.of("target", 21)));
        }, 1000, TimeUnit.MINUTES);
        Scheduler.getInstance().schedule(() -> {
            dispatcher.sendMessage(Message.toDevice(CoffeeMachineEvent.POURS_COFFEE, "CoffeeMachine#1", Map.of("coffee", "Latte")));
        }, 7, TimeUnit.MINUTES);



    }
}
