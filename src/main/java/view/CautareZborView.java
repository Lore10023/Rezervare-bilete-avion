package view;

import dao.CautareZborDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CautareZborView extends JFrame{
    private JTextField orasPlecare;
    private JTextField orasSosire;
    private JButton cautaButton;

    public CautareZborView() {
        setTitle("Căutare Zboruri"); // titlu fereastra
        setSize(300, 250); // dimensiune fereastra
        setDefaultCloseOperation(EXIT_ON_CLOSE); // inchidere aplicatie
        setLocationRelativeTo(null); // centrare fereastra pe ecran

        orasPlecare = new JTextField(10);
        orasSosire = new JTextField(10);
        cautaButton = new JButton("Caută");

        // creare panel principal
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // spațiu între componente

        // Label "Oraș plecare"
        gbc.gridx = 0; // coloana 0
        gbc.gridy = 0; // rândul 0
        gbc.anchor = GridBagConstraints.LINE_END; // aliniază eticheta la dreapta
        panel.add(new JLabel("Oraș plecare:"), gbc);

        // TextField oraș plecare
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(orasPlecare, gbc);

        // Label "Oraș sosire"
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Oraș sosire:"), gbc);

        // TextField oraș sosire
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(orasSosire, gbc);

        // Buton caută, pe linie nouă, centrat
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // ocupă ambele coloane
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(cautaButton, gbc);

        add(panel);

        cautaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plecare = orasPlecare.getText(); // citire date din câmpuri
                String sosire = orasSosire.getText();

                CautareZborDAO.cautaZboruri(plecare, sosire); // caută în baza de date și afișează rezultatele în consolă
            }
        });

        setVisible(true); // fereastra este afișată
    }
    public static void main(String[] args) {
        new CautareZborView();
    } // se creează și afișează fereastra atunci când se rulează programul
}