package controller;

import dao.CompanieDAO;
import view.GestionareZboruriView;
import view.LoginCompanieView;

import javax.swing.*;
import java.awt.*;

public class LoginCompanieController {
    private LoginCompanieView view;
    private CompanieDAO dao;

    public LoginCompanieController(LoginCompanieView view, CompanieDAO dao) {
        this.view = view;
        this.dao = dao;

        // Adaugă listener pentru butonul Login
        this.view.addLoginListener(e -> autentificare());
    }

    private void autentificare() {
        String username = view.getUsername();
        String password = view.getPassword();

        try {
            boolean rezultat = dao.autentifica(username, password);

            if (rezultat) {
                view.setMessage("Autentificare reușită!", new Color(0, 128, 0));

                // Deschide fereastra GestionareZboruriView
                SwingUtilities.invokeLater(() -> {
                    new GestionareZboruriView().setVisible(true);
                });

                view.dispose(); // Închide fereastra de login
            } else {
                view.setMessage("Date invalide, încearcă din nou.", Color.RED);
            }
        } catch (Exception ex) {
            view.setMessage("Eroare conexiune BD: " + ex.getMessage(), Color.RED);
        }
    }
}
