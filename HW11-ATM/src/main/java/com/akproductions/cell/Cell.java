package com.akproductions.cell;

import com.akproductions.cupure.Cupure;

public class Cell extends Cupure{

    public Cell(int nominal, int count) throws Exception {
        super(nominal, count);
    }

    public void addCount(int count) {
        this.count+=count;
    }

    public void subCount(int count) {
        this.count-=count;
    }
}
