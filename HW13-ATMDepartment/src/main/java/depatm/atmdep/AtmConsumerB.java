package depatm.atmdep;

public class AtmConsumerB {

    private final AtmListener listener = data ->
    {
        data.getAtmRecord().forEach(atm -> {
            int summa= (int)(Math.random()*5000);
            System.out.println("AtmConsumerB get money from " + atm.getAtmName() + ", summa "+summa);
            try {
                System.out.println(atm.getMoney(summa));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("AtmConsumerB balance:" + atm.getAtmName());
            System.out.println(atm.getBalance());

        });
        data.addHistoryRecord("Банкоматы опрошены из наблюдателя B");

    };


    public AtmListener getListener() {
        return listener;
    }

}
