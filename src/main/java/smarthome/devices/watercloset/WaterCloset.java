package smarthome.devices.watercloset;

import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class WaterCloset extends StateMachine<WaterClosetState, WaterClosetEvent> implements Device<WaterClosetData> {
    private final Scheduler scheduler = Scheduler.getInstance();
    private String id;
    private WaterClosetData waterClosetData = new WaterClosetData();

    public WaterCloset(){
        super(WaterClosetState.OFF);
        addTransition(new Transition<>(WaterClosetState.OFF, WaterClosetState.ON, WaterClosetEvent.TURN_ON));
        addTransition(new Transition<>(WaterClosetState.ON, WaterClosetState.OFF, WaterClosetEvent.TURN_OFF));

    }

    @Override
    protected void onEnter(WaterClosetState currentState) {
        if (currentState.equals(WaterClosetState.ON)) {
            setCurrentTask(scheduler.schedule(() -> {
                Dispatcher.getInstance().sendMessage(Message.toDevice(WaterClosetEvent.TURN_OFF, id));
            }, 1, TimeUnit.MINUTES));
        }

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public WaterClosetEvent toEvent(String name) {
        return WaterClosetEvent.valueOf(name);
    }

    @Override
    public WaterClosetData getData() {
        return waterClosetData;
    }

    @Override
    public String getDoc() {
        return "WaterCloset";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(WaterClosetData data) {
        this.waterClosetData = data;

    }

    @Override
    public String toString() {
        return "WaterCloset{" +
                "id='" + id + '\'' +
                '}';
    }
}
