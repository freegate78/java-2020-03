package depatm.atmdep;

import java.util.ArrayList;
import java.util.List;

public class AtmEventProducer {

        private final List<AtmListener> listeners = new ArrayList<>();

        void addListener(AtmListener listener) {
            listeners.add(listener);
        }

        void removeListener(AtmListener listener) {
            listeners.remove(listener);
        }

        void event(Application data) {
            listeners.forEach(listener -> listener.onUpdate(data));
        }
    }
