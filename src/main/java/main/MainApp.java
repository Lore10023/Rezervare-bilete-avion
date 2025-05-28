package main;

import controller.LoginCompanieController;
import dao.CompanieDAO;
import view.LoginCompanieView;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginCompanieView loginView = new LoginCompanieView();
            CompanieDAO dao = new CompanieDAO();

            new LoginCompanieController(loginView, dao);

            loginView.setVisible(true);
        });
    }
}
