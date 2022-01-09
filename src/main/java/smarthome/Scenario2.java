package smarthome;

import smarthome.devices.Device;
import smarthome.devices.DeviceFactory;
import smarthome.devices.air_conditioner.AirConditioner;
import smarthome.devices.air_conditioner.AirConditionerEvent;
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
import smarthome.devices.kettle.Kettle;
import smarthome.devices.kettle.KettleEvent;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.oven.Oven;
import smarthome.devices.oven.OvenEvent;
import smarthome.devices.refrigerator.Refrigerator;
import smarthome.devices.refrigerator.RefrigeratorEvent;
import smarthome.devices.stove.Stove;
import smarthome.devices.stove.StoveEvent;
import smarthome.devices.tv.TV;
import smarthome.devices.tv.TVEvent;
import smarthome.devices.washing_machine.WashingMachine;
import smarthome.devices.washing_machine.WashingMachineEvent;
import smarthome.devices.watercloset.WaterClosetEvent;
import smarthome.devices.waterconsumer.WaterConsumerEvent;
import smarthome.location.Location;
import smarthome.sensors.ElectricitySensor;
import smarthome.sensors.HumanSensor;
import smarthome.sensors.Sensor;
import smarthome.sensors.TemperatureSensor;
import smarthome.servises.DeviceLog;
import smarthome.servises.LocationConfiguration;
import smarthome.servises.Scheduler;
import smarthome.skinbag.Skinbag;
import smarthome.statemachine.Message;
import smarthome.statemachine.SmEvent;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Scenario2 {

    public static void main(String[] args) {

        DeviceFactory df = DeviceFactory.getInstance();
        Scheduler.getInstance().setTimeScale(100000);

        //Prices are up for 2022.
        DeviceLog.getInstance().setPriceElectricity(0.09D);
        DeviceLog.getInstance().setPriceWater(0.04D);
        DeviceLog.getInstance().setPricePetrol(0.40D);


        //Configuratiion

        if (LocationConfiguration.getInstance().isFileExist()) {
            LocationConfiguration.getInstance().load();
        } else {

            Location flatAndBalcony = new Location("Flat and balcony", Set.of(df.createDevice(ElectricitySensor.class), df.createDevice(AlarmSystem.class)) , false);
            LocationConfiguration.getInstance().setLocation(flatAndBalcony);

            Location flat = new Location("Flat");

            Location balcony = new Location("Balcony", Set.of(
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class),
                    df.createDevice(Lamp.class)),
                    false);

            flatAndBalcony.setLocations(Set.of(flat, balcony));

//            Skinbag skinbag = new Skinbag("GrandMother", Set.of(
//                    RefrigeratorEvent.TURN_OFF, RefrigeratorEvent.TURN_ON, RefrigeratorEvent.CHANGE_TEMPERATURE,
//                    LampEvent.TURN_ON, LampEvent.TURN_OFF));

            //husband and wife
            Skinbag tomas = new Skinbag("Tomas", getPermission("Tomas"));
            Skinbag renata = new Skinbag("Renata", getPermission("Lolla"));

            //husband and wife
            Skinbag mark = new Skinbag("Mark", getPermission("Mark"));
            Skinbag bella = new Skinbag("Bella", getPermission("Bella"));

            flat.setLocations(Set.of(
                    new Location("Kitchen", Set.of(
                            df.createDevice(Refrigerator.class),
                            df.createDevice(Dishwasher.class),
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(AirConditioner.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(CoffeeMachine.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Kettle.class),
                            df.createDevice(Stove.class),
                            df.createDevice(TV.class),
                            df.createDevice(Oven.class)
                    ), new HashSet<>(Set.of(mark, bella, tomas, renata))), // OR can be immutable
                    new Location("Living room", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Heater.class),
                            df.createDevice(AudioStation.class),
                            df.createDevice(AirConditioner.class),
                            df.createDevice(TV.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)
                    ), new HashSet<>(Set.of(mark, bella, tomas, renata))),
                    new Location("Bedroom #1", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(AirConditioner.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(TV.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)), new HashSet<>(Set.of(mark, bella, tomas, renata))),
                    new Location("Bedroom #2", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(AirConditioner.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(Heater.class),
                            df.createDevice(TV.class),
                            df.createDevice(AudioStation.class),
                            df.createDevice(AirConditioner.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)), new HashSet<>(Set.of(mark, bella, tomas, renata))),
                    new Location("WC", Set.of(
                            df.createDevice(TemperatureSensor.class),
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Heater.class),
                            df.createDevice(WashingMachine.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)), new HashSet<>(Set.of(mark, bella, tomas, renata))),
                    new Location("Hallway", Set.of(
                            df.createDevice(HumanSensor.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class),
                            df.createDevice(Lamp.class)
                    ), new HashSet<>(Set.of(mark, bella, tomas, renata)))));


        }


        Dispatcher dispatcher = Dispatcher.getInstance();
        dispatcher.init(Set.of(LocationConfiguration.getInstance().getLocation()));


        Set<Skinbag> familyTomasAndRenata = new HashSet<>();
        Set<Skinbag> familyMarkAndBella = new HashSet<>();

        for (Skinbag skinbag : dispatcher.getSkinbags()){
            if (skinbag.getId().equals("Tomas") || skinbag.getId().equals("Renata")){
                familyTomasAndRenata.add(skinbag);
            }else if(skinbag.getId().equals("Mark") || skinbag.getId().equals("Bella")){
                familyMarkAndBella.add(skinbag);
            }
        }


        // Sensors + devises

        // Temperature heaters (All heaters off - summer season)

        for (Location location : dispatcher.getMapLocation().values()){
            for (Heater heater : location.getDevicesByType(Heater.class)){
                dispatcher.sendMessage(Message.toDevice(HeaterEvent.TURN_OFF, heater.getId()));
            }
        }


        // Temperature AirConditioner
        for (Location location : dispatcher.getMapLocation().values()) {
            for (AirConditioner airConditioner : location.getDevicesByType(AirConditioner.class)) {
                dispatcher.sendMessage(Message.toDevice(AirConditionerEvent.TURN_ON, airConditioner.getId()));
                List<TemperatureSensor> sensors = location.getDevicesByType(TemperatureSensor.class);
                if (sensors.size() == 1) {
                    Sensor sensor = sensors.get(0);
                    sensor.addListener(value -> {
                        if (value > 26) {
                            Dispatcher.getInstance().sendMessage(Message.toDevice(AirConditionerEvent.TURN_ON, airConditioner.getId()) );
                            Dispatcher.getInstance().sendMessage(Message.toDevice(AirConditionerEvent.CHANGE_TEMPERATURE, airConditioner.getId(), Map.of("target", 16)));
                            System.out.println("Temperature in " + location.getId() + " is " + new DecimalFormat("#0").format(sensors.get(0).getData().getValue()) + "\u00B0" + "C");
                        } else if (value < 22) {
                            Dispatcher.getInstance().sendMessage(Message.toDevice(AirConditionerEvent.TURN_ON, airConditioner.getId()) );
                            Dispatcher.getInstance().sendMessage(Message.toDevice(AirConditionerEvent.CHANGE_TEMPERATURE, airConditioner.getId(), Map.of("target", 30)));
                            System.out.println("Temperature in " + location.getId() + " is " + new DecimalFormat("#0").format(sensors.get(0).getData().getValue()) + "\u00B0" + "C");
                        }else if(value >= 22 && value <= 26){
                            Dispatcher.getInstance().sendMessage(Message.toDevice(AirConditionerEvent.TURN_OFF, airConditioner.getId()));
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

        //Scenario

        // Refrigerator
        dispatcher.sendMessage(Message.toLocation(RefrigeratorEvent.TURN_ON, "Kitchen"));
        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.CHANGE_TEMPERATURE, "Refrigerator #1", Map.of("target", 4)));


        // Change temperature
        for (Location location : dispatcher.getMapLocation().values()) {
            List<TemperatureSensor> sensors = location.getDevicesByType(TemperatureSensor.class);
            if (sensors.size() == 1) {
                Scheduler.getInstance().schedule(() -> {
                    sensors.get(0).setValue(21 + Math.random() * 19);
                }, 30 + (int) (Math.random() * 100), TimeUnit.MINUTES);

            }

        }

        //morning
        for(Skinbag skinbag : familyTomasAndRenata){
            skinbag.goToNewLocation(dispatcher.getMapLocation().get("Bedroom #2"));
        }

        for(Skinbag skinbag : familyMarkAndBella){
            skinbag.goToNewLocation(dispatcher.getMapLocation().get("Bedroom #1"));
        }

        dispatcher.sendMessage(Message.toDevice(KettleEvent.TURN_ON, "Kettle #1"));
        dispatcher.sendMessage(Message.toDevice(KettleEvent.TIMER_MODE, "Kettle #1", Map.of("timer", 0.5)));

        Scheduler.getInstance().schedule(() -> {

            for(Skinbag skinbag : familyTomasAndRenata){
                skinbag.goToNewLocation(dispatcher.getMapLocation().get("Kitchen"));
            }

        }, 10, TimeUnit.MINUTES);

        dispatcher.sendMessage(Message.toDevice(KettleEvent.TIMER_MODE, "Kitchen", Map.of("timer", 0.5)));

        dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_ON_HOT_WATER, "WC"));
        for(Skinbag skinbag : familyMarkAndBella){

            Scheduler.getInstance().schedule(() -> {
                skinbag.goToNewLocation(dispatcher.getMapLocation().get("WC"));
            }, 5, TimeUnit.MINUTES);

            Scheduler.getInstance().schedule(() -> {
                dispatcher.sendMessage(Message.toLocation(WaterConsumerEvent.TURN_OFF, "WC"));
                skinbag.goToNewLocation(dispatcher.getMapLocation().get("Kitchen"));
            }, 5, TimeUnit.MINUTES);
        }

        dispatcher.sendMessage(Message.toDevice(StoveEvent.TURN_ON, "Stove #1"));

        Scheduler.getInstance().schedule(() -> {
            dispatcher.sendMessage(Message.toDevice(StoveEvent.TURN_OFF, "Stove #1"));
        }, 15, TimeUnit.MINUTES);

        dispatcher.sendMessage(Message.toDevice(CoffeeMachineEvent.POURS_COFFEE, "CoffeeMachine #1", Map.of("coffee", "Americano")));
        dispatcher.sendMessage(Message.toDevice(CoffeeMachineEvent.POURS_COFFEE, "CoffeeMachine #1", Map.of("coffee", "Cappuccino")));
        dispatcher.sendMessage(Message.toDevice(CoffeeMachineEvent.POURS_COFFEE, "CoffeeMachine #1", Map.of("coffee", "Cappuccino")));

        dispatcher.sendMessage(Message.toLocation(AudioStationEvent.TURN_ON, "Living room"));
        dispatcher.sendMessage(Message.toLocation(AudioStationEvent.PLAY, "Living room", Map.of("audio", "LADY_GAGA")));

        dispatcher.sendMessage(Message.toDevice(DishwasherEvent.TURN_ON, "Dishwasher #1"));
        dispatcher.sendMessage(Message.toDevice(DishwasherEvent.INTENSIVE, "Dishwasher #1"));

        for(Skinbag skinbag : familyMarkAndBella){

            Scheduler.getInstance().schedule(() -> {
                skinbag.goToNewLocation(dispatcher.getMapLocation().get("WC"));
            }, 5, TimeUnit.MINUTES);

            Scheduler.getInstance().schedule(() -> {
                skinbag.goToNewLocation(dispatcher.getMapLocation().get("Living room"));
            }, 5, TimeUnit.MINUTES);
        }

        //noon

        dispatcher.sendMessage(Message.toDevice(TVEvent.TURN_ON, "TV #1"));
        dispatcher.sendMessage(Message.toDevice(TVEvent.CHANGE_CHANNEL, "TV #1", Map.of("channel", "5")));

        for(Skinbag skinbag : familyTomasAndRenata){
            skinbag.goToNewLocation();
            skinbag.doSomething();

            Scheduler.getInstance().schedule(() -> {

                if (skinbag.getId().equals("Renata") && skinbag.getLocation().getId().equals("Kitchen")){
                    dispatcher.sendMessage(Message.toDevice(StoveEvent.TURN_ON, "Stove #1"));
                    System.out.println("Cooks borsh!");

                    Scheduler.getInstance().schedule(() -> {
                        dispatcher.sendMessage(Message.toDevice(StoveEvent.TURN_OFF, "Stove #1"));
                    }, 50, TimeUnit.MINUTES);

                }else if(skinbag.getId().equals("Renata") && skinbag.getLocation().getId().equals("Bedroom #2")){
                    dispatcher.sendMessage(Message.toLocation(AudioStationEvent.TURN_ON, "Bedroom #2"));
                    dispatcher.sendMessage(Message.toLocation(AudioStationEvent.PLAY, "Bedroom #2", Map.of("audio", "FRANCHE_RAP")));

                }

                if(skinbag.getId().equals("Tomas") && skinbag.getLocation().getId().equals("Living room")){


                    skinbag.goToNewLocation(dispatcher.getMapLocation().get("Balcony"));


                    Scheduler.getInstance().schedule(() -> {
                        skinbag.goToNewLocation(dispatcher.getMapLocation().get("Bedroom #2"));
                    }, 50, TimeUnit.MINUTES);

                }if(skinbag.getId().equals("Tomas") && skinbag.getLocation().getId().equals("Kitchen")){

                    dispatcher.sendMessage(Message.toDevice(KettleEvent.HEAT, "Kettle #1"));

                }

            }, 2, TimeUnit.HOURS);

        }

        //day&evening

        dispatcher.sendMessage(Message.toDevice(RefrigeratorEvent.BROKEN, "Refrigerator#1"));

        dispatcher.sendMessage(Message.toDevice(OvenEvent.TURN_ON, "Oven #1"));

        Scheduler.getInstance().schedule(() -> {
            dispatcher.sendMessage(Message.toDevice(OvenEvent.TURN_OFF, "Oven #1"));
        }, 90, TimeUnit.MINUTES);

        for (Skinbag skinbag : dispatcher.getSkinbags()){
            skinbag.goToNewLocation();
            skinbag.doSomething();

            for (Location location: dispatcher.getMapLocation().values()){
                if (skinbag.getLocation() == location){
                    dispatcher.sendMessage(Message.toDevice(TVEvent.TURN_ON, "TV #1"));
                    dispatcher.sendMessage(Message.toDevice(TVEvent.CHANGE_CHANNEL, "TV #1", Map.of("channel", 1)));
                }
            }

        }

        //day&evening

        for (Skinbag skinbag : familyMarkAndBella){
            skinbag.goToNewLocation(dispatcher.getMapLocation().get("Bedroom #1"));

        }

        for (Skinbag skinbag : familyTomasAndRenata){
            skinbag.goToNewLocation(dispatcher.getMapLocation().get("Bedroom #2"));

        }


        for (Location location : dispatcher.getMapLocation().values()){
            dispatcher.sendMessage(Message.toLocation(LampEvent.TURN_OFF, location.getId()));
        }

        // Report
        Scheduler.getInstance().schedule(() -> {
            DeviceLog.getInstance().report();
        }, 24, TimeUnit.HOURS);
    }


    static public Set<SmEvent> getPermission(String person){

        Set<SmEvent> permission = new HashSet<>();

        if(person.equals("Tomas") || person.equals("Mark")){
            permission.addAll(Arrays.asList(RefrigeratorEvent.values()));
            permission.addAll(Arrays.asList(AirConditionerEvent.values()));
            permission.addAll(Arrays.asList(AudioStationEvent.values()));
            permission.addAll(Arrays.asList(CoffeeMachineEvent.values()));
            permission.addAll(Arrays.asList(DishwasherEvent.values()));
            permission.addAll(Arrays.asList(GeneratorEvent.values()));
            permission.addAll(Arrays.asList(HeaterEvent.values()));
            permission.addAll(Arrays.asList(KettleEvent.values()));
            permission.addAll(Arrays.asList(LampEvent.values()));
            permission.addAll(Arrays.asList(OvenEvent.values()));
            permission.addAll(Arrays.asList(StoveEvent.values()));
            permission.addAll(Arrays.asList(TVEvent.values()));
            permission.addAll(Arrays.asList(WashingMachineEvent.values()));
            permission.addAll(Arrays.asList(WaterClosetEvent.values()));
            permission.addAll(Arrays.asList(WaterConsumerEvent.values()));
        }else {
            permission.addAll(Arrays.asList(RefrigeratorEvent.values()));
            permission.addAll(Arrays.asList(AirConditionerEvent.values()));
            permission.addAll(Arrays.asList(AudioStationEvent.values()));
            permission.addAll(Arrays.asList(CoffeeMachineEvent.values()));
            permission.addAll(Arrays.asList(DishwasherEvent.values()));
            permission.addAll(Arrays.asList(HeaterEvent.values()));
            permission.addAll(Arrays.asList(KettleEvent.values()));
            permission.addAll(Arrays.asList(LampEvent.values()));
            permission.addAll(Arrays.asList(OvenEvent.values()));
            permission.addAll(Arrays.asList(StoveEvent.values()));
            permission.addAll(Arrays.asList(TVEvent.values()));
            permission.addAll(Arrays.asList(WashingMachineEvent.values()));
            permission.addAll(Arrays.asList(WaterClosetEvent.values()));
            permission.addAll(Arrays.asList(WaterConsumerEvent.values()));

        }

        return permission;
    }
}
