package view;

import controller.ZborController;
import model.Zbor;
import model.ZboruriRegulate;
import model.ZboruriSezoniere;
import model.ZiSaptamana;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class GestionareZboruriView extends JFrame {
    private ZborController controller;
    private JTable tabelZboruri;
    private JButton btnAdaugare;
    private JButton btnStergere;

    public GestionareZboruriView() {
        controller = new ZborController();
        initUI();
        incarcaDate();
    }

    private void initUI() {
        setTitle("Gestionare Zboruri");
        setSize(950, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] coloane = {"Cod Cursă", "Model Avion", "Oraș Plecare", "Oraș Destinație", "Tip Zbor", "Detalii"};
        tabelZboruri = new JTable(new DefaultTableModel(coloane, 0));
        JScrollPane scrollPane = new JScrollPane(tabelZboruri);

        btnAdaugare = new JButton("Adaugă Zbor");
        btnStergere = new JButton("Șterge Zbor");

        JPanel panelButoane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButoane.add(btnAdaugare);
        panelButoane.add(btnStergere);

        add(scrollPane, BorderLayout.CENTER);
        add(panelButoane, BorderLayout.SOUTH);

        btnAdaugare.addActionListener(e -> {
            AdaugareZboruriView adaugareView = new AdaugareZboruriView();
            adaugareView.setVisible(true);
            adaugareView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    incarcaDate();
                }
            });
        });

        btnStergere.addActionListener(e -> stergeZborSelectat());
    }

    private void stergeZborSelectat() {
        int selectedRow = tabelZboruri.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selectați un zbor pentru ștergere!",
                    "Eroare",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codCursa = (String) tabelZboruri.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Sigur doriți să ștergeți zborul " + codCursa + "?\nAceastă acțiune este permanentă!",
                "Confirmare ștergere",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = controller.stergeZbor(codCursa);
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Zbor șters cu succes!",
                        "Succes",
                        JOptionPane.INFORMATION_MESSAGE);
                incarcaDate();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Ștergerea a eșuat! Verificați dacă există rezervări active.",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void incarcaDate() {
        DefaultTableModel model = (DefaultTableModel) tabelZboruri.getModel();
        model.setRowCount(0);

        List<Zbor> zboruri = controller.getAllZboruri();
        for (Zbor z : zboruri) {
            String tipZbor = (z instanceof ZboruriRegulate) ? "Regulat" : "Sezonier";
            String detalii = "";

            if (z instanceof ZboruriRegulate zr) {
                // Convertim ziua din enum în String
                List<ZiSaptamana> zile = zr.getZisaptamana();
                String zileString = zile != null ? zile.stream().map(Enum::name).collect(Collectors.joining(", ")) : "N/A";

                // Formatăm ora
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String oraString = zr.getOra() != null ? zr.getOra().format(timeFormatter) : "N/A";

                detalii = "Ziua: " + zileString + ", Ora: " + oraString;

            } else if (z instanceof ZboruriSezoniere zs) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String inceput = zs.getPerioada_inceput() != null ? zs.getPerioada_inceput().format(dateFormatter) : "N/A";
                String sfarsit = zs.getPerioada_finala() != null ? zs.getPerioada_finala().format(dateFormatter) : "N/A";
                detalii = "Perioada: " + inceput + " - " + sfarsit;
            }

            model.addRow(new Object[]{
                    z.getCodCursa(),
                    z.getModel(),
                    z.getOrasPlecare(),
                    z.getOrasDestinatie(),
                    tipZbor,
                    detalii
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionareZboruriView view = new GestionareZboruriView();
            view.setVisible(true);
        });
    }
}
