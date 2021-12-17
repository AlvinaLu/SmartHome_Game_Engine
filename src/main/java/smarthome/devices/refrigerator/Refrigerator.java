package smarthome.devices.refrigerator;

import smarthome.devices.Device;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.lamp.LampState;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Refrigerator extends StateMachine<RefrigeratorState, RefrigeratorEvent> implements Device<RefrigeratorData> {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String id = UUID.randomUUID().toString();

    private int targetTemperature = 0;

    private RefrigeratorData refrigeratorData = new RefrigeratorData();


    public Refrigerator() {
        super(new State<>(RefrigeratorState.OFF));
        addTransition(new Transition<>(RefrigeratorState.OFF, RefrigeratorState.ON, RefrigeratorEvent.TURN_ON));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorEvent.CHANGE_TEMPERATURE));
        addTransition(new Transition<>(RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorState.ON, RefrigeratorEvent.TURN_ON));
        addTransition(new Transition<>(RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public RefrigeratorData getData() {
        return refrigeratorData;
    }

    public void setRefrigeratorData(RefrigeratorData refrigeratorData) {
        this.refrigeratorData = refrigeratorData;
    }

    @Override
    public String getDoc() {
        return "holodos ne vkluchat";
    }

    @Override
    protected void onTransition(Transition<RefrigeratorState, RefrigeratorEvent> transition, Message<RefrigeratorEvent> message) {
        if (message.getEvent().equals(RefrigeratorEvent.CHANGE_TEMPERATURE)) {
            int target = (Integer) message.getData().get("target");
            if (target < this.getData().getCurrentTemperature()) {
                setCurrentTask(scheduler.schedule(() -> {
                    getData().setCurrentTemperature(targetTemperature);
                    onMessage(Message.toDevice(RefrigeratorEvent.TURN_ON,null));
                }, 1, TimeUnit.SECONDS));
                System.out.println("Cooling to " + target + "C");
            } else {
                System.out.println("Heating to " + target + "C");
            }
        }
    }

    @Override
    public String toString() {
        return "Refrigerator{" +
                "id='" + id + '\'' +
                '}';
    }
}
