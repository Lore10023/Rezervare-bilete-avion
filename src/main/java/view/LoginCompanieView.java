package view;

import dao.CompanieDAO;
import view.GestionareZboruriView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginCompanieView extends JFrame {
    private JTextField username_field;
    private JPasswordField password_field;
    private JButton login_btn;
    private JLabel message_lbl;

    public LoginCompanieView() {
        setTitle("Login Companie Aeriana");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:", SwingConstants.RIGHT);
        JLabel passwordLabel = new JLabel("Parola:", SwingConstants.RIGHT);
        Dimension labelSize = new Dimension(80, 20);
        usernameLabel.setPreferredSize(labelSize);
        passwordLabel.setPreferredSize(labelSize);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        username_field = new JTextField(15);
        panel.add(username_field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        password_field = new JPasswordField(15);
        panel.add(password_field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        login_btn = new JButton("Login");
        panel.add(login_btn, gbc);

        gbc.gridy = 3;
        message_lbl = new JLabel("", SwingConstants.CENTER);
        message_lbl.setForeground(Color.RED);
        panel.add(message_lbl, gbc);

        add(panel);

        login_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autentificare();
            }
        });
    }

    private void autentificare() {
        String username = username_field.getText();
        String password = new String(password_field.getPassword());

        try {
            CompanieDAO dao = new CompanieDAO();
            boolean rezultat = dao.autentifica(username, password);

            if (rezultat) {
                message_lbl.setForeground(new Color(0, 128, 0));
                message_lbl.setText("Autentificare reușită!");

                // Deschide fereastra GestionareZboruriView
                SwingUtilities.invokeLater(() -> {
                    new GestionareZboruriView().setVisible(true);
                });

                dispose(); // Închide fereastra de login
            } else {
                message_lbl.setForeground(Color.RED);
                message_lbl.setText("Date invalide, încearcă din nou.");
            }
        } catch (Exception ex) {
            message_lbl.setForeground(Color.RED);
            message_lbl.setText("Eroare conexiune BD: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginCompanieView().setVisible(true);
        });
    }
}
