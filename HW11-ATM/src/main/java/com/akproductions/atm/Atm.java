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

    public void putMoney(Map<Cupure, Integer> cupures) throws Exception {
        Map<Integer, Integer> preparedCells = new TreeMap<Integer, Integer>();

        for (Map.Entry<Cupure, Integer> cupuresOfSameNominal : cupures.entrySet()) {
            boolean foundNominal = false;
            for (int i = 0; i < cells.length; i++) {
                if (cupuresOfSameNominal.getKey().getNominal() == this.cells[i].getNominal()) {
                    preparedCells.put(cupuresOfSameNominal.getKey().getNominal(), cupuresOfSameNominal.getValue());
                    foundNominal = true;
                }
            }
            if (!(foundNominal)) {
                throw new Exception("Atm action - Unrecognized cupure with nominal " + cupuresOfSameNominal.getKey().getNominal() + " !!!");
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

    public Map<Cupure, Integer> getMoney(long summa) throws Exception {

        Map<Cupure, Integer> cupuresToGet = new HashMap<Cupure, Integer>();
        long diffSumma = summa;

        for (Map.Entry<Integer, Integer> cell : atmCellsSortedByNominal(Collections.reverseOrder()).entrySet()) {
            int castNominal = (int) Math.floor(diffSumma / cell.getKey());
            if (castNominal > 0 && castNominal <= cell.getValue()) {
                cupuresToGet.put(new Cupure(cell.getKey()), castNominal);
                diffSumma -= cell.getKey() * castNominal;
            }
        }
        if (diffSumma == 0) {
            return extractMoneyFromAtm(cupuresToGet);
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

    private Map<Cupure, Integer>  extractMoneyFromAtm(Map<Cupure, Integer> cupuresToGet) throws Exception {
        for (Map.Entry<Cupure, Integer> mapEntry : cupuresToGet.entrySet()) {
            for (int i = 0; i < cells.length; i++) {
                if (mapEntry.getKey().getNominal() == this.cells[i].getNominal()) {
                    this.cells[i].subCount(mapEntry.getValue());
                }
            }
        }
        return cupuresToGet;
    }

    public long getBalance() {
        long balance = 0;
        for (int i = 0; i < cells.length; i++) {
            balance += this.cells[i].getNominal() * this.cells[i].getCount();
        }
        return balance;
    }

}
