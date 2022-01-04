package smarthome.devices.electricity_generator;

import smarthome.devices.Device;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

public class Generator extends StateMachine<GeneratorState, GeneratorEvent> implements Device<GeneratorData> {
    private String id;
    private GeneratorData data;

    public Generator() {
        super(GeneratorState.OFF);
        addTransition(new Transition<>(GeneratorState.OFF, GeneratorState.ON, GeneratorEvent.TURN_ON));
        addTransition(new Transition<>(GeneratorState.ON, GeneratorState.OFF, GeneratorEvent.TURN_OFF));
    }

    @Override
    protected void onEnter(GeneratorState currentState) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public GeneratorEvent toEvent(String name) {
        return GeneratorEvent.valueOf(name);
    }

    @Override
    public GeneratorData getData() {
        return data;
    }

    @Override
    public String getDoc() {
        return "Generator";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setData(GeneratorData data) {
        this.data = data;
    }

    @Override
    public boolean isVital() {
        return true;
    }

    @Override
    public String toString() {
        return "Generator{" +
                "id='" + id + '\'' +
                '}';
    }
}
