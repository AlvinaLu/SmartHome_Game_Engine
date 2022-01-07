package smarthome.devices.waterconsumer;

import smarthome.devices.Device;
import smarthome.devices.washing_machine.WashingMachineData;
import smarthome.devices.washing_machine.WashingMachineEvent;
import smarthome.devices.washing_machine.WashingMachineState;
import smarthome.servises.Scheduler;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class WaterConsumer extends StateMachine<WaterConsumerState, WaterConsumerEvent> implements Device<WaterConsumerData> {
    private final Scheduler scheduler = Scheduler.getInstance();
    private String id;
    private WaterConsumerData waterConsumerData = new WaterConsumerData();

    public WaterConsumer() {
        super(WaterConsumerState.OFF);
        addTransition(new Transition<>(WaterConsumerState.OFF, WaterConsumerState.COLD_WATER, WaterConsumerEvent.TURN_ON_COLD_WATER));
        addTransition(new Transition<>(WaterConsumerState.COLD_WATER, WaterConsumerState.OFF, WaterConsumerEvent.TURN_OFF));

        addTransition(new Transition<>(WaterConsumerState.OFF, WaterConsumerState.WARM_WATER, WaterConsumerEvent.TURN_ON_WARM_WATER));
        addTransition(new Transition<>(WaterConsumerState.WARM_WATER, WaterConsumerState.OFF, WaterConsumerEvent.TURN_OFF));

        addTransition(new Transition<>(WaterConsumerState.OFF, WaterConsumerState.HOT_WATER, WaterConsumerEvent.TURN_ON_HOT_WATER));
        addTransition(new Transition<>(WaterConsumerState.HOT_WATER, WaterConsumerState.OFF, WaterConsumerEvent.TURN_OFF));
    }

    @Override
    protected void onEnter(WaterConsumerState currentState) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public WaterConsumerEvent toEvent(String name) {
        return WaterConsumerEvent.valueOf(name);
    }

    @Override
    public WaterConsumerData getData() {
        return waterConsumerData;
    }

    @Override
    public String getDoc() {
        return "WaterConsumer";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(WaterConsumerData data) {
        this.waterConsumerData = data;
    }

    @Override
    public String toString() {
        return "WaterConsumer{" +
                "id='" + id + '\'' +
                '}';
    }
}
