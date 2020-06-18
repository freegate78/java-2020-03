package com.akproductions.atm;

import com.akproductions.cell.Cell;

import java.util.HashMap;
import java.util.Map;

public class Atm {
    private Cell cells [];
    public Atm(Cell[] cells_load){
        install_cells(cells_load);
        load_money(cells_load);
    }

    private void install_cells(Cell [] cells_load){
        this.cells = new Cell[cells_load.length];
        for (int i=0;i<this.cells.length;i++){
            this.cells[i]=new Cell();
        }
    }
    private void load_money(Cell[] cells_load)
    {
        for (int i=0;i<cells_load.length;i++){
            this.cells[i].setCount(cells_load[i].getCount());
            this.cells[i].setNominal(cells_load[i].getNominal());
        }
    }

    public void put_money(Cell[] cells_put) throws Exception {
        Map<Integer, Integer> preparedCells = new HashMap(0);

        for (int i =0;i< cells_put.length ; i++) {
            int cast_nominal=0;
            for (int j = 0; j < cells.length; j++) {
                if (cells[j].getNominal() == cells_put[i].getNominal()) {
                    preparedCells.put(this.cells[j].getNominal(), cells_put[i].getCount());
                    cast_nominal++;
                }
            }
            if (cast_nominal == 0) {
                throw new Exception("Unrecognized cupure with nominal "+cells_put[i].getNominal() + " !!!");
            }
        }

            for (Map.Entry<Integer, Integer> mapEntry : preparedCells.entrySet()) {
                for (int i=0;i<cells.length;i++){
                    if(mapEntry.getKey()==this.cells[i].getNominal())
                    {this.cells[i].setCount(this.cells[i].getCount()+mapEntry.getValue());}
                }
            }
    }

    public Cell[] get_money(long summa)throws Exception  {
        Map<Integer, Integer> preparedCells = new HashMap(0);

        long diff_summa = summa;

        do {
            for (int i = cells.length - 1; i >= 0; i--) {
                int cast_nominal = (int) Math.floor(diff_summa / this.cells[i].getNominal());
                if (cast_nominal > 0 && cast_nominal<=this.cells[i].getCount()) {
                    preparedCells.put(this.cells[i].getNominal(), cast_nominal);
                    diff_summa -= this.cells[i].getNominal() * cast_nominal;
                }
            }
            if(preparedCells.size()==0){diff_summa =-1;}
        }while (diff_summa >0);

        if (diff_summa==0){
            Cell[] tmpCells = new Cell[preparedCells.size()];
            for (int i=0;i<tmpCells.length;i++){
                tmpCells[i]=new Cell();
            }

            int index = 0;
            for (Map.Entry<Integer, Integer> mapEntry : preparedCells.entrySet()) {
                tmpCells[index].setNominal(mapEntry.getKey());
                tmpCells[index].setCount(mapEntry.getValue());
                for (int i=0;i<cells.length;i++){
                    if(mapEntry.getKey()==this.cells[i].getNominal())
                    {this.cells[i].setCount(this.cells[i].getCount()-mapEntry.getValue());}
                }
                index++;
            }
            return tmpCells;
        }

        throw new Exception("Cannot give this amount with my cupures!!!");
    }

    public long getBalance() {
        long balance=0;
        for (int i=0;i<cells.length;i++){
            balance+=this.cells[i].getNominal()*this.cells[i].getCount();
        }
        return balance;
    }

}
