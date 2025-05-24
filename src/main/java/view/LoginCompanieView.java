package view;

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
        setTitle("Login Companie Aeriana"); // titlu fereastra
        setSize(350, 200); // dimensiune fereastra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // inchidere aplicatie
        setLocationRelativeTo(null); // centrare fereastra pe ecran

        // creare panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // cream labeluri cu aceeasi dimensiune
        JLabel usernameLabel = new JLabel("Username:", SwingConstants.RIGHT);
        JLabel passwordLabel = new JLabel("Parola:", SwingConstants.RIGHT);
        Dimension labelSize = new Dimension(80, 20); // latime fixa pt aliniere
        usernameLabel.setPreferredSize(labelSize);
        passwordLabel.setPreferredSize(labelSize);

        // username label + field
        gbc.gridx = 0; // coloana
        gbc.gridy = 0; // rand
        gbc.insets = new Insets(5,5,5,5); // padding
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        username_field = new JTextField(15);
        panel.add(username_field, gbc);

        // password label + field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        password_field = new JPasswordField(15);
        panel.add(password_field, gbc);

        // buton login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        login_btn = new JButton("Login");
        panel.add(login_btn, gbc);

        // label mesaj eroare/succes
        gbc.gridy = 3;
        message_lbl = new JLabel("", SwingConstants.CENTER);
        message_lbl.setForeground(Color.RED);
        panel.add(message_lbl, gbc);

        add(panel);

        // listener buton login
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

        if (username.equals("admin") && password.equals("admin")) {
            message_lbl.setForeground(new Color(0,128,0));  // verde
            message_lbl.setText("Autentificare reușită!");
            // deschidere fereeastra gestionare zboruri
        } else {
            message_lbl.setForeground(Color.RED);
            message_lbl.setText("Date invalide, încearcă din nou.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginCompanieView().setVisible(true);
        });
    }
}
