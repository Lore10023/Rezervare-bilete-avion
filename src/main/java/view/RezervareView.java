package view;

import dao.RezervarePasageriDAO;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class RezervareView extends JFrame {
    public RezervareView() {
        setTitle("Rezervare Bilet");
        setSize(450, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 10, 10));

        // Componente de interfață
        JTextField tfCodCursa = new JTextField();
        JTextField tfNume = new JTextField();
        JTextField tfTel = new JTextField();
        JTextField tfAdulti = new JTextField("1");
        JTextField tfCopii = new JTextField("0");
        JTextField tfSeniori = new JTextField("0");

        JCheckBox cbMasa = new JCheckBox("Masă inclusă");
        JCheckBox cbBagaje = new JCheckBox("Bagaje suplimentare");

        JComboBox<Clasa> cbClasa = new JComboBox<>(Clasa.values());

        JTextField tfDiscount = new JTextField();  // Discount în %
        JComboBox<Plata> modalitatePlataCombo = new JComboBox<>(Plata.values());

        JButton btnCalculeaza = new JButton("Calculează Preț");
        JLabel lblRezultat = new JLabel("Preț total: ");
        JButton btnRezervare = new JButton("Rezervare");


        // Adaugă componente în UI
        add(new JLabel("Cod Cursa:")); add(tfCodCursa);
        add(new JLabel("Nume:")); add(tfNume);
        add(new JLabel("Telefon:")); add(tfTel);
        add(new JLabel("Nr. Adulți:")); add(tfAdulti);
        add(new JLabel("Nr. Copii:")); add(tfCopii);
        add(new JLabel("Nr. Seniori:")); add(tfSeniori);
        add(cbMasa); add(cbBagaje);
        add(new JLabel("Clasa:")); add(cbClasa);
        add(new JLabel("Discount manual (%):")); add(tfDiscount);
        add(new JLabel("Modalitate plata: ")); add(modalitatePlataCombo);
        add(btnCalculeaza); add(lblRezultat);
        add(btnRezervare);

        btnCalculeaza.addActionListener(e -> {
            try {
                String nume = tfNume.getText();
                String tel = tfTel.getText();
                int adulti = Integer.parseInt(tfAdulti.getText());
                int copii = Integer.parseInt(tfCopii.getText());
                int seniori = Integer.parseInt(tfSeniori.getText());
                boolean masaInclusa = cbMasa.isSelected();
                boolean bagajeSuplimentare = cbBagaje.isSelected();
                Clasa clasa = (Clasa) cbClasa.getSelectedItem();
                Plata plata = (Plata) modalitatePlataCombo.getSelectedItem();

                Double discountCustom = null;
                if (!tfDiscount.getText().isEmpty()) {
                    double procent = Double.parseDouble(tfDiscount.getText());
                    discountCustom = procent / 100.0;
                }

                // Zboruri fictive
                Map<Clasa, Integer> locuri = new HashMap<>();
                locuri.put(Clasa.ECONOMIE, 100);
                locuri.put(Clasa.BUSINESS, 50);
                locuri.put(Clasa.CLASA1, 10);

                Map<Clasa, Double> tarife = new HashMap<>();
                tarife.put(Clasa.ECONOMIE, 300.0);
                tarife.put(Clasa.BUSINESS, 500.0);
                tarife.put(Clasa.CLASA1, 800.0);

                Zbor zborTur = new ZborStandard("RO123", "Boeing 737", "București", "Paris",locuri, tarife);
                Zbor zborReturn = new ZborStandard  ("RO124", "Boeing 737", "Paris", "București",locuri, tarife);

                // Creează rezervarea
                Rezervare rezervare = new Rezervare(nume, tel, adulti, copii, seniori,
                        masaInclusa, bagajeSuplimentare,
                        zborReturn, zborTur, plata, clasa, discountCustom);

                double pret = rezervare.calculPretBilet();

                lblRezultat.setText("Preț total: " + String.format("%.2f", pret) + " RON");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Completează corect câmpurile numerice!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Eroare: " + ex.getMessage());
            }
        });

        btnRezervare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nume = tfNume.getText();
                    String tel = tfTel.getText();
                    int adulti = Integer.parseInt(tfAdulti.getText());
                    int copii = Integer.parseInt(tfCopii.getText());
                    int seniori = Integer.parseInt(tfSeniori.getText());
                    boolean masaInclusa = cbMasa.isSelected();
                    boolean bagajeSuplimentare = cbBagaje.isSelected();
                    Clasa clasa = (Clasa) cbClasa.getSelectedItem();
                    Plata plata = (Plata) modalitatePlataCombo.getSelectedItem();

                    Double discountCustom = null;
                    if (!tfDiscount.getText().isEmpty()) {
                        discountCustom = Double.parseDouble(tfDiscount.getText()) / 100.0;
                    }


                    // Zboruri fictive
                    Map<Clasa, Integer> locuri = new HashMap<>();
                    locuri.put(Clasa.ECONOMIE, 100);
                    locuri.put(Clasa.BUSINESS, 50);
                    locuri.put(Clasa.CLASA1, 10);

                    Map<Clasa, Double> tarife = new HashMap<>();
                    tarife.put(Clasa.ECONOMIE, 300.0);
                    tarife.put(Clasa.BUSINESS, 500.0);
                    tarife.put(Clasa.CLASA1, 800.0);

                    Zbor zborTur = new ZborStandard("RO123", "Boeing 737", "București", "Paris",locuri, tarife);
                    Zbor zborReturn = new ZborStandard("RO124", "Boeing 737", "Paris", "București",locuri, tarife);

                    Rezervare rezervare = new Rezervare(nume, tel, adulti, copii, seniori,
                            masaInclusa, bagajeSuplimentare,
                            zborReturn, zborTur, plata, clasa, discountCustom);

                    double pret = rezervare.calculPretBilet();

                    RezervarePasageriDAO.adaugaRezervare(rezervare, pret);
                    JOptionPane.showMessageDialog(null, "Rezervarea a fost salvată cu succes!");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Completează corect câmpurile numerice!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
                }
            }
        });



        setVisible(true);
    }

    public static void main(String[] args) {
        new RezervareView();
    }
}
