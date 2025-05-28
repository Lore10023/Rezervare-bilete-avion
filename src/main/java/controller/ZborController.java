package controller;

import dao.ZborDAO;
import dao.ZborDAO;
import model.Zbor;
import java.util.List;

public class ZborController {

    private final ZborDAO zborDAO;

    public ZborController() {
        this.zborDAO = new ZborDAO();
    }

    public List<Zbor> getAllZboruri() {
        return zborDAO.getAllZboruri();
    }

    public boolean stergeZbor(String codCursa) {
        return zborDAO.stergeZbor(codCursa);
    }

    public boolean adaugaZborRegulat(String codCursa, String modelAvion, String orasPlecare, String orasDestinatie,
                                     String clasa, int nrLocuri, double tarif, String[] zileSaptamana, String ora) {
        try {
            return zborDAO.adaugaZborRegulat(codCursa, modelAvion, orasPlecare, orasDestinatie,
                    clasa, nrLocuri, tarif, zileSaptamana, ora);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean adaugaZborSezonier(String codCursa, String modelAvion, String orasPlecare, String orasDestinatie,
                                      String clasa, int nrLocuri, double tarif,
                                      String dataInceput, String dataFinal) {
        try {
            return zborDAO.adaugaZborSezonier(codCursa, modelAvion, orasPlecare, orasDestinatie,
                    clasa, nrLocuri, tarif, dataInceput, dataFinal);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

