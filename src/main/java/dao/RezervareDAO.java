package dao;

public class RezervareDAO {private int id;
    private String nume;
    private String modalitatePlata;
    private boolean plataCacheValidata;

    public RezervareDAO(int id, String nume, String modalitatePlata, boolean plataCacheValidata) {
        this.id = id;
        this.nume = nume;
        this.modalitatePlata = modalitatePlata;
        this.plataCacheValidata = plataCacheValidata;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getModalitatePlata() {
        return modalitatePlata;
    }

    public boolean isPlataCacheValidata() {
        return plataCacheValidata;
    }
}