package com.akproductions.cell;

import com.akproductions.cupure.Cupure;

public class Cell {
    private int nominal;
    private int count;
    private String name;

    public Cell(int nominal, int count) throws Exception {
        try {
            this.nominal = Cupure.Nominal.find(nominal).get().getNominal();
            this.name = Cupure.Nominal.find(nominal).get().name();
        } catch (Exception e) {
            throw new Exception("Cell action - Unrecognized cupure with nominal " + nominal + " !!! " + e.getStackTrace());
        }
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getNominal() {
        return nominal;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void subCount(int count) {
        this.count -= count;
    }

    @Override
    public String toString() {
        return "cupure <"+name+"> with nominal " + this.nominal + ", amount " + this.count + " pcs.";
    }

}
