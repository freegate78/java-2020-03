package depatm.atmdep;

import java.io.IOException;

class AtmMemento {
    private final AtmState state;

    AtmMemento(AtmState state) throws IOException, ClassNotFoundException {
        this.state = new AtmState(state);
    }

    AtmState getState() {
        return state;
    }
}