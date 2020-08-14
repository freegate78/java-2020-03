package depatm.atmdep;

import depatm.cell.Cell;
import depatm.cupure.Cupure;

public class AtmLoad extends AtmProcessor {

    @Override
    protected void processInternal(Application application) throws Exception {


        application.getAtmRecord().forEach(atm -> {
            try {
                int initialCupuresCount = (int)(Math.random()*100);
                Cell[] cells = new Cell[Cupure.Nominal.values().length];
                for (int i = 0; i < Cupure.Nominal.values().length; i++) {
                    cells[i] = new Cell(Cupure.Nominal.values()[i].getNominal(), initialCupuresCount);
                }
                atm.loadMoney(cells);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        application.addHistoryRecord("Банкоматы загружены");
    }

    @Override
    public String getProcessorName() {
        return "Загрузка банкоматов...";
    }
}
