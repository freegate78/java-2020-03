package depatm.atmdep;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AtmState {
    private final Application app;

    AtmState(Application app) {
        this.app = app;
    }

    AtmState(AtmState state) {
        //deep cloning via serialize/deserialize
        Gson gson = new GsonBuilder().create();
        this.app = gson.fromJson(gson.toJson(state.getApp()), Application.class);
    }

    Application getApp() {
        return app;

    }

    @Override
    public String toString() {
        return "State{" +
                "app=" + app +
                '}';
    }
}
