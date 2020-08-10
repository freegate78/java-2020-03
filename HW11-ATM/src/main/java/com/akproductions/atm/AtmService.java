package com.akproductions.atm;

import com.akproductions.cell.Cell;
import com.akproductions.cupure.Cupure;

import java.util.HashMap;
import java.util.Map;

public class AtmService {
    public static void main(String[] args) throws Exception {
        int initialCupuresCount = 100;
        Cell[] cells = new Cell[Cupure.Nominal.values().length];
        for (int i = 0; i < Cupure.Nominal.values().length; i++) {
            cells[i] = new Cell(Cupure.Nominal.values()[i].getNominal(), initialCupuresCount);
        }
        Atm atm = new Atm(cells);
        System.out.println(atm.getBalance());
        getMoney(atm, 23435);
        getMoney(atm, 23789985);

        Integer[] nominalsLoad = new Integer[]{1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
        Integer[] countsLoad = new Integer[]{10, 1, 1, 2, 3, 4, 2, 8, 7, 8, 3, 2};
        putMoney(atm, nominalsLoad, countsLoad);
        nominalsLoad[1] = 666;
        putMoney(atm, nominalsLoad, countsLoad);
    }

    private static void getMoney(Atm atm, int summToGet) {
        System.out.println("get "+summToGet+":");
        try {
            Map<Cupure, Integer> cupuresToGet = atm.getMoney(summToGet);
            cupuresToGet.forEach((key, value) -> {System.out.println("Cupure : " + key + ", Count : " + value);});

        } catch (Exception exp) {
            System.out.println(exp.fillInStackTrace());
        }

        System.out.println(atm.getBalance());

    }

    private static void putMoney(Atm atm, Integer[] nominalsLoad, Integer[] countsLoad) {
        System.out.println("put:");
        try {
            Map<Cupure, Integer> cupuresToPut = new HashMap<Cupure, Integer>();
            for (int i = 0; i < nominalsLoad.length; i++) {
                cupuresToPut.put(new Cupure(nominalsLoad[i]),countsLoad[i]);
            }

            cupuresToPut.forEach((key, value) -> {System.out.println("Cupure : " + key + ", Count : " + value);});
            atm.putMoney(cupuresToPut);
        } catch (Exception exp) {
            System.out.println(exp.fillInStackTrace());
        }
        System.out.println(atm.getBalance());
    }
}
