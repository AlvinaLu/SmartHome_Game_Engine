package smarthome.statemachine;

import java.util.HashSet;
import java.util.Set;

public class StateMachine<NAME, EVENT extends SmEvent> {
    private State<NAME, EVENT> currentState;
    private Set<Transition<NAME,EVENT>> transitions = new HashSet<>();

    public StateMachine(State<NAME, EVENT> currentState) {
        this.currentState = currentState;
    }
}
