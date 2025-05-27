package view;

import dao.RezervarePasageriDAO;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RezervareView extends JFrame {
    private JTextField numeField;
    private JTextField nrTelField;
    private JTextField nrAdultiField;
    private JTextField nrCopiiField;
    private JTextField nrSenioriField;
    private JCheckBox masaInclusaCheck;
    private JCheckBox bagajSuplCheck;
    private JCheckBox discountCheck;
    private JTextField pretTotalField;
    private JComboBox<String> modalitatePlataCombo;
    private JCheckBox plataCacheValidataCheck;
    private JComboBox<Clasa> clasaCombo;
    private JComboBox<Zbor> zborTurCombo;
    private JComboBox<Zbor> zborReturCombo;
    private JButton rezervaButton;

    public RezervareView() {
        setTitle("Rezervare Bilet");
        setSize(400, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        numeField = new JTextField(15);
        nrTelField = new JTextField(15);
        nrAdultiField = new JTextField(5);
        nrCopiiField = new JTextField(5);
        nrSenioriField = new JTextField(5);
        masaInclusaCheck = new JCheckBox("Masă inclusă");
        bagajSuplCheck = new JCheckBox("Bagaj suplimentar");
        discountCheck = new JCheckBox("Discount tur-retur aplicat");
        pretTotalField = new JTextField(10);
        modalitatePlataCombo = new JComboBox<>(new String[]{"card", "cache"});
        plataCacheValidataCheck = new JCheckBox("Plata cash validată");
        clasaCombo = new JComboBox<>(Clasa.values());
        zborTurCombo = new JComboBox<>();
        zborReturCombo = new JComboBox<>();
        rezervaButton = new JButton("Rezervă");

        // TODO: Populează zborTurCombo și zborReturCombo cu obiecte Zbor valide
        // zborTurCombo.addItem(...);
        // zborReturCombo.addItem(...);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Nume:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(numeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Nr. Tel:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(nrTelField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Nr. Adulți:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(nrAdultiField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Nr. Copii:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(nrCopiiField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Nr. Seniori:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(nrSenioriField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(masaInclusaCheck, gbc);

        gbc.gridy = 6;
        panel.add(bagajSuplCheck, gbc);

        gbc.gridy = 7;
        panel.add(discountCheck, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Preț total:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        pretTotalField.setEditable(false);
        panel.add(pretTotalField, gbc);

        gbc.gridx = 0; gbc.gridy = 9; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Modalitate plată:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(modalitatePlataCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 10; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Clasa:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(clasaCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 11; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Zbor tur:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(zborTurCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 12; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Zbor retur:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(zborReturCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 13; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(plataCacheValidataCheck, gbc);

        gbc.gridy = 14; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(rezervaButton, gbc);

        add(panel);

        rezervaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nume = numeField.getText();
                    String nrTel = nrTelField.getText();
                    int nrAdulti = Integer.parseInt(nrAdultiField.getText());
                    int nrCopii = Integer.parseInt(nrCopiiField.getText());
                    int nrSeniori = Integer.parseInt(nrSenioriField.getText());
                    boolean masaInclusa = masaInclusaCheck.isSelected();
                    boolean bagajSupl = bagajSuplCheck.isSelected();
                    boolean discount = discountCheck.isSelected();
                    String modalitatePlata = (String) modalitatePlataCombo.getSelectedItem();
                    boolean plataCashValidata = plataCacheValidataCheck.isSelected();
                    Clasa clasa = (Clasa) clasaCombo.getSelectedItem();
                    Zbor zborTur = (Zbor) zborTurCombo.getSelectedItem();
                    Zbor zborRetur = discount ? (Zbor) zborReturCombo.getSelectedItem() : null;

                    Plata plata = Plata.valueOf(modalitatePlata.toUpperCase()); // enum Plata cu valori CASH, CARD

                    Double procentCustom = null;
                    if (discount) {
                        procentCustom = 0.05; // procentul discountului tur-retur
                    }

                    Rezervare rezervare = new Rezervare(nume, nrTel, nrAdulti, nrCopii, nrSeniori,
                            masaInclusa, bagajSupl, zborRetur, zborTur,
                            plata, clasa, procentCustom);

                    double pretTotal = rezervare.calculPretBilet();
                    pretTotalField.setText(String.format("%.2f", pretTotal));

                    RezervarePasageriDAO.adaugaRezervare(nume, nrTel, nrAdulti, nrCopii, nrSeniori,
                            masaInclusa, bagajSupl, discount, pretTotal,
                            modalitatePlata, plataCashValidata);

                    JOptionPane.showMessageDialog(RezervareView.this, "Rezervarea a fost adăugată cu succes!");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(RezervareView.this, "Introduceți valori valide pentru numere!",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RezervareView.this, "Eroare la adăugarea rezervării: " + ex.getMessage(),
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        setVisible(true); // fereastra este afișată
    }

    public static void main(String[] args) {
        new RezervareView();
    } // se creează și afișează fereastra atunci când se rulează programul
}
