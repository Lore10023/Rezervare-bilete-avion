package model;

import java.util.Map;

public abstract class Zbor {
    protected String codCursa;
    protected String model;
    protected String orasPlecare;
    protected String orasDestinatie;
    protected Map<Clasa, Integer> locuriDisponibile; // cheie = clasă ("business", "economy", etc.), valoare = nr locuri
    protected Map<Clasa, Double> tarife; // cheie = clasă, valoare = tarif

    public Zbor(String codCursa, String model, String orasPlecare, String orasDestinatie, Map<Clasa, Integer> locuriDisponibile, Map<Clasa, Double> tarife) {
        this.codCursa = codCursa;
        this.model = model;
        this.orasPlecare = orasPlecare;
        this.orasDestinatie = orasDestinatie;
        this.locuriDisponibile = locuriDisponibile;
        this.tarife = tarife;
    }

    //public abstract boolean esteDisponibil();

    public double getTarif(Clasa clasa) {
        return tarife.get(clasa);
    }

    public int getLocuridisponibile(Clasa clasa) {
        return locuriDisponibile.get(clasa);
    }

    public String getCodCursa() {
        return codCursa;
    }

    public void setCodCursa(String codCursa) {
        this.codCursa = codCursa;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrasPlecare() {
        return orasPlecare;
    }

    public void setOrasPlecare(String orasPlecare) {
        this.orasPlecare = orasPlecare;
    }

    public String getOrasDestinatie() {
        return orasDestinatie;
    }

    public void setOrasDestinatie(String orasDestinatie) {
        this.orasDestinatie = orasDestinatie;
    }

    public Map<Clasa, Integer> getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public void setLocuriDisponibile(Map<Clasa, Integer> locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    public Map<Clasa, Double> getTarife() {
        return tarife;
    }

    public void setTarife(Map<Clasa, Double> tarife) {
        this.tarife = tarife;
    }

    @Override
    public String toString() {
        return "Zbor{" +
                "codCursa='" + codCursa + '\'' +
                ", model='" + model + '\'' +
                ", orasPlecare='" + orasPlecare + '\'' +
                ", orasDestinatie='" + orasDestinatie + '\'' +
                ", locuriDisponibile=" + locuriDisponibile +
                ", tarife=" + tarife +
                '}';
    }
}
