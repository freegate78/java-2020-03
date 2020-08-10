package depatm.atmdep;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

class AtmOriginator {
    //Фактически, это stack
    private final Deque<AtmMemento> stack = new ArrayDeque<>();

    void saveState(AtmState state) throws IOException, ClassNotFoundException {
        stack.push(new AtmMemento(state));
    }

    AtmState restoreState() {
        return stack.pop().getState();
    }
}
