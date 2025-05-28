package view;

import javax.swing.*;
import java.awt.*;

public class MainLoginView extends JFrame {

    public MainLoginView() {
        setTitle("Sistem Rezervări - Autentificare");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrează fereastra

        // Creăm butoanele
        JButton btnCompanie = new JButton("Login Companie");
        JButton btnPersonal = new JButton("Login Personal");
        JButton btnCautareZboruri = new JButton("Căutare Zboruri");

        // Acțiune pentru login companie
        btnCompanie.addActionListener(e -> {
            new LoginCompanieView().setVisible(true);
            dispose();
        });

        // Acțiune pentru login personal
        btnPersonal.addActionListener(e -> {
            new PersonalLoginView().setVisible(true);
            dispose();
        });

        // Acțiune pentru căutare zboruri
        btnCautareZboruri.addActionListener(e -> {
            new CautareZborView().setVisible(true);
            // nu închide fereastra de login — rămâne deschisă
        });

        // Panel cu layout pe 3 rânduri
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // padding
        panel.add(btnCompanie);
        panel.add(btnPersonal);
        panel.add(btnCautareZboruri);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainLoginView();
    }
}

