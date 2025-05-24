package model;

public class DiscountTurRetur implements DiscountPolicy{
    private double procent=0.05;

    public double getProcent() {
        return procent;
    }

    public void setProcent(double procent) {
        this.procent = procent;
    }

    @Override
    public double calculeazaDiscount(Rezervare rezervare, double pretbaza){

        if (rezervare.getZbor_return() == null) {
            return pretbaza; // Fără reducere
        }

        double discountAplicat = (rezervare.getProcentCustom() == null) ? procent : rezervare.getProcentCustom();
        return pretbaza - (pretbaza * discountAplicat);
    }
}
