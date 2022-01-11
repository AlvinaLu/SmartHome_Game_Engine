package smarthome.devices.dishwasher;

import smarthome.Dispatcher;
import smarthome.devices.Device;
import smarthome.servises.Scheduler;
import smarthome.statemachine.Message;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.concurrent.TimeUnit;

public class Dishwasher extends StateMachine<DishwasherState, DishwasherEvent> implements Device<DishwasherData> {
    private Scheduler scheduler =Scheduler.getInstance();
    private String id;
    private DishwasherData dishwasherData = new DishwasherData();


    public Dishwasher() {
        super(DishwasherState.OFF);
        addTransition(new Transition<>(DishwasherState.OFF, DishwasherState.ON, DishwasherEvent.TURN_ON));
        addTransition(new Transition<>(DishwasherState.ON, DishwasherState.OFF, DishwasherEvent.TURN_OFF));
        addTransition(new Transition<>(DishwasherState.ON, DishwasherState.ECO, DishwasherEvent.ECO));
        addTransition(new Transition<>(DishwasherState.ON, DishwasherState.INTENSIVE, DishwasherEvent.INTENSIVE));
        addTransition(new Transition<>(DishwasherState.ON, DishwasherState.GLASS, DishwasherEvent.GLASS));
        addTransition(new Transition<>(DishwasherState.ECO, DishwasherState.ON, DishwasherEvent.TURN_ON));
        addTransition(new Transition<>(DishwasherState.INTENSIVE, DishwasherState.ON, DishwasherEvent.TURN_ON));
        addTransition(new Transition<>(DishwasherState.GLASS, DishwasherState.ON, DishwasherEvent.TURN_ON));
        addTransition(new Transition<>(DishwasherState.ECO, DishwasherState.OFF, DishwasherEvent.TURN_OFF));
        addTransition(new Transition<>(DishwasherState.INTENSIVE, DishwasherState.OFF, DishwasherEvent.TURN_OFF));
        addTransition(new Transition<>(DishwasherState.GLASS, DishwasherState.OFF, DishwasherEvent.TURN_OFF));


    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DishwasherEvent toEvent(String name) {
        return DishwasherEvent.valueOf(name);
    }

    @Override
    public DishwasherData getData() {
        return dishwasherData;
    }

    @Override
    public String getDoc() {
        return "Dishwasher";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(DishwasherData data) {
        this.dishwasherData = data;
    }

    @Override
    protected void onEnter(DishwasherState currentState) {
        if (currentState.equals(DishwasherState.ECO)){
            System.out.println(this + ": Dishwasher program is eco ");
            setCurrentTask(scheduler.schedule(() -> {
                System.out.println(this + ": Dishwasher program is finished ");
                Dispatcher.getInstance().sendMessage(Message.toDevice(DishwasherEvent.TURN_ON,id));
            }, 50, TimeUnit.MINUTES));
        }
        if (currentState.equals(DishwasherState.INTENSIVE)){
            System.out.println(this + ": Dishwasher program is intensive ");
            setCurrentTask(scheduler.schedule(() -> {
                System.out.println(this + ": Dishwasher program is finished ");
                Dispatcher.getInstance().sendMessage(Message.toDevice(DishwasherEvent.TURN_ON,id));
            }, 80, TimeUnit.MINUTES));

        }
        if (currentState.equals(DishwasherState.GLASS)){
            System.out.println(this + ": Dishwasher program is glass ");
            setCurrentTask(scheduler.schedule(() -> {
                System.out.println(this + ": Dishwasher program is finished ");
                Dispatcher.getInstance().sendMessage(Message.toDevice(DishwasherEvent.TURN_ON,id));
            }, 40, TimeUnit.MINUTES));
        }
    }


    @Override
    public String toString() {
        return "Dishwasher{" +
                "id='" + id + '\'' +
                '}';
    }
}
