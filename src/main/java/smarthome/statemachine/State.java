package smarthome.statemachine;

import java.util.HashSet;
import java.util.Set;

public class State<NAME, EVENT extends SmEvent> {
    private NAME name;
    private Set<Transition<NAME,EVENT>> transitions = new HashSet<>();

    public State(NAME name) {
        this.name = name;
    }

    public void addTransition(Transition<NAME,EVENT> transition) {
        transitions.add(transition);
    }
}
