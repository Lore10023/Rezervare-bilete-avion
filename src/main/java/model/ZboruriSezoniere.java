package model;

import java.time.LocalDate;
import java.util.Map;

public class ZboruriSezoniere extends Zbor{
    private LocalDate perioada_inceput;
    private LocalDate perioada_finala;

    public ZboruriSezoniere(String codCursa, String model, String orasPlecare, String orasDestinatie, Map<Clasa, Integer> locuriDisponibile, Map<Clasa, Double> tarife, LocalDate perioada_inceput, LocalDate perioada_finala) {
        super(codCursa, model, orasPlecare, orasDestinatie, locuriDisponibile, tarife);
        this.perioada_inceput = perioada_inceput;
        this.perioada_finala = perioada_finala;
    }

//    @Override
//    public boolean esteDisponibil() {
//        LocalDate azi = LocalDate.now();
//        return azi.isAfter(perioada_inceput.minusDays(1)) && azi.isBefore(perioada_finala.plusDays(1));
//    }

    public LocalDate getPerioada_inceput() {
        return perioada_inceput;
    }

    public void setPerioada_inceput(LocalDate perioada_inceput) {
        this.perioada_inceput = perioada_inceput;
    }

    public LocalDate getPerioada_finala() {
        return perioada_finala;
    }

    public void setPerioada_finala(LocalDate perioada_finala) {
        this.perioada_finala = perioada_finala;
    }

    @Override
    public String toString() {
        return super.toString()+"\nZboruriSezoniere{" +
                "perioada_inceput=" + perioada_inceput +
                ", perioada_finala=" + perioada_finala +
                '}';
    }
}
