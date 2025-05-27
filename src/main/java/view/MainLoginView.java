package view;

import javax.swing.*;
import java.awt.*;

public class MainLoginView extends JFrame {
    public MainLoginView() {
        setTitle("Sistem Rezervări - Autentificare");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnCompanie = new JButton("Login Companie");
        JButton btnPersonal = new JButton("Login Personal");

        btnCompanie.addActionListener(e -> {
            new LoginCompanieView().setVisible(true);
            dispose();
        });

        btnPersonal.addActionListener(e -> {
            new PersonalLoginView().setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.add(btnCompanie);
        panel.add(btnPersonal);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainLoginView();
    }
}
