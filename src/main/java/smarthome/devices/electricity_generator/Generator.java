package smarthome.devices.electricity_generator;

import smarthome.devices.Device;
import smarthome.devices.lamp.Lamp;
import smarthome.devices.refrigerator.RefrigeratorState;
import smarthome.servises.IdGenerator;
import smarthome.statemachine.Message;
import smarthome.statemachine.State;
import smarthome.statemachine.StateMachine;
import smarthome.statemachine.Transition;

import java.util.UUID;

public class Generator extends StateMachine<GeneratorState, GeneratorEvent> implements Device<GeneratorData> {
    private String id;

    public Generator() {
        super(new State<>(GeneratorState.OFF));
        addTransition(new Transition<>(GeneratorState.OFF, GeneratorState.ON, GeneratorEvent.TURN_ON));
        addTransition(new Transition<>(GeneratorState.ON, GeneratorState.OFF, GeneratorEvent.TURN_OFF));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public GeneratorData getData() {
        return null;
    }

    @Override
    public String getDoc() {
        return "lamp";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void onTransition(Transition<GeneratorState, GeneratorEvent> transition, Message<GeneratorEvent> message) {

    }
}
