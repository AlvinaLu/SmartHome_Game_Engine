package smarthome.devices.refrigerator;

import smarthome.devices.Device;
import smarthome.devices.lamp.LampEvent;
import smarthome.devices.lamp.LampState;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Refrigerator extends StateMachine<RefrigeratorState, RefrigeratorEvent> implements Device<RefrigeratorData> {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private int targetTemperature = 0;

    private RefrigeratorData refrigeratorData;


    public Refrigerator() {
        super(new State<>(RefrigeratorState.OFF));
        addTransition(new Transition<>(RefrigeratorState.OFF, RefrigeratorState.ON, RefrigeratorEvent.TURN_ON));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorEvent.CHANGE_TEMPERATURE));
        addTransition(new Transition<>(RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorState.ON, RefrigeratorEvent.TURN_ON));
    }

    @Override
    public RefrigeratorData getData() {
        return refrigeratorData;
    }

    @Override
    public String getDoc() {
        return "holodos ne vkluchat";
    }

    @Override
    protected void onTransition(Transition<RefrigeratorState, RefrigeratorEvent> transition, Message<RefrigeratorEvent> message) {
        if (message.getEvent().equals(RefrigeratorEvent.CHANGE_TEMPERATURE)) {
            int target = (int) message.getData().get("target");
            if (target < getData().getCurrentTemperature()) {
                scheduler.schedule(() -> {
                    getData().setCurrentTemperature(targetTemperature);
                    onMessage(new Message<>(RefrigeratorEvent.TURN_ON, Collections.emptyMap()));
                }, 1, TimeUnit.SECONDS);
                System.out.println("Cooling to " + target);
            } else {
                System.out.println("Heating to " + target);
            }
        }
    }
}
