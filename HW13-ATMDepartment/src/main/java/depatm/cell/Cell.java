package depatm.cell;

import depatm.cupure.Cupure;

public class Cell{
    Cupure cupure;
    int count;
    public Cell(int nominal, int count) throws Exception {
        cupure = new Cupure(nominal);
        this.count=count;
    }

    public int getNominal() {
        return cupure.getNominal();
    }

    public int getCount() {
        return count;
    }


    public void addCount(int count) {
        this.count+=count;
    }

    public void subCount(int count) {
        this.count-=count;
    }
}
