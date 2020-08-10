package depatm.atmdep;

import java.util.UUID;

public class AtmResult extends AtmProcessor {

    @Override
    protected void processInternal(Application application) {

        application.getAtmRecord().forEach(atm -> {
            atm.setAtmName(UUID.randomUUID().toString());
        });

        application.addHistoryRecord("Банкоматы готовы к работе");
    }

    @Override
    public String getProcessorName() {
        return "Подготовка к работе банкоматов...";
    }
}
