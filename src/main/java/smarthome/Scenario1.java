package smarthome;

import smarthome.devices.Device;
import smarthome.devices.DeviceFactory;
import smarthome.devices.dishwasher.Dishwasher;
import smarthome.devices.dishwasher.DishwasherEvent;
import smarthome.devices.electricity_generator.Generator;
import smarthome.devices.electricity_generator.GeneratorEvent;
import smarthome.devices.heater.Heater;
import smarthome.devices.heater.HeaterEvent;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.washing_machine.WashingMachine;
import smarthome.devices.washing_machine.WashingMachineEvent;
import smarthome.location.Location;
import smarthome.sensors.ElectricitySensor;
import smarthome.sensors.Sensor;
import smarthome.sensors.TemperatureSensor;
import smarthome.servises.DeviceLog;
import smarthome.servises.LocationConfiguration;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Scenario1 {
    public static void main(String[] args) throws IOException {


        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(10000);
        DeviceLog.getInstance().setPriceElectricity(0.05D);
        DeviceLog.getInstance().setPriceWater(0.01D);
        DeviceLog.getInstance().setPricePetrol(0.03D);

//Configuratiion
        if (LocationConfiguration.getInstance().isFileExist()) {
            LocationConfiguration.getInstance().load();
        } else {

            Location house_and_grounds = new Location("House and grounds", Set.of(df.createDevice(ElectricitySensor.class), df.createDevice(Generator.class)));
            LocationConfiguration.getInstance().setLocation(house_and_grounds);

            Location greengrass = new Location("Greengrass");
            Location house = new Location("House");
            house_and_grounds.setLocations(Set.of(greengrass, house));

            Location floor1 = new Location("Floor#1");
            floor1.setLocations(Set.of(
                    new Location("Kitchen", Set.of(
                            df.createDevice(Refrigerator.class),
                            df.createDevice(Dishwasher.class),
                            df.createDevice(WashingMachine.class),
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)
                    )),
                    new Location("Family room", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)
                    )),
                    new Location("Living room", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class))),
                    new Location("Dinning room", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bath#1", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)
                    ))));

            Location floor2 = new Location("Floor#2");
            floor2.setLocations(Set.of(
                    new Location("Bath#2", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bath#3", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bedroom#1", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bedroom#2", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bedroom#3", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)))));

            Location foyer_and_stairs = new Location("Foyer and stars");
            Location garage = new Location("Garage");
            house.setLocations(Set.of(foyer_and_stairs, garage, floor1, floor2));


        }
        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(LocationConfiguration.getInstance().getLocation()));

// Sensors + devises
// Temperature

        for (Location location : dispatcher.getMapLocation().values()) {
            for (Heater heater : location.getDevicesByType(Heater.class)) {
                dispatcher.sendMessage(Message.toDevice(HeaterEvent.TURN_ON, heater.getId()));
                List<TemperatureSensor> sensors = location.getDevicesByType(TemperatureSensor.class);
                if (sensors.size() == 1) {
                    Sensor sensor = sensors.get(0);
                    sensor.addListener(value -> {
                        if (value < 21) {
                            Dispatcher.getInstance().sendMessage(Message.toDevice(HeaterEvent.TURN_ON, heater.getId()));
                            System.out.println("Temperature in " + location.getId() + " is " + new DecimalFormat("#0").format(sensors.get(0).getData().getValue()) + "\u00B0" + "C");
                        } else if (value >= 21) {
                            Dispatcher.getInstance().sendMessage(Message.toDevice(HeaterEvent.TURN_OFF, heater.getId()));
                            System.out.println("Temperature in " + location.getId() + " is " + new DecimalFormat("#0").format(sensors.get(0).getData().getValue()) + "\u00B0" + "C");
                        }
                    });
                }
            }
        }



//Scenario
        // Refrigerator
        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "House"));
        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.CHANGE_TEMPERATURE, "Refrigerator#1", Map.of("target", 4)));
// Change temperature
        for (Location location : dispatcher.getMapLocation().values()) {
            List<TemperatureSensor> sensors = location.getDevicesByType(TemperatureSensor.class);
            if (sensors.size() == 1) {
                Scheduler.getInstance().schedule(() -> {
                    sensors.get(0).setValue(18 + Math.random() * 5);
                }, 30, TimeUnit.MINUTES);

            }

        }
        // Lamps
        for (Location location : dispatcher.getMapLocation().values()) {
            List<Lamp> devices = location.getDevicesByType(Lamp.class);
            devices.forEach(it -> Scheduler.getInstance().schedule(() -> {
                dispatcher.sendMessage(Message.toDevice(LampEvent.TURN_ON, it.getId()));
            }, 30, TimeUnit.MINUTES));

        }

        dispatcher.sendMessage(Message.toLocation(DishwasherEvent.TURN_ON, "Kitchen"));
        dispatcher.sendMessage(Message.toLocation(DishwasherEvent.GLASS, "Kitchen"));
        dispatcher.sendMessage(Message.toLocation(WashingMachineEvent.TURN_ON, "Kitchen"));
        dispatcher.sendMessage(Message.toLocation(WashingMachineEvent.QUICK_WASH, "Kitchen"));

//power failure
        System.out.println("============================================================================\nPower failure ");
        for (Location location : dispatcher.getMapLocation().values()) {
            List<ElectricitySensor> sensors = location.getDevicesByType(ElectricitySensor.class);
            if (sensors.size() == 1) {
                sensors.get(0).addListener(value -> {
                    if (value == 0) {
                        dispatcher.sendMessage(Message.toDevice(GeneratorEvent.TURN_ON, "Generator#1"));
                        dispatcher.getMapDevice().values().forEach(it -> {
                            if (it instanceof StateMachine  && !it.isVital()) {
                                dispatcher.sendMessage(Message.toDevice(((StateMachine<?, ?>) it).toEvent("TURN_OFF"), it.getId()));
                            }
                        });

                    }else{
                        dispatcher.sendMessage(Message.toDevice(GeneratorEvent.TURN_OFF, "Generator#1"));
                    }
                });

            }

        }

        dispatcher.getSensor("ElectricitySensor#1").setValue(0D);

        Scheduler.getInstance().schedule(() -> {
            dispatcher.getSensor("ElectricitySensor#1").setValue(220D);
        }, 160, TimeUnit.MINUTES);


// Report
        Scheduler.getInstance().schedule(() -> {
            DeviceLog.getInstance().report();
        }, 1200, TimeUnit.MINUTES);



    }

}

