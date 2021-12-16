package smarthome.statemachine;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class State<NAME, EVENT extends SmEvent> {
    private NAME name;

    public State(NAME name) {
        this.name = name;
    }

    public NAME getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State<?, ?> state = (State<?, ?>) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
