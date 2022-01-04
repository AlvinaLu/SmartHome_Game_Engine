package smarthome.devices.washing_machine;
import smarthome.devices.Device;
import smarthome.servises.Scheduler;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class WashingMachine extends StateMachine<WashingMachineState, WashingMachineEvent> implements Device<WashingMachineData> {
    private final Scheduler scheduler = Scheduler.getInstance();
    private String id;
    private WashingMachineData washingMachineData = new WashingMachineData();


    public WashingMachine() {
        super(WashingMachineState.OFF);
        addTransition(new Transition<>(WashingMachineState.OFF, WashingMachineState.ON, WashingMachineEvent.TURN_ON));
        addTransition(new Transition<>(WashingMachineState.ON, WashingMachineState.OFF, WashingMachineEvent.TURN_OFF));
        addTransition(new Transition<>(WashingMachineState.ON, WashingMachineState.WOOL, WashingMachineEvent.WOOL));
        addTransition(new Transition<>(WashingMachineState.ON, WashingMachineState.QUICK_WASH, WashingMachineEvent.QUICK_WASH));
        addTransition(new Transition<>(WashingMachineState.ON, WashingMachineState.ANTI_ALLERGY, WashingMachineEvent.ANTI_ALLERGY));
        addTransition(new Transition<>(WashingMachineState.ANTI_ALLERGY, WashingMachineState.ON, WashingMachineEvent.TURN_ON));
        addTransition(new Transition<>(WashingMachineState.QUICK_WASH, WashingMachineState.ON, WashingMachineEvent.TURN_ON));
        addTransition(new Transition<>(WashingMachineState.QUICK_WASH, WashingMachineState.ON, WashingMachineEvent.TURN_ON));
        addTransition(new Transition<>(WashingMachineState.ANTI_ALLERGY, WashingMachineState.OFF, WashingMachineEvent.TURN_OFF));
        addTransition(new Transition<>(WashingMachineState.QUICK_WASH, WashingMachineState.OFF, WashingMachineEvent.TURN_OFF));
        addTransition(new Transition<>(WashingMachineState.QUICK_WASH, WashingMachineState.OFF, WashingMachineEvent.TURN_OFF));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public WashingMachineEvent toEvent(String name) {
        return WashingMachineEvent.valueOf(name);
    }

    @Override
    public WashingMachineData getData() {
        return washingMachineData;
    }

    public void setData(WashingMachineData dishwasherData) {
        this.washingMachineData = dishwasherData;
    }

    @Override
    public String getDoc() {
        return "WashingMachine";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    @Override
    protected void onEnter(WashingMachineState currentState) {

    }


    @Override
    public String toString() {
        return "WashingMachine{" +
                "id='" + id + '\'' +
                '}';
    }
}

