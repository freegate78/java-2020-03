package depatm.atmdep;

public class AtmConsumerA implements AtmListener{
        @Override
        public void onUpdate(Application data) {

            data.getAtmRecord().forEach(atm -> {
                System.out.println("AtmConsumerA data:" + atm.getAtmName());
                System.out.println(atm.getBalance());
            });
            data.addHistoryRecord("Банкоматы опрошены из наблюдателя А");

        }
    }
