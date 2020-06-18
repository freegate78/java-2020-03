package com.akproductions.atm;

import com.akproductions.cell.Cell;

public class AtmService {
    public static void main(String[] args) throws Exception {
        Integer [] nominals = new Integer[] {1,2,5,10,20,50,100,200,500,1000,2000,5000};
        Integer [] counts = new Integer[] {100,100,100,100,100,100,100,100,100,100,100,100};
        Cell [] cells = new Cell[nominals.length];
        for (int i=0;i<nominals.length;i++){
            cells[i]=new Cell();
            cells[i].setCount(counts[i]);
            cells[i].setNominal(nominals[i]);
        }

        Atm atm = new Atm(cells);
        System.out.println(atm.getBalance());
        try {
            Cell[] cells1 = atm.get_money(23435);
            for (int i = 0; i < cells1.length; i++) {
                System.out.println("get cupure with nominal " + cells1[i].getNominal() + ", amount " + cells1[i].getCount() + " pcs.");
            }
        } catch (Exception exp) {
            System.out.println(exp.fillInStackTrace());
        }

        System.out.println(atm.getBalance());

        try {
            Cell[] cells1 = atm.get_money(23789985);
            for (int i = 0; i < cells1.length; i++) {
                System.out.println("get cupure with nominal " + cells1[i].getNominal() + ", amount " + cells1[i].getCount() + " pcs.");
            }
        } catch (Exception exp) {
            System.out.println(exp.fillInStackTrace());
        }

            System.out.println(atm.getBalance());

        try {
            Integer [] nominals_load = new Integer[] {1,2,5,10,20,50,100,200,500,1000,2000,5000};
            Integer [] counts_load = new Integer[] {10,1,1,2,3,4,2,8,7,8,3,2};
            Cell [] cells3 = new Cell[nominals_load.length];
            for (int i=0;i<nominals_load.length;i++){
                cells3[i]=new Cell();
                cells3[i].setCount(counts_load[i]);
                cells3[i].setNominal(nominals_load[i]);
            }
            for (int i = 0; i < cells3.length; i++) {
                System.out.println("put cupure with nominal " + cells3[i].getNominal() + ", amount " + cells3[i].getCount() + " pcs.");
            }

            atm.put_money(cells3);
        } catch (Exception exp) {
            System.out.println(exp.fillInStackTrace());
        }

        System.out.println(atm.getBalance());

        try {
            Integer [] nominals_load = new Integer[] {1,2,5,10,20,50,100,200,500,1000,2000,6000};
            Integer [] counts_load = new Integer[] {10,1,1,2,3,4,2,8,7,8,3,2};
            Cell [] cells3 = new Cell[nominals_load.length];
            for (int i=0;i<nominals_load.length;i++){
                cells3[i]=new Cell();
                cells3[i].setCount(counts_load[i]);
                cells3[i].setNominal(nominals_load[i]);
            }
            for (int i = 0; i < cells3.length; i++) {
                System.out.println("put cupure with nominal " + cells3[i].getNominal() + ", amount " + cells3[i].getCount() + " pcs.");
            }

            atm.put_money(cells3);
        } catch (Exception exp) {
            System.out.println(exp.fillInStackTrace());
        }

        System.out.println(atm.getBalance());



    }
}
