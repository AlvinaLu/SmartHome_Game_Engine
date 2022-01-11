package smarthome;

import smarthome.devices.Device;
import smarthome.devices.DeviceFactory;
import smarthome.devices.alarmSystem.AlarmSystem;
import smarthome.devices.alarmSystem.AlarmSystemEvent;
import smarthome.devices.audio_station.AudioStation;
import smarthome.devices.audio_station.AudioStationEvent;
import smarthome.devices.coffee_machine.CoffeeMachine;
import smarthome.devices.coffee_machine.CoffeeMachineEvent;
import smarthome.devices.dishwasher.Dishwasher;
import smarthome.devices.dishwasher.DishwasherEvent;
import smarthome.devices.electricity_generator.Generator;
import smarthome.devices.electricity_generator.GeneratorEvent;
import smarthome.devices.heater.Heater;
import smarthome.devices.heater.HeaterEvent;
import smarthome.devices.kettle.KettleEvent;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.oven.OvenEvent;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.stove.StoveEvent;
import smarthome.devices.tv.TV;
import smarthome.devices.tv.TVEvent;
import smarthome.devices.washing_machine.WashingMachine;
import smarthome.devices.washing_machine.WashingMachineEvent;
import smarthome.devices.watercloset.WaterClosetEvent;
import smarthome.devices.waterconsumer.WaterConsumer;
import smarthome.devices.waterconsumer.WaterConsumerEvent;
import smarthome.location.Location;
import smarthome.sensors.*;
import smarthome.servises.DeviceLog;
import smarthome.servises.LocationConfiguration;
import smarthome.servises.Scheduler;
import smarthome.skinbag.Skinbag;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Scenario1 {
    public static void start() {


        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(10000);
        DeviceLog.getInstance().setPriceElectricity(0.05D);
        DeviceLog.getInstance().setPriceWater(0.01D);
        DeviceLog.getInstance().setPricePetrol(0.03D);
//Configuratiion
        if (LocationConfiguration.getInstance().isFileExist()) {
            LocationConfiguration.getInstance().load();
        } else {

            Location houseAndGrounds = new Location("House and grounds", Set.of(df.createDevice(ElectricitySensor.class), df.createDevice(Generator.class), df.createDevice(AlarmSystem.class)), false);
            LocationConfiguration.getInstance().setLocation(houseAndGrounds);

            Location greengrass = new Location("Greengrass");
            Location house = new Location("House", false);
            houseAndGrounds.setLocations(Set.of(greengrass, house));

            Location floor1 = new Location("Floor#1", false);
            Skinbag dad = new Skinbag("Dad", Set.of(
                    RefrigeratorEvent.TURN_OFF, RefrigeratorEvent.TURN_ON, RefrigeratorEvent.CHANGE_TEMPERATURE,
                    LampEvent.TURN_ON, LampEvent.TURN_OFF, AudioStationEvent.TURN_OFF, AudioStationEvent.TURN_ON, AudioStationEvent.PLAY,
                    AudioStationEvent.TURN_UP, AudioStationEvent.TURN_DOWN, AudioStationEvent.PAUSE, CoffeeMachineEvent.TURN_ON,
                    CoffeeMachineEvent.TURN_OFF, CoffeeMachineEvent.POURS_COFFEE, DishwasherEvent.TURN_OFF, DishwasherEvent.TURN_ON, DishwasherEvent.ECO,
                    DishwasherEvent.INTENSIVE, DishwasherEvent.GLASS, KettleEvent.TURN_ON, KettleEvent.TURN_OFF, KettleEvent.TIMER_MODE, KettleEvent.TEMPERATURE_MAINTENANCE,
                    KettleEvent.HEAT, OvenEvent.TURN_OFF, OvenEvent.TURN_ON, OvenEvent.CHANGE_TEMPERATURE, OvenEvent.SET_MODE, StoveEvent.TURN_ON,
                    StoveEvent.TURN_OFF, StoveEvent.LOCK, StoveEvent.UNLOCK, TVEvent.TURN_OFF, TVEvent.TURN_ON, TVEvent.CHANGE_VOLUME, TVEvent.CHANGE_CHANNEL, TVEvent.TURN_UP,
                    TVEvent.TURN_DOWN, WashingMachineEvent.TURN_OFF, WashingMachineEvent.TURN_ON, WashingMachineEvent.ANTI_ALLERGY, WashingMachineEvent.QUICK_WASH, WashingMachineEvent.WOOL,
                    WaterClosetEvent.TURN_OFF, WaterClosetEvent.TURN_ON, WaterConsumerEvent.TURN_OFF, WaterConsumerEvent.TURN_ON_COLD_WATER, WaterConsumerEvent.TURN_ON_WARM_WATER,
                    WaterConsumerEvent.TURN_ON_HOT_WATER));
            Skinbag mom = new Skinbag("Mom", Set.of(
                    RefrigeratorEvent.TURN_OFF, RefrigeratorEvent.TURN_ON, RefrigeratorEvent.CHANGE_TEMPERATURE,
                    LampEvent.TURN_ON, LampEvent.TURN_OFF, AudioStationEvent.TURN_OFF, AudioStationEvent.TURN_ON, AudioStationEvent.PLAY,
                    AudioStationEvent.TURN_UP, AudioStationEvent.TURN_DOWN, AudioStationEvent.PAUSE, CoffeeMachineEvent.TURN_ON,
                    CoffeeMachineEvent.TURN_OFF, CoffeeMachineEvent.POURS_COFFEE, DishwasherEvent.TURN_OFF, DishwasherEvent.TURN_ON, DishwasherEvent.ECO,
                    DishwasherEvent.INTENSIVE, DishwasherEvent.GLASS, KettleEvent.TURN_ON, KettleEvent.TURN_OFF, KettleEvent.TIMER_MODE, KettleEvent.TEMPERATURE_MAINTENANCE,
                    KettleEvent.HEAT, OvenEvent.TURN_OFF, OvenEvent.TURN_ON, OvenEvent.CHANGE_TEMPERATURE, OvenEvent.SET_MODE, StoveEvent.TURN_ON,
                    StoveEvent.TURN_OFF, StoveEvent.LOCK, StoveEvent.UNLOCK, TVEvent.TURN_OFF, TVEvent.TURN_ON, TVEvent.CHANGE_VOLUME, TVEvent.CHANGE_CHANNEL, TVEvent.TURN_UP,
                    TVEvent.TURN_DOWN, WashingMachineEvent.TURN_OFF, WashingMachineEvent.TURN_ON, WashingMachineEvent.ANTI_ALLERGY, WashingMachineEvent.QUICK_WASH, WashingMachineEvent.WOOL,
                    WaterClosetEvent.TURN_OFF, WaterClosetEvent.TURN_ON, WaterConsumerEvent.TURN_OFF, WaterConsumerEvent.TURN_ON_COLD_WATER, WaterConsumerEvent.TURN_ON_WARM_WATER,
                    WaterConsumerEvent.TURN_ON_HOT_WATER));
            Skinbag daughter = new Skinbag("Daughter Liza", Set.of(
                    LampEvent.TURN_ON, LampEvent.TURN_OFF, AudioStationEvent.TURN_OFF, AudioStationEvent.TURN_ON, AudioStationEvent.PLAY,
                    AudioStationEvent.TURN_UP, AudioStationEvent.TURN_DOWN, AudioStationEvent.PAUSE, CoffeeMachineEvent.TURN_ON,
                    CoffeeMachineEvent.TURN_OFF, CoffeeMachineEvent.POURS_COFFEE, TVEvent.TURN_OFF, TVEvent.TURN_ON, TVEvent.CHANGE_VOLUME, TVEvent.CHANGE_CHANNEL, TVEvent.TURN_UP,
                    TVEvent.TURN_DOWN, WaterClosetEvent.TURN_OFF, WaterClosetEvent.TURN_ON, WaterConsumerEvent.TURN_OFF, WaterConsumerEvent.TURN_ON_COLD_WATER, WaterConsumerEvent.TURN_ON_WARM_WATER,
                    WaterConsumerEvent.TURN_ON_HOT_WATER));
            Skinbag baby = new Skinbag("Baby Bart", Set.of(WaterClosetEvent.TURN_OFF, WaterClosetEvent.TURN_ON, WaterConsumerEvent.TURN_OFF, WaterConsumerEvent.TURN_ON_COLD_WATER, WaterConsumerEvent.TURN_ON_WARM_WATER));
            Skinbag cat = new Skinbag("Cat Tom", Set.of());
            Skinbag hamster = new Skinbag("Hamster Boo", Set.of());
            floor1.setLocations(Set.of(
                    new Location("Kitchen", Set.of(
                            df.createDevice(Refrigerator.class),
                            df.createDevice(Dishwasher.class),
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
                    ), new HashSet<>(Set.of(mom))), // OR can be immutable
                    new Location("Family room", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(AudioStation.class),
                            df.createDevice(TV.class)
                    ), new HashSet<>(Set.of(dad))),
                    new Location("Living room", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class)), new HashSet<>(Set.of(hamster, cat))),
                    new Location("Dinning room", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bath#1", Set.of(
                            df.createDevice(WashingMachine.class),
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(WaterConsumer.class),
                            df.createDevice(WaterLeakSensor.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class)
                    ))));

            Location floor2 = new Location("Floor#2", false);
            floor2.setLocations(Set.of(
                    new Location("Bath#2", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(WaterConsumer.class),
                            df.createDevice(WaterLeakSensor.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bath#3", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(WaterLeakSensor.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(WaterConsumer.class),
                            df.createDevice(Lamp.class)), new HashSet<>(Set.of(daughter))),
                    new Location("Bedroom#1", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bedroom#2", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class))),
                    new Location("Bedroom#3", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(AlarmSystem.class),
                            df.createDevice(Lamp.class)), new HashSet<>(Set.of(baby)))));

            Location foyerAndStars = new Location("Foyer and stars", Set.of(
                    df.createDevice(TemperatureSensor.class),
                    df.createDevice(HumanSensor.class),
                    df.createDevice(Heater.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(AlarmSystem.class)));
            Location garage = new Location("Garage", Set.of(
                    df.createDevice(HumanSensor.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(AlarmSystem.class)));
            house.setLocations(Set.of(foyerAndStars, garage, floor1, floor2));


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

        // Person in room
        for (Location location : dispatcher.getMapLocation().values()) {
                List<HumanSensor> sensors = location.getDevicesByType(HumanSensor.class);
                if (sensors.size() == 1) {
                    Sensor sensor = sensors.get(0);
                    sensor.addListener(value -> {
                        if (value >= 0) {
                            Dispatcher.getInstance().sendMessage(Message.toLocation(LampEvent.TURN_ON, location.getId()));
                        } else {
                            Dispatcher.getInstance().sendMessage(Message.toLocation(LampEvent.TURN_ON, location.getId()));
                        }
                    });
                }
        }
        // Fire
        for (Location location : dispatcher.getMapLocation().values()) {
            for (AlarmSystem alarmSystem : location.getDevicesByType(AlarmSystem.class)) {
                List<FireSensor> sensorsFire = location.getDevicesByType(FireSensor.class);
                List<WaterLeakSensor> sensorsWater = location.getDevicesByType(WaterLeakSensor.class);
                if (sensorsFire.size() == 1) {
                    Sensor sensor = sensorsFire.get(0);
                    sensor.addListener(value -> {
                        if (value >= 0) {
                            dispatcher.sendMessage(Message.toDevice(AlarmSystemEvent.TURN_ALARM, alarmSystem.getId(), Map.of("fire", true)));
                            Scheduler.getInstance().schedule(() -> {
                                for (Skinbag skinbag : dispatcher.getSkinbags()) {
                                    skinbag.goToNewLocation(dispatcher.getMapLocation().get("House"));
                                    if (skinbag.getId().equals("Mom")) {
                                        System.out.println(skinbag.getId() + " Call the firemen");
                                    }
                                    if (skinbag.getId().equals("Dad")) {
                                        System.out.println(skinbag.getId() + " Take a fire extinguisher");
                                    }
                                }
                            }, 1, TimeUnit.MINUTES);
                        }
                    });
                }
                if (sensorsWater.size() == 1) {
                    Sensor sensorW = sensorsWater.get(0);
                    sensorW.addListener(value -> {
                        if (value >= 0) {
                            dispatcher.sendMessage(Message.toLocation(AlarmSystemEvent.TURN_ALARM, location.getId(), Map.of("water", true, "waterLocation", location.getId())));
                            Scheduler.getInstance().schedule(() -> {
                                for (Skinbag skinbag : dispatcher.getSkinbags()) {
                                    skinbag.goToNewLocation(location);
                                    if (skinbag.getId().equals("Mom")) {
                                        System.out.println(skinbag.getId() + " clean floor");
                                    }
                                    if (skinbag.getId().equals("Dad")) {
                                        System.out.println(skinbag.getId() + " Call to plumber");
                                    }
                                }
                            }, 1, TimeUnit.MINUTES);
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
                    sensors.get(0).setValue(18 + Math.random() * 6);
                }, 30 + (int) (Math.random() * 100), TimeUnit.MINUTES);

            }

        }
        // Lamps is turn on if Human is in room
        for (Location location : dispatcher.getMapLocation().values()) {
            List<FireSensor> sensors = location.getDevicesByType(FireSensor.class);
            Scheduler.getInstance().schedule(() -> {
                if (sensors.size() > 0) {
                    sensors.get(0).setValue(18 + Math.random() * 6);
                }
            }, 30 + (int) (Math.random() * 100), TimeUnit.MINUTES);
        }

        // Fire
        Scheduler.getInstance().schedule(() -> {
            for (Location location : dispatcher.getMapLocation().values()) {
                if (Math.random() < 0.1) {
                    List<FireSensor> sensors = location.getDevicesByType(FireSensor.class);
                    if (sensors.size() == 1) {
                        sensors.get(0).setValue(12D);
                    }
                }

            }
        }, 50 + (int) (Math.random() * 100), TimeUnit.MINUTES);

        ///WaterLeak
        Scheduler.getInstance().schedule(() -> {
            for (Location location : dispatcher.getMapLocation().values()) {
                if (Math.random() < 0.1) {
                    List<WaterLeakSensor> sensors = location.getDevicesByType(WaterLeakSensor.class);
                    if (sensors.size() == 1) {
                        sensors.get(0).setValue(12D);
                    }
                }
            }
        }, 20 + (int) (Math.random() * 100), TimeUnit.MINUTES);

        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.BROKEN, "kitchen"));
        for (int i = 0; i < 2; i++) {
            for (Location location : dispatcher.getMapLocation().values()) {
                for (Skinbag skinbag : dispatcher.getSkinbags()) {
                    Scheduler.getInstance().schedule(() -> {
                        skinbag.goToNewLocation();
                        skinbag.doSomething();
                        if (skinbag.getId().equals("Dad") && location.equals("kitchen")) {
                            dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_ON_COLD_WATER, "Kitchen" ));
                            dispatcher.sendMessage(Message.toLocation(CoffeeMachineEvent.POURS_COFFEE, location.getId(), Map.of("coffee", "Americano")));
                            dispatcher.sendMessage(Message.toLocation(DishwasherEvent.ECO, location.getId()));
                        }
                        if ((skinbag.getId().equals("Dad") || skinbag.getId().equals("Liza")) && location.equals("Family room")) {
                            if (Math.random() < 0.5){
                                dispatcher.sendMessage(Message.toLocation(TVEvent.TURN_ON, location.getId() ));
                                for(int j = 0; j < 5; j++) {
                                    Scheduler.getInstance().schedule(() -> {
                                        dispatcher.sendMessage(Message.toLocation(TVEvent.CHANGE_CHANNEL, location.getId(), Map.of("channel", Math.random()*100)));
                                    }, 50, TimeUnit.MINUTES);
                                }
                                dispatcher.sendMessage(Message.toLocation(TVEvent.TURN_OFF, location.getId() ));
                            }else {
                                dispatcher.sendMessage(Message.toLocation(AudioStationEvent.TURN_ON, location.getId() ));
                                for(int j = 0; j < 5; j++) {
                                    Scheduler.getInstance().schedule(() -> {
                                        if(skinbag.getId().equals("Dad")) {
                                            dispatcher.sendMessage(Message.toLocation(AudioStationEvent.PLAY, location.getId(), Map.of("audio", "Sex Pistols")));
                                        }else {
                                            dispatcher.sendMessage(Message.toLocation(AudioStationEvent.PLAY, location.getId(), Map.of("audio", "Justin Bieber")));
                                        }
                                    }, 30, TimeUnit.MINUTES);
                                }
                                dispatcher.sendMessage(Message.toLocation(AudioStationEvent.TURN_OFF, location.getId() ));
                            }
                            dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_ON_COLD_WATER, location.getId() ));
                            dispatcher.sendMessage(Message.toLocation(CoffeeMachineEvent.POURS_COFFEE, location.getId(), Map.of("coffee", "Americano")));
                            dispatcher.sendMessage(Message.toLocation(DishwasherEvent.ECO, location.getId()));
                        }
                        if (skinbag.getId().equals("Mom") && location.equals("kitchen")) {
                            dispatcher.sendMessage(Message.toLocation(StoveEvent.UNLOCK, location.getId() ));
                            dispatcher.sendMessage(Message.toLocation(StoveEvent.TURN_ON, location.getId() ));
                            for(int j = 0; j < 5; j++) {
                                dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_ON_WARM_WATER, location.getId()));
                                Scheduler.getInstance().schedule(() -> {
                                    dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_OFF, location.getId()));
                                }, 50, TimeUnit.MINUTES);
                            }
                            Scheduler.getInstance().schedule(() -> {
                                dispatcher.sendMessage(Message.toLocation(StoveEvent.TURN_ON, location.getId()));
                                dispatcher.sendMessage(Message.toLocation(DishwasherEvent.INTENSIVE, location.getId()));
                            }, 50, TimeUnit.MINUTES);
                        }
                        if (skinbag.getId().equals("Mom") && location.equals("Bath#1")) {
                            dispatcher.sendMessage(Message.toLocation(WashingMachineEvent.TURN_ON, location.getId() ));
                            dispatcher.sendMessage(Message.toLocation(WashingMachineEvent.QUICK_WASH, location.getId()));
                        }
                        if(skinbag.getId().equals("Bart") && (location.equals("Bath#1") || location.equals("Bath#2") || location.equals("Bath#3"))){
                            dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_ON_COLD_WATER, location.getId()));
                        }
                        if((skinbag.getId().equals("Mom") || skinbag.getId().equals("Dad")) && (location.equals("Bath#1") || location.equals("Bath#2") || location.equals("Bath#3"))){
                            dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_OFF, location.getId()));
                        }
                        if (Math.random() < 0.2 && skinbag.getId().equals("Hamster Boo") && (location.equals("Bath#1"))){
                            List<WaterLeakSensor> sensors = location.getDevicesByType(WaterLeakSensor.class);
                            if (sensors.size() == 1) {
                                sensors.get(0).setValue(12D);
                                System.out.println(skinbag.getId() + " gnawed the water pipe");
                            }
                        }
                        if (skinbag.getId().equals("Dad")) {
                            dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.BROKEN, "Kitchen"));
                            for (Device device: location.getDevices()){
                                if (device instanceof StateMachine<?, ?>){
                                    StateMachine<?, ?> sm = (StateMachine<?, ?>) device;
                                    if(sm.getCurrentState().toString().equals("BROKEN")) {
                                        Scheduler.getInstance().schedule(() -> {
                                            System.out.println(device.getId() + " is broken");
                                            dispatcher.sendMessage(Message.toLocation(sm.toEvent("TURN_ON"), location.getId()));
                                            System.out.println(device.getId() + " has been repair");
                                        }, 50, TimeUnit.MINUTES);
                                    }
                                }
                            }
                        }
                    }, 10 * i, TimeUnit.MINUTES);
                }
            }
        }


//power failure
        System.out.println("============================================================================\nPower failure ");
        for (Location location : dispatcher.getMapLocation().values()) {
            List<ElectricitySensor> sensors = location.getDevicesByType(ElectricitySensor.class);
            if (sensors.size() == 1) {
                sensors.get(0).addListener(value -> {
                    if (value == 0) {
                        dispatcher.sendMessage(Message.toDevice(GeneratorEvent.TURN_ON, "Generator#1"));
                        dispatcher.getMapDevice().values().forEach(it -> {
                            if (it instanceof StateMachine && !it.isVital()) {
                                dispatcher.sendMessage(Message.toDevice(((StateMachine<?, ?>) it).toEvent("TURN_OFF"), it.getId()));
                            }
                        });

                    } else {
                        dispatcher.sendMessage(Message.toDevice(GeneratorEvent.TURN_OFF, "Generator#1"));
                    }
                });

            }

        }
        long i = (long) (Math.random() * 100);
        Scheduler.getInstance().schedule(() -> {
            dispatcher.getSensor("ElectricitySensor#1").setValue(0D);
        }, 160 + i, TimeUnit.MINUTES);

        Scheduler.getInstance().schedule(() -> {
            System.out.println("============================================================================\nPower failure stop ");
            dispatcher.getSensor("ElectricitySensor#1").setValue(220D);
        }, 167 + 1, TimeUnit.MINUTES);

        dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_ON_COLD_WATER, "Kitchen"));

// Report
        Scheduler.getInstance().schedule(() -> {
            DeviceLog.getInstance().report();
        }, 24, TimeUnit.HOURS);


    }


}



