package view;

import javax.swing.*;
import java.awt.*;

public class AdaugareZboruriView extends JFrame {

    private JTextField tfCodCursa, tfModelAvion, tfOrasPlecare, tfOrasDestinatie;
    private JTextField tfNrLocuri, tfTarif, tfZileSaptamana, tfOra;
    private JTextField tfPerioadaInceput, tfPerioadaFinal;

    private JRadioButton rbBusiness, rbClasa1, rbEconomie;
    private JRadioButton radioRegulat, radioSezonier;

    private JButton btnSalvare, btnAnulare;
    private JPanel panelFormular;

    private final Font fontCamp = new Font("SansSerif", Font.PLAIN, 14);
    private final Dimension dimensiuneCamp = new Dimension(250, 30);
    private final int labelWidth = 150;

    public AdaugareZboruriView() {
        setTitle("Adăugare Zbor");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Selectare tip zbor
        JPanel panelTipZbor = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        radioRegulat = new JRadioButton("Zbor Regulat");
        radioSezonier = new JRadioButton("Zbor Sezonier");
        ButtonGroup grupTipZbor = new ButtonGroup();
        grupTipZbor.add(radioRegulat);
        grupTipZbor.add(radioSezonier);
        radioRegulat.setSelected(true);

        radioRegulat.setFont(fontCamp);
        radioSezonier.setFont(fontCamp);

        panelTipZbor.add(radioRegulat);
        panelTipZbor.add(radioSezonier);
        add(panelTipZbor, BorderLayout.NORTH);

        // Formular central
        panelFormular = new JPanel(new GridBagLayout());
        panelFormular.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(panelFormular, BorderLayout.CENTER);

        // Butoane jos
        btnSalvare = new JButton("Salvează Zbor");
        btnAnulare = new JButton("Anulează");

        btnSalvare.setFont(fontCamp);
        btnAnulare.setFont(fontCamp);

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBtn.add(btnSalvare);
        panelBtn.add(btnAnulare);
        add(panelBtn, BorderLayout.SOUTH);

        construieșteFormularRegulat();

        radioRegulat.addActionListener(e -> construieșteFormularRegulat());
        radioSezonier.addActionListener(e -> construieșteFormularSezonier());

        btnSalvare.addActionListener(e -> salveazaZbor());
        btnAnulare.addActionListener(e -> dispose());
    }

    private JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setPreferredSize(new Dimension(labelWidth, 30));
        lbl.setFont(fontCamp);
        return lbl;
    }

    private JTextField campText() {
        JTextField tf = new JTextField();
        tf.setPreferredSize(dimensiuneCamp);
        tf.setFont(fontCamp);
        return tf;
    }

    private void adaugaRand(JLabel label, JComponent component, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = y;
        panelFormular.add(label, gbc);

        gbc.gridx = 1;
        panelFormular.add(component, gbc);
    }

    private void construieșteFormularRegulat() {
        panelFormular.removeAll();

        tfCodCursa = campText();
        tfModelAvion = campText();
        tfOrasPlecare = campText();
        tfOrasDestinatie = campText();
        tfNrLocuri = campText();
        tfTarif = campText();
        tfZileSaptamana = campText();
        tfOra = campText();

        rbBusiness = new JRadioButton("Business");
        rbClasa1 = new JRadioButton("Clasa 1");
        rbEconomie = new JRadioButton("Economie");

        ButtonGroup grupClase = new ButtonGroup();
        grupClase.add(rbBusiness);
        grupClase.add(rbClasa1);
        grupClase.add(rbEconomie);
        rbEconomie.setSelected(true);

        rbBusiness.setFont(fontCamp);
        rbClasa1.setFont(fontCamp);
        rbEconomie.setFont(fontCamp);

        JPanel panelClasa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelClasa.add(rbBusiness);
        panelClasa.add(rbClasa1);
        panelClasa.add(rbEconomie);

        int y = 0;
        adaugaRand(label("Cod Cursă:"), tfCodCursa, y++);
        adaugaRand(label("Model Avion:"), tfModelAvion, y++);
        adaugaRand(label("Oraș Plecare:"), tfOrasPlecare, y++);
        adaugaRand(label("Oraș Destinație:"), tfOrasDestinatie, y++);
        adaugaRand(label("Număr Locuri:"), tfNrLocuri, y++);
        adaugaRand(label("Tarif:"), tfTarif, y++);
        adaugaRand(label("Clasa:"), panelClasa, y++);
        adaugaRand(label("Zile Săptămână:"), tfZileSaptamana, y++);
        adaugaRand(label("Ora (HH:mm):"), tfOra, y++);

        panelFormular.revalidate();
        panelFormular.repaint();
    }

    private void construieșteFormularSezonier() {
        construieșteFormularRegulat();

        tfPerioadaInceput = campText();
        tfPerioadaFinal = campText();

        int y = panelFormular.getComponentCount() / 2;
        adaugaRand(label("Perioadă Început:"), tfPerioadaInceput, y++);
        adaugaRand(label("Perioadă Final:"), tfPerioadaFinal, y++);
    }

    private void salveazaZbor() {
        String codCursa = tfCodCursa.getText();
        String modelAvion = tfModelAvion.getText();
        String orasPlecare = tfOrasPlecare.getText();
        String orasDestinatie = tfOrasDestinatie.getText();
        String nrLocuri = tfNrLocuri.getText();
        String tarif = tfTarif.getText();
        String zileSaptamana = tfZileSaptamana.getText();
        String ora = tfOra.getText();

        String clasa = rbBusiness.isSelected() ? "Business" :
                rbClasa1.isSelected() ? "Clasa 1" : "Economie";

        if (radioRegulat.isSelected()) {
            JOptionPane.showMessageDialog(this, "Zbor REGULAT salvat:\n" +
                    "Cod: " + codCursa + ", Avion: " + modelAvion + "\n" +
                    "Plecare: " + orasPlecare + " → " + orasDestinatie + "\n" +
                    "Locuri: " + nrLocuri + ", Clasa: " + clasa + ", Tarif: " + tarif + "\n" +
                    "Zile: " + zileSaptamana + ", Ora: " + ora);
        } else {
            String perioadaInceput = tfPerioadaInceput.getText();
            String perioadaFinal = tfPerioadaFinal.getText();

            JOptionPane.showMessageDialog(this, "Zbor SEZONIER salvat:\n" +
                    "Cod: " + codCursa + ", Avion: " + modelAvion + "\n" +
                    "Plecare: " + orasPlecare + " → " + orasDestinatie + "\n" +
                    "Locuri: " + nrLocuri + ", Clasa: " + clasa + ", Tarif: " + tarif + "\n" +
                    "Zile: " + zileSaptamana + ", Ora: " + ora + "\n" +
                    "Perioadă: " + perioadaInceput + " → " + perioadaFinal);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdaugareZboruriView().setVisible(true));
    }
}