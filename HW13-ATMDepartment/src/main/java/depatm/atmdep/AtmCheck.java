package depatm.atmdep;

public class AtmCheck extends AtmProcessor {

    @Override
    protected void processInternal(Application application) {

        application.getAtmRecord().forEach(atm -> {
            System.out.println(atm.getBalance());
        });

        application.addHistoryRecord("Банкоматы проверены");
    }

    @Override
    public String getProcessorName() {
        return "Проверка банкоматов...";
    }
}
