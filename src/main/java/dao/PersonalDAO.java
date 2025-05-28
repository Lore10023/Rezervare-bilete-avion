package dao;

public class PersonalDAO {
    private int id;
    private String nume;
    private String codAcces;

    public PersonalDAO(int id, String nume, String codAcces) {
        this.id = id;
        this.nume = nume;
        this.codAcces = codAcces;
    }

    public int getId() { return id; }
    public String getNume() { return nume; }
    public String getCodAcces() { return codAcces; }
}
