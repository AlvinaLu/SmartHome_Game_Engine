package smarthome.statemachine;

import java.util.HashSet;
import java.util.Set;

public abstract class StateMachine<NAME, EVENT extends SmEvent> {
    private State<NAME, EVENT> currentState;
    private Set<Transition<NAME,EVENT>> transitions = new HashSet<>();

    public StateMachine(State<NAME, EVENT> currentState) {
        this.currentState = currentState;
    }

    public void onMessage(Message<EVENT> message) {
        try {
            Transition<NAME, EVENT> transition = transitions.stream()
                    .filter(it -> it.getSource().equals(currentState.getName()) && it.getEvent().equals(message.getEvent()))
                    .findAny()
                    .orElse(null);

            if (transition==null) {
                System.out.println("transition not found for event "+message.getEvent());
                return;
            }

            onTransition(transition, message);
            System.out.println("Transitioning from " + transition.getSource() + " to " + transition.getTarget());
            currentState = new State<>(transition.getTarget());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    protected abstract void onTransition(Transition<NAME, EVENT> transition, Message<EVENT> message);

    public void addTransition(Transition<NAME,EVENT> transition) {
        transitions.add(transition);
    }
}
