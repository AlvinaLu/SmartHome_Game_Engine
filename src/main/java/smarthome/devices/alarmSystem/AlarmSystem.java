package smarthome.devices.alarmSystem;

import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.devices.air_conditioner.AirConditionerData;
import smarthome.devices.air_conditioner.AirConditionerEvent;
import smarthome.devices.air_conditioner.AirConditionerState;
import smarthome.devices.refrigerator.RefrigeratorState;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class AlarmSystem extends StateMachine<AlarmSystemState, AlarmSystemEvent> implements Device<AlarmSystemData> {

    private Scheduler scheduler = Scheduler.getInstance();
    private String id;
    private AlarmSystemData alarmSystemData = new AlarmSystemData();

    public AlarmSystem() {
        super(AlarmSystemState.ON);
        addTransition(new Transition<>(AlarmSystemState.OFF, AlarmSystemState.ON, AlarmSystemEvent.TURN_ON));
        addTransition(new Transition<>(AlarmSystemState.ON, AlarmSystemState.OFF, AlarmSystemEvent.TURN_OFF));
        addTransition(new Transition<>(AlarmSystemState.ON, AlarmSystemState.ALARM, AlarmSystemEvent.TURN_ALARM));
        addTransition(new Transition<>(AlarmSystemState.ALARM, AlarmSystemState.OFF, AlarmSystemEvent.TURN_OFF));
        addTransition(new Transition<>(AlarmSystemState.ALARM, AlarmSystemState.ON, AlarmSystemEvent.TURN_ON));

    }

    @Override
    protected void onEnter(AlarmSystemState currentState) {
        if (currentState.equals(AlarmSystemState.ALARM)) {
            if (getMessageData().containsKey("fire")) {
                boolean fire = (boolean) getMessageData().get("fire");
                if (fire == true) {
                    System.out.println("ALARM  !!! ALARM !!!  FIRE !!! ALARM  !!! ALARM !!!  FIRE !!!");
                    setCurrentTask(scheduler.schedule(() -> {
                    Dispatcher.getInstance().sendMessage(Message.toDevice(AlarmSystemEvent.TURN_ON,id));
                }, 5, TimeUnit.MINUTES));
                }
            }
            if (getMessageData().containsKey("water") && getMessageData().containsKey("waterLocation")) {
                boolean water = (boolean) getMessageData().get("water");
                String waterLocation = (String) getMessageData().get("waterLocation");
                if (water == true) {
                    System.out.println("ALARM  !!! ALARM !!!  WATER !!! in " + waterLocation);
                    setCurrentTask(scheduler.schedule(() -> {
                        Dispatcher.getInstance().sendMessage(Message.toDevice(AlarmSystemEvent.TURN_ON,id));
                    }, 5, TimeUnit.MINUTES));
                }
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public AlarmSystemEvent toEvent(String name) {
        return AlarmSystemEvent.valueOf(name);
    }

    @Override
    public AlarmSystemData getData() {
        return alarmSystemData;
    }

    @Override
    public String getDoc() {
        return "AlarmSystem";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(AlarmSystemData data) {
        alarmSystemData = data;
    }

    @Override
    public boolean isVital() {
        return true;
    }

    @Override
    public String toString() {
        return "AlarmSystem{" +
                "id='" + id + '\'' +
                '}';
    }
}
