package smarthome.devices.air_conditioner;

import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class AirConditioner extends StateMachine<AirConditionerState, AirConditionerEvent> implements Device<AirConditionerData> {

    private Scheduler scheduler =Scheduler.getInstance();
    private String id;
    private AirConditionerData airConditionerData = new AirConditionerData();

    public AirConditioner() {
        super(AirConditionerState.OFF);
        addTransition(new Transition<>(AirConditionerState.OFF, AirConditionerState.ON, AirConditionerEvent.TURN_ON));
        addTransition(new Transition<>(AirConditionerState.ON, AirConditionerState.OFF, AirConditionerEvent.TURN_OFF));
        addTransition(new Transition<>(AirConditionerState.ON, AirConditionerState.CHANGE_TEMPERATURE, AirConditionerEvent.CHANGE_TEMPERATURE));
        addTransition(new Transition<>(AirConditionerState.COOLING, AirConditionerState.OFF, AirConditionerEvent.TURN_OFF));
        addTransition(new Transition<>(AirConditionerState.HEATING, AirConditionerState.OFF, AirConditionerEvent.TURN_OFF));
        addTransition(new Transition<>(AirConditionerState.CHANGE_TEMPERATURE, AirConditionerState.COOLING, AirConditionerEvent.COOL));
        addTransition(new Transition<>(AirConditionerState.CHANGE_TEMPERATURE, AirConditionerState.HEATING, AirConditionerEvent.HEAT));
        addTransition(new Transition<>(AirConditionerState.ON, AirConditionerState.QUIET_MODE, AirConditionerEvent.QUIET_MODE));
        addTransition(new Transition<>(AirConditionerState.COOLING, AirConditionerState.QUIET_MODE, AirConditionerEvent.QUIET_MODE));
        addTransition(new Transition<>(AirConditionerState.HEATING, AirConditionerState.QUIET_MODE, AirConditionerEvent.QUIET_MODE));
        addTransition(new Transition<>(AirConditionerState.QUIET_MODE, AirConditionerState.OFF, AirConditionerEvent.TURN_OFF));
        addTransition(new Transition<>(AirConditionerState.QUIET_MODE, AirConditionerState.ON, AirConditionerEvent.TURN_ON));


    }

    @Override
    protected void onEnter(AirConditionerState currentState) {
        if (currentState.equals(AirConditionerState.CHANGE_TEMPERATURE)) {
            int target = (Integer) getMessageData().get("target");
            this.airConditionerData.setTargetTemperature(target);

            if (target < this.getData().getCurrentTemperature()) {
                onMessage(Message.toDevice(AirConditionerEvent.COOL,null));
                System.out.println(this + ": cooling to " + target + "\u00B0" +"C");
            } else {
                onMessage(Message.toDevice(AirConditionerEvent.HEAT,null));
                System.out.println(this + ": heating to " + target + "\u00B0" +"C");
            }
        }
        else if (currentState.equals(AirConditionerState.COOLING) || currentState.equals(AirConditionerState.HEATING)) {
            int changeTemp = Math.abs(this.getData().getTargetTemperature() - this.getData().getCurrentTemperature());
            setCurrentTask(scheduler.schedule(() -> {
                this.airConditionerData.setCurrentTemperature(this.getData().getCurrentTemperature());
                Dispatcher.getInstance().sendMessage(Message.toDevice(AirConditionerEvent.TURN_ON,id));
            }, 1*changeTemp, TimeUnit.HOURS));
        }

    }

    private void changeTemperature(int target) {
        this.airConditionerData.setTargetTemperature(target);

        if (target < this.getData().getCurrentTemperature()) {
            onMessage(Message.toDevice(AirConditionerEvent.COOL,null));
            System.out.println(this + ": cooling to " + target + "\u00B0" +"C");
        } else {
            onMessage(Message.toDevice(AirConditionerEvent.HEAT,null));
            System.out.println(this + ": heating to " + target + "\u00B0" +"C");
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public AirConditionerEvent toEvent(String name) {
        return AirConditionerEvent.valueOf(name);
    }

    @Override
    public AirConditionerData getData() {
        return this.airConditionerData;
    }

    @Override
    public String getDoc() {
        return "AirConditioner";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(AirConditionerData data) {
        this.airConditionerData = data;
    }

    @Override
    public String toString() {
        return "AirConditioner{" +
                "id='" + id + '\'' +
                '}';
    }
}
