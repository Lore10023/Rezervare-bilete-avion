package model;

import java.time.LocalTime;
import java.util.*;

public class ZboruriRegulate extends Zbor{
    private List<ZiSaptamana> zisaptamana;
    private LocalTime ora;

    public ZboruriRegulate(String codCursa, String model, String orasPlecare, String orasDestinatie, Map<Clasa, Integer> locuriDisponibile, Map<Clasa, Double> tarife, List<ZiSaptamana> zisaptamana, LocalTime ora) {
        super(codCursa, model, orasPlecare, orasDestinatie, locuriDisponibile, tarife);
        this.zisaptamana = zisaptamana;
        this.ora = ora;
    }

//    @Override
//    public boolean esteDisponibil() {
//        return true;
//    }

    public List<ZiSaptamana> getZisaptamana() {
        return zisaptamana;
    }

    public void setZisaptamana(List<ZiSaptamana> zisaptamana) {
        this.zisaptamana = zisaptamana;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    @Override
    public String toString() {
        return super.toString()+"\nZboruriRegulate{" +
                "zisaptamana=" + zisaptamana +
                ", ora=" + ora +
                '}';
    }
}