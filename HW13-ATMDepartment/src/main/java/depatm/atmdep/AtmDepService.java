package depatm.atmdep;

import depatm.atm.Atm;

import java.io.IOException;

public class AtmDepService {
    private static Application appl;
    private static AtmOriginator originator;
    private static AtmState abc;

    public static void main(String[] args) throws Exception {

        chain_of_responsibility_init();
        appl.printHistory();
        memento_atm_save();
        appl.printHistory();
        observe_atm_balances();
        appl.printHistory();
        memento_atm_restore();
        appl.printHistory();
    }

    private static void chain_of_responsibility_init() throws Exception {
        appl = new Application();
        appl.addAtmRecord(new Atm());
        appl.addAtmRecord(new Atm());
        appl.addAtmRecord(new Atm());

        AtmProcessor load = new AtmLoad();
        AtmProcessor check = new AtmCheck();
        AtmProcessor result = new AtmResult();
        load.setNext(check);
        check.setNext(result);

        load.process(appl);
    }

    private static void observe_atm_balances() {
        AtmEventProducer producer = new AtmEventProducer();
        AtmConsumerA consumerA = new AtmConsumerA();
        AtmConsumerB consumerB = new AtmConsumerB();

        producer.addListener(consumerA);
        producer.addListener(consumerB.getListener());

        producer.event(appl);

        producer.removeListener(consumerA);
        producer.removeListener(consumerB.getListener());

    }

    private static void memento_atm_save() throws IOException, ClassNotFoundException {
        {
            originator = new AtmOriginator();

            abc = new AtmState(appl);
            System.out.println("State saved, balance now:");
            appl.atmCheck();

            originator.saveState(abc);
            appl.addHistoryRecord("Банкоматы сохранены");



        }
    }

    private static void memento_atm_restore() {
        System.out.println("  undo changes");

        appl = originator.restoreState().getApp();
        System.out.println("State restored, balance now:");
        appl.addHistoryRecord("Банкоматы восстановлены");
        appl.atmCheck();
    }
}