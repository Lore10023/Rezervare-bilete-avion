package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import controller.ZborController;

public class AdaugareZboruriView extends JFrame {
    private ZborController controller = new ZborController();

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

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
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
        setPlaceholder(tfCodCursa, "Ex: 1009");

        tfModelAvion = campText();
        setPlaceholder(tfModelAvion, "Ex: Boeing 737");

        tfOrasPlecare = campText();
        setPlaceholder(tfOrasPlecare, "Ex: București");

        tfOrasDestinatie = campText();
        setPlaceholder(tfOrasDestinatie, "Ex: Timișoara");

        tfNrLocuri = campText();
        setPlaceholder(tfNrLocuri, "Ex: 150");

        tfTarif = campText();
        setPlaceholder(tfTarif, "Ex: 299.99");

        tfZileSaptamana = campText();
        setPlaceholder(tfZileSaptamana, "Ex: LUNI");

        tfOra = campText();
        setPlaceholder(tfOra, "Ex: 14:30");

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
        setPlaceholder(tfPerioadaInceput, "Ex: 2024-06-01");

        tfPerioadaFinal = campText();
        setPlaceholder(tfPerioadaFinal, "Ex: 2024-09-30");

        int y = panelFormular.getComponentCount() / 2;
        adaugaRand(label("Perioadă Început:"), tfPerioadaInceput, y++);
        adaugaRand(label("Perioadă Final:"), tfPerioadaFinal, y++);

        panelFormular.revalidate();
        panelFormular.repaint();
    }

    private void salveazaZbor() {
        try {
            String codCursa = tfCodCursa.getText();
            String modelAvion = tfModelAvion.getText();
            String orasPlecare = tfOrasPlecare.getText();
            String orasDestinatie = tfOrasDestinatie.getText();

            // Validare simplă să nu fie placeholder la câmpuri obligatorii
            if (tfCodCursa.getForeground() == Color.GRAY ||
                    tfModelAvion.getForeground() == Color.GRAY ||
                    tfOrasPlecare.getForeground() == Color.GRAY ||
                    tfOrasDestinatie.getForeground() == Color.GRAY) {
                JOptionPane.showMessageDialog(this, "Completează toate câmpurile obligatorii!", "Atenție", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int nrLocuri = Integer.parseInt(tfNrLocuri.getText());
            double tarif = Double.parseDouble(tfTarif.getText());
            String clasa = rbBusiness.isSelected() ? "Business" :
                    rbClasa1.isSelected() ? "Clasa1" : "Economie";

            if (radioRegulat.isSelected()) {
                if (tfZileSaptamana.getForeground() == Color.GRAY || tfOra.getForeground() == Color.GRAY) {
                    JOptionPane.showMessageDialog(this, "Completează toate câmpurile obligatorii!", "Atenție", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String[] zileArray = tfZileSaptamana.getText().split(",");
                String ora = tfOra.getText();

                boolean success = controller.adaugaZborRegulat(
                        codCursa, modelAvion, orasPlecare, orasDestinatie,
                        clasa, nrLocuri, tarif, zileArray, ora
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Zbor regulat adăugat cu succes!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Eroare la adăugarea zborului!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (tfPerioadaInceput.getForeground() == Color.GRAY || tfPerioadaFinal.getForeground() == Color.GRAY) {
                    JOptionPane.showMessageDialog(this, "Completează toate câmpurile obligatorii!", "Atenție", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String perioadaInceput = tfPerioadaInceput.getText();
                String perioadaFinal = tfPerioadaFinal.getText();

                boolean success = controller.adaugaZborSezonier(
                        codCursa, modelAvion, orasPlecare, orasDestinatie,
                        clasa, nrLocuri, tarif, perioadaInceput, perioadaFinal
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Zbor sezonier adăugat cu succes!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Eroare la adăugarea zborului!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Număr locuri sau tarif invalid!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
}
