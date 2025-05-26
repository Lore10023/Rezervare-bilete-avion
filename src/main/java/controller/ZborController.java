package controller;

import dao.ZborDAO;
import model.Zbor;
import java.util.List;

public class ZborController {
    private ZborDAO zborDAO = new ZborDAO();

    public List<Zbor> getAllZboruri() {
        return zborDAO.getAllZboruri();
    }

    public boolean stergeZbor(String codCursa) {
        return zborDAO.stergeZbor(codCursa);
    }
}