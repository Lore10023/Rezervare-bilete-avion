package model;

public class Rezervare {
    private String nume;
    private String nr_tel;
    private int adulti;
    private int copii;
    private int seniori;
    private boolean masa_inclusa;
    private boolean bagaje_suplimentare;
    private Zbor zbor_return;
    private Zbor zbor_tur;
    private Plata plata;
    private Clasa clasa;
    private Double procentCustom;

    public Rezervare(String nume, String nr_tel, int adulti, int copii, int seniori, boolean masa_inclusa, boolean bagaje_suplimentare, Zbor zbor_return, Zbor zbor_tur, Plata plata, Clasa clasa, Double procentCustom) {
        this.nume = nume;
        this.nr_tel = nr_tel;
        this.adulti = adulti;
        this.copii = copii;
        this.seniori = seniori;
        this.masa_inclusa = masa_inclusa;
        this.bagaje_suplimentare = bagaje_suplimentare;
        this.zbor_return = zbor_return;
        this.zbor_tur = zbor_tur;
        this.plata = plata;
        this.clasa = clasa;
        this.procentCustom = procentCustom;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNr_tel() {
        return nr_tel;
    }

    public void setNr_tel(String nr_tel) {
        this.nr_tel = nr_tel;
    }

    public int getAdulti() {
        return adulti;
    }

    public void setAdulti(int adulti) {
        this.adulti = adulti;
    }

    public int getCopii() {
        return copii;
    }

    public void setCopii(int copii) {
        this.copii = copii;
    }

    public int getSeniori() {
        return seniori;
    }

    public void setSeniori(int seniori) {
        this.seniori = seniori;
    }

    public boolean isMasa_inclusa() {
        return masa_inclusa;
    }

    public void setMasa_inclusa(boolean masa_inclusa) {
        this.masa_inclusa = masa_inclusa;
    }

    public boolean isBagaje_suplimentare() {
        return bagaje_suplimentare;
    }

    public void setBagaje_suplimentare(boolean bagaje_suplimentare) {
        this.bagaje_suplimentare = bagaje_suplimentare;
    }

    public Zbor getZbor_return() {
        return zbor_return;
    }

    public void setZbor_return(Zbor zbor_return) {
        this.zbor_return = zbor_return;
    }

    public Zbor getZbor_tur() {
        return zbor_tur;
    }

    public void setZbor_tur(Zbor zbor_tur) {
        this.zbor_tur = zbor_tur;
    }

    public Plata getPlata() {
        return plata;
    }

    public void setPlata(Plata plata) {
        this.plata = plata;
    }

    public Clasa getClasa() {
        return clasa;
    }

    public void setClasa(Clasa clasa) {
        this.clasa = clasa;
    }

    public Double getProcentCustom() {
        return procentCustom;
    }

    public void setProcentCustom(Double procentCustom) {
        this.procentCustom = procentCustom;
    }

    public double calculPretBilet(){
        int nr_pasageri=adulti+copii+seniori;
        double pret_tur= zbor_tur.getTarif(clasa);
        double pret_retur= zbor_return!=null? zbor_return.getTarif(clasa):0;
        double pretbaza=(pret_retur+pret_tur)*nr_pasageri;
        double pret=0.0;

        //Optiuni suplimentare
        if(bagaje_suplimentare){
            pretbaza=pretbaza+pretbaza*0.05;
        }
        if(masa_inclusa){
            pretbaza=pretbaza+pretbaza*0.05;
        }

            DiscountTurRetur dtr=new DiscountTurRetur();
            pret= dtr.calculeazaDiscount(this,pretbaza);

        return pret;
    }
}
