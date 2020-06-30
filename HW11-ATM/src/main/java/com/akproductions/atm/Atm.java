package com.akproductions.atm;

import com.akproductions.cell.Cell;
import com.akproductions.cupure.Cupure;

import java.util.*;

public class Atm {
    private Cell cells[];

    public Atm(Cell[] cellsLoad) throws Exception {
        installCells(cellsLoad);
    }

    private void installCells(Cell[] cellsLoad) throws Exception {
        this.cells = new Cell[cellsLoad.length];
        for (int i = 0; i < this.cells.length; i++) {
            this.cells[i] = new Cell(cellsLoad[i].getNominal(), cellsLoad[i].getCount());
        }
    }

    public void putMoney(Cupure[] cupures) throws Exception {
        Map<Integer, Integer> preparedCells = new HashMap<Integer, Integer>();

        for (int i = 0; i < cupures.length; i++) {
            boolean foundNominal = false;
            for (int j = 0; j < cells.length; j++) {
                if (cells[j].getNominal() == cupures[i].getNominal()) {
                    preparedCells.put(this.cells[j].getNominal(), cupures[i].getCount());
                    foundNominal = true;
                }
            }
            if (!(foundNominal)) {
                throw new Exception("Atm action - Unrecognized cupure with nominal " + cupures[i].getNominal() + " !!!");
            }
        }

        for (Map.Entry<Integer, Integer> mapEntry : preparedCells.entrySet()) {
            for (int i = 0; i < cells.length; i++) {
                if (mapEntry.getKey() == this.cells[i].getNominal()) {
                    this.cells[i].addCount(mapEntry.getValue());
                }
            }
        }
    }

    public Cupure[] getMoney(long summa) throws Exception {

        Map<Integer, Integer> cupuresToGet = new TreeMap<Integer, Integer>();
        long diffSumma = summa;

        for (Map.Entry<Integer, Integer> cell : atmCellsSortedByNominal(Collections.reverseOrder()).entrySet()) {
            int castNominal = (int) Math.floor(diffSumma / cell.getKey());
            if (castNominal > 0 && castNominal <= cell.getValue()) {
                cupuresToGet.put(cell.getKey(), castNominal);
                diffSumma -= cell.getKey() * castNominal;
            }
        }
        if (diffSumma == 0) {
            return exctractMoneyFromAtm(cupuresToGet);
        }

        throw new Exception("Atm action - Cannot give this amount with my cupures!!!");
    }

    private Map<Integer, Integer> atmCellsSortedByNominal(Comparator<Integer> sortOrder) {
        Map<Integer, Integer> innerCells;
        innerCells = new TreeMap<Integer, Integer>(sortOrder);
        for (int i = cells.length - 1; i >= 0; i--) {
            innerCells.put(this.cells[i].getNominal(), this.cells[i].getCount());
        }
        return innerCells;

    }

    private Cupure[] exctractMoneyFromAtm(Map<Integer, Integer> cupuresToGet) throws Exception {
        Cupure[] tmpCupures = new Cupure[cupuresToGet.size()];
        int index = 0;
        for (Map.Entry<Integer, Integer> mapEntry : cupuresToGet.entrySet()) {
            tmpCupures[index] = new Cupure(mapEntry.getKey(), mapEntry.getValue());
            for (int i = 0; i < cells.length; i++) {
                if (mapEntry.getKey() == this.cells[i].getNominal()) {
                    this.cells[i].subCount(mapEntry.getValue());
                }
            }
            index++;
        }
        return tmpCupures;
    }

    public long getBalance() {
        long balance = 0;
        for (int i = 0; i < cells.length; i++) {
            balance += this.cells[i].getNominal() * this.cells[i].getCount();
        }
        return balance;
    }

}
