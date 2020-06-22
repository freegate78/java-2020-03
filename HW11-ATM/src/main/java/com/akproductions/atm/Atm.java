package com.akproductions.atm;

import com.akproductions.cell.Cell;

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

    public void putMoney(Cell[] cellsPut) throws Exception {
        Map<Integer, Integer> preparedCells = new HashMap<Integer, Integer>();

        for (int i = 0; i < cellsPut.length; i++) {
            int castNominal = 0;
            for (int j = 0; j < cells.length; j++) {
                if (cells[j].getNominal() == cellsPut[i].getNominal()) {
                    preparedCells.put(this.cells[j].getNominal(), cellsPut[i].getCount());
                    castNominal++;
                }
            }
            if (castNominal == 0) {
                throw new Exception("Atm action - Unrecognized cupure with nominal " + cellsPut[i].getNominal() + " !!!");
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

    public Cell[] getMoney(long summa) throws Exception {

        Map<Integer, Integer> preparedCells = getMoneysCells(summa);

        if (preparedCells.size() > 0) {
            return getCellsFromHash(preparedCells);
        }

        throw new Exception("Atm action - Cannot give this amount with my cupures!!!");
    }

    public long getBalance() {
        long balance = 0;
        for (int i = 0; i < cells.length; i++) {
            balance += this.cells[i].getNominal() * this.cells[i].getCount();
        }
        return balance;
    }

    private List<Map.Entry<Integer, Integer>> sort(Map<Integer, Integer> map, String order) {
        List<Map.Entry<Integer, Integer>> toSort = new ArrayList<>();
        for (Map.Entry<Integer, Integer> cell : map.entrySet()) {
            toSort.add(cell);
        }
        if (order.equalsIgnoreCase("bigFirst")) {
            toSort.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
        } else
            toSort.sort(Map.Entry.comparingByKey());

        return toSort;
    }

    private Map<Integer, Integer> atmCellsAsHash() {
        Map<Integer, Integer> innerCells = new HashMap<Integer, Integer>();
        for (int i = cells.length - 1; i >= 0; i--) {
            innerCells.put(this.cells[i].getNominal(), this.cells[i].getCount());
        }
        return innerCells;

    }

    private Map<Integer, Integer> getMoneysCells(long summa) {
        Map<Integer, Integer> preparedCells = new HashMap<Integer, Integer>();
        long diffSumma = summa;

        for (Map.Entry<Integer, Integer> cell : sort(atmCellsAsHash(), "bigFirst")) {
            int castNominal = (int) Math.floor(diffSumma / cell.getKey());
            if (castNominal > 0 && castNominal <= cell.getValue()) {
                preparedCells.put(cell.getKey(), castNominal);
                diffSumma -= cell.getKey() * castNominal;
            }
        }
        if (diffSumma == 0) return preparedCells;
        else return new HashMap<Integer, Integer>();

    }

    private Cell[] getCellsFromHash(Map<Integer, Integer> preparedCells) throws Exception {
        Cell[] tmpCells = new Cell[preparedCells.size()];
        int index = 0;
        for (Map.Entry<Integer, Integer> mapEntry : preparedCells.entrySet()) {
            tmpCells[index] = new Cell(mapEntry.getKey(), mapEntry.getValue());
            for (int i = 0; i < cells.length; i++) {
                if (mapEntry.getKey() == this.cells[i].getNominal()) {
                    this.cells[i].subCount(mapEntry.getValue());
                }
            }
            index++;
        }
        return tmpCells;
    }
}
