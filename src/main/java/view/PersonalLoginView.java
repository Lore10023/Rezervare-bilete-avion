package view;

import controller.PersonalController;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersonalLoginView extends JFrame {

    public PersonalLoginView() {
        setTitle("Login Personal Aeroport");
        setSize(300, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField codField = new JTextField(15);
        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Înapoi");  // 🔁 Buton Înapoi

        loginBtn.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/sistemrezervari", "root", "programITnou24");
                PersonalController controller = new PersonalController(conn);

                if (controller.autentificare(codField.getText())) {
                    JOptionPane.showMessageDialog(this, "Autentificare reușită!");
                    dispose();
                    new ValidareRezervariView(controller);
                } else {
                    JOptionPane.showMessageDialog(this, "Cod incorect!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la conectare.");
            }
        });


        backBtn.addActionListener(e -> {
            dispose();
            MainLoginView.main(null);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        JPanel fieldPanel = new JPanel();
        fieldPanel.add(new JLabel("Cod acces:"));
        fieldPanel.add(codField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginBtn);
        buttonPanel.add(backBtn);

        panel.add(fieldPanel);
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }
}
