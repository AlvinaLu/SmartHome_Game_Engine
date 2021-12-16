package smarthome.statemachine;

public class Transition<NAME, EVENT extends SmEvent> {
    private NAME source;
    private NAME target;
    private EVENT event;

    public Transition(NAME source, NAME target, EVENT event) {
        this.source = source;
        this.target = target;
        this.event = event;
    }

    public NAME getSource() {
        return source;
    }

    public NAME getTarget() {
        return target;
    }

    public EVENT getEvent() {
        return event;
    }
}
