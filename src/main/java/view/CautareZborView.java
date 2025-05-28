package view;

import view.RezervareView;
import dao.CautareZborDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CautareZborView extends JFrame{
    private JTextField orasPlecare;
    private JTextField orasSosire;
    private JTextField nrPersoane;
    private JCheckBox checkRetur;
    private JTextArea rezultateTur;
    private JTextArea rezultateRetur;
    private JButton cautaButton;
    private JButton rezervareButton;


    public CautareZborView() {
        setTitle("Căutare Zboruri");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inițializare componente
        orasPlecare = new JTextField(10);
        orasSosire = new JTextField(10);
        nrPersoane = new JTextField(5);
        checkRetur = new JCheckBox("Doresc și retur");
        cautaButton = new JButton("Caută zboruri");

        rezultateTur = new JTextArea(10, 50);
        rezultateTur.setEditable(false);
        rezultateRetur = new JTextArea(10, 50);
        rezultateRetur.setEditable(false);
        rezultateRetur.setEnabled(false);
        rezervareButton = new JButton("Vreau să fac rezervare");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;
        panel.add(new JLabel("Oraș plecare:"), gbcAt(0, y));
        panel.add(orasPlecare, gbcAt(1, y++));

        panel.add(new JLabel("Oraș sosire:"), gbcAt(0, y));

        panel.add(orasSosire, gbcAt(1, y++));

        panel.add(new JLabel("Număr persoane:"), gbcAt(0, y));
        panel.add(nrPersoane, gbcAt(1, y++));

        panel.add(checkRetur, gbcAt(0, y++, 2));

        panel.add(cautaButton, gbcAt(0, y++, 2));
        panel.add(rezervareButton, gbcAt(0, y++, 2));

        panel.add(new JLabel("Rezultate zbor tur:"), gbcAt(0, y++, 2));
        panel.add(new JScrollPane(rezultateTur), gbcAt(0, y++, 2));

        panel.add(new JLabel("Rezultate zbor retur:"), gbcAt(0, y++, 2));
        panel.add(new JScrollPane(rezultateRetur), gbcAt(0, y++, 2));

        add(panel);

        checkRetur.addActionListener(e -> {
            boolean selected = checkRetur.isSelected();
            rezultateRetur.setEnabled(selected);
        });

        cautaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plecare = orasPlecare.getText();
                String sosire = orasSosire.getText();
                String nr = nrPersoane.getText();

                if (plecare.isEmpty() || sosire.isEmpty()  || nr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Completează toate câmpurile pentru zborul tur!");
                    return;
                }

                rezultateTur.setText(CautareZborDAO.cautaZboruri(plecare, sosire, Integer.parseInt(nr)));

                if (checkRetur.isSelected()) {
                    rezultateRetur.setText(CautareZborDAO.cautaZboruri(sosire, plecare, Integer.parseInt(nr)));
                }
            }
        });

        rezervareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // închide fereastra curentă
                new RezervareView(); // deschide fereastra de rezervare
            }
        });

        setVisible(true);
    }

    private GridBagConstraints gbcAt(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private GridBagConstraints gbcAt(int x, int y, int width) {
        GridBagConstraints gbc = gbcAt(x, y);
        gbc.gridwidth = width;
        return gbc;
    }

    public static void main(String[] args) {
        new CautareZborView();
    } // se creează și afișează fereastra atunci când se rulează programul
}