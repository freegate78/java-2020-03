package com.akproductions.cupure;

import java.util.Optional;

public class Cupure {
    private final Nominal nominal;
    protected int count;

    public int getNominal() {
        return nominal.getNominal();
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return nominal.name();
    }

    public Cupure(int nominal, int count) throws Exception {
        this.count = count;
        try {
            this.nominal = Nominal.find(nominal).get();
        }
        catch (Exception e)
        {throw new Exception("Cupure action - Unrecognized cupure with nominal " + nominal + " !!!" + e.getLocalizedMessage());
        }
    }

    public enum Nominal {
        CUPURE1(1), CUPURE2(2), CUPURE3(5), CUPURE4(10), CUPURE5(20), CUPURE6(50), CUPURE7(100),CUPURE8(200), CUPURE9(500), CUPUREA(1000), CUPUREB(2000), CUPUREC(5000);

        final int nominal;

        Nominal(int i) {
            this.nominal=i;
        }

        public int getNominal() {
            return nominal;
        }

        public static Optional<Nominal> find(int nominal)
        { Nominal[] values = values();
            for (Nominal value : values) { if (value.nominal == nominal)
                return Optional.of(value); }
            return Optional.empty(); }
    }

    @Override
    public String toString() {
        return "cupure <"+nominal.name()+"> with nominal " + this.nominal.getNominal() + ", amount " + this.count + " pcs.";
    }

}
