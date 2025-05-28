package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginCompanieView extends JFrame {
    private JTextField username_field;
    private JPasswordField password_field;
    private JButton login_btn;
    private JLabel message_lbl;

    public LoginCompanieView() {
        setTitle("Login Companie Aeriana");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal cu background colorat
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 243, 247)); // Very light grayish blue

        // Card panel (containerul central)
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setPreferredSize(new Dimension(360, 260));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new CompoundBorder(
                new DropShadowBorder(),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Label "Bine ai venit, Admin!"
        JLabel welcomeLabel = new JLabel("Bine ai venit, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Montserrat", Font.BOLD, 26));
        welcomeLabel.setForeground(new Color(45, 118, 232)); // vibrant blue
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        cardPanel.add(welcomeLabel, gbc);

        // Separator subtire sub welcome label
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(220, 220, 220));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        cardPanel.add(separator, gbc);
        gbc.insets = new Insets(12, 0, 12, 0); // reset margin

        // Label username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        usernameLabel.setForeground(new Color(80, 80, 80));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 5, 10);
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        cardPanel.add(usernameLabel, gbc);

        // TextField username
        username_field = new JTextField();
        username_field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        username_field.setBorder(new RoundedBorder(10));
        username_field.setBackground(new Color(245, 247, 250));
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(0, 0, 5, 0);
        cardPanel.add(username_field, gbc);

        // Label parola
        JLabel passwordLabel = new JLabel("Parola");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passwordLabel.setForeground(new Color(80, 80, 80));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        gbc.insets = new Insets(0, 0, 10, 10);
        cardPanel.add(passwordLabel, gbc);

        // PasswordField
        password_field = new JPasswordField();
        password_field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        password_field.setBorder(new RoundedBorder(10));
        password_field.setBackground(new Color(245, 247, 250));
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(0, 0, 10, 0);
        cardPanel.add(password_field, gbc);

        // Buton login cu gradient și shadow
        login_btn = new JButton("Login");
        login_btn.setFont(new Font("Montserrat", Font.BOLD, 17));
        login_btn.setForeground(Color.WHITE);
        login_btn.setFocusPainted(false);
        login_btn.setContentAreaFilled(false);
        login_btn.setOpaque(true);
        login_btn.setBackground(new Color(45, 118, 232));
        login_btn.setBorder(new RoundedBorder(20));
        login_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        login_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                login_btn.setBackground(new Color(37, 100, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                login_btn.setBackground(new Color(45, 118, 232));
            }
        });
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 0, 5, 0);
        cardPanel.add(login_btn, gbc);

        // Label mesaje (eroare / succes)
        message_lbl = new JLabel("", SwingConstants.CENTER);
        message_lbl.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        message_lbl.setForeground(new Color(220, 20, 60)); // crimson pentru erori
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 0, 0, 0);
        cardPanel.add(message_lbl, gbc);

        mainPanel.add(cardPanel);
        add(mainPanel);
    }

    public String getUsername() {
        return username_field.getText();
    }

    public String getPassword() {
        return new String(password_field.getPassword());
    }

    public void setMessage(String mesaj, Color culoare) {
        message_lbl.setText(mesaj);
        message_lbl.setForeground(culoare);
    }

    public void addLoginListener(ActionListener listener) {
        login_btn.addActionListener(listener);
    }

    // Clasă internă pentru bordură rotunjită la componente
    static class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(180, 180, 180));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x + 1, y + 1, width - 3, height - 3, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius/2, radius/2, radius/2, radius/2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius / 2;
            return insets;
        }
    }

    // Clasă simplă pentru umbra cardului (shadow)
    static class DropShadowBorder extends AbstractBorder {
        private final int shadowSize = 6;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int shadowAlpha = 50; // transparență
            Color shadowColor = new Color(0, 0, 0, shadowAlpha);

            // Desenăm o umbră în dreapta și jos (dreapta-jos)
            for (int i = 0; i < shadowSize; i++) {
                g2.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha - i * 8));
                g2.drawRoundRect(x + i, y + i, width - i*2 - 1, height - i*2 - 1, 20, 20);
            }
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize, shadowSize, shadowSize, shadowSize);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = shadowSize;
            return insets;
        }
    }
}
