package smarthome.devices.refrigerator;

import smarthome.Dispatcher;
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
        addTransition(new Transition<>(RefrigeratorState.HEATING, RefrigeratorState.BROKEN, RefrigeratorEvent.BROKEN));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.BROKEN, RefrigeratorEvent.BROKEN));
        addTransition(new Transition<>(RefrigeratorState.COOLING, RefrigeratorState.BROKEN, RefrigeratorEvent.BROKEN));
        addTransition(new Transition<>(RefrigeratorState.HEATING, RefrigeratorState.BROKEN, RefrigeratorEvent.BROKEN));
        addTransition(new Transition<>(RefrigeratorState.UNFREEZE, RefrigeratorState.BROKEN, RefrigeratorEvent.BROKEN));
        addTransition(new Transition<>(RefrigeratorState.HEATING, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.ON, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.COOLING, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.HEATING, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
        addTransition(new Transition<>(RefrigeratorState.UNFREEZE, RefrigeratorState.OFF, RefrigeratorEvent.TURN_OFF));
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
        return "Refrigerator";
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
        if (currentState.equals(RefrigeratorState.CHANGE_TEMPERATURE) && getMessageData().containsKey("target")) {
            int target = (Integer) getMessageData().get("target");
            changeTemperature(target);
        } else if (currentState.equals(RefrigeratorState.COOLING) || currentState.equals(RefrigeratorState.HEATING)) {
            int changeTemp = Math.abs(refrigeratorData.getTargetTemperature() - getData().getCurrentTemperature());
            setCurrentTask(scheduler.schedule(() -> {
                getData().setCurrentTemperature(refrigeratorData.getCurrentTemperature());
                Dispatcher.getInstance().sendMessage(Message.toDevice(RefrigeratorEvent.TURN_ON,id));
            }, 1*changeTemp, TimeUnit.HOURS));
        }
    }

    private void changeTemperature(int target) {
        refrigeratorData.setTargetTemperature(target);
        if (target < this.getData().getCurrentTemperature()) {
            onMessage(Message.toDevice(RefrigeratorEvent.COOL,null));
            System.out.println(this + ": cooling to " + target + "\u00B0" +"C");
        } else {
            onMessage(Message.toDevice(RefrigeratorEvent.HEAT,null));
            System.out.println(this + ": heating to " + target + "\u00B0" +"C");
        }
    }
    @Override
    public boolean isVital(){
        return true;
    }

    @Override
    public String toString() {
        return "Refrigerator{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public RefrigeratorEvent toEvent(String name) {
        return RefrigeratorEvent.valueOf(name);
    }
}
