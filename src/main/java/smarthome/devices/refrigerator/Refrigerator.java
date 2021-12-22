package smarthome.devices.refrigerator;

import smarthome.devices.Device;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class Refrigerator extends StateMachine<RefrigeratorState, RefrigeratorEvent> implements Device<RefrigeratorData> {
    private Scheduler scheduler =Scheduler.getInstance();
    private String id;
    private RefrigeratorData refrigeratorData = new RefrigeratorData();


    public Refrigerator() {
        super(RefrigeratorState.OFF);
        addTransition(new Transition<>(RefrigeratorState.OFF, RefrigeratorState.ON, RefrigeratorEvent.TURN_ON));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorEvent.CHANGE_TEMPERATURE));
        addTransition(new Transition<>(RefrigeratorState.COOLING, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.HEATING, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorState.COOLING, RefrigeratorEvent.COOL));
        addTransition(new Transition<>(RefrigeratorState.CHANGE_TEMPERATURE, RefrigeratorState.HEATING, RefrigeratorEvent.HEAT));
        addTransition(new Transition<>(RefrigeratorState.COOLING, RefrigeratorState.ON, RefrigeratorEvent.TURN_ON));
        addTransition(new Transition<>(RefrigeratorState.HEATING, RefrigeratorState.ON, RefrigeratorEvent.TURN_ON));
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
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(RefrigeratorData data) {
        this.refrigeratorData = data;
    }

    @Override
    protected void onEnter(RefrigeratorState currentState) {
        if (currentState.equals(RefrigeratorState.CHANGE_TEMPERATURE)) {
            int target = (Integer) getMessageData().get("target");
            changeTemperature(target);
        } else if (currentState.equals(RefrigeratorState.COOLING) || currentState.equals(RefrigeratorState.HEATING)) {
            int changeTemp = Math.abs(refrigeratorData.getTargetTemperature() - getData().getCurrentTemperature());
            setCurrentTask(scheduler.schedule(() -> {
                getData().setCurrentTemperature(refrigeratorData.getCurrentTemperature());
                onMessage(Message.toDevice(RefrigeratorEvent.TURN_ON,null));
            }, 1*changeTemp, TimeUnit.HOURS));
        }
    }

    private void changeTemperature(int target) {
        refrigeratorData.setTargetTemperature(target);
        if (target < this.getData().getCurrentTemperature()) {
            onMessage(Message.toDevice(RefrigeratorEvent.COOL,null));
            System.out.println("Cooling to " + target + "C");
        } else {
            onMessage(Message.toDevice(RefrigeratorEvent.HEAT,null));
            System.out.println("Heating to " + target + "C");
        }
    }

    @Override
    public String toString() {
        return "Refrigerator{" +
                "id='" + id + '\'' +
                '}';
    }
}
