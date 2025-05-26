package controller;

import dao.ZborDAO;
import model.Zbor;

import java.util.List;

public class ZborController {

    private ZborDAO zborDAO;

    public ZborController() {
        this.zborDAO = new ZborDAO();
    }

    public List<Zbor> getAllZboruri() {
        return zborDAO.getAllZboruri();
    }
}