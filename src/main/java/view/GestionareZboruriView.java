//package view;
//
//import controller.ZborController;
//import model.Zbor;
//import model.ZboruriRegulate;
//import model.ZboruriSezoniere;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.List;
//import view.AdaugareZboruriView;
//
//public class GestionareZboruriView extends JFrame {
//
//    private ZborController controller;
//    private JTable tabelZboruri;
//    private JButton btnAdaugare;
//    private JButton btnStergere;
//
//    public GestionareZboruriView() {
//        controller = new ZborController();
//        initUI();
//        incarcaDate();
//    }
//
//    /**
//     * Inițializează interfața grafică a ferestrei principale.
//     */
//    private void initUI() {
//        setTitle("Gestionare Zboruri");
//        setSize(950, 450);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout(10, 10));
//
//        // Coloanele tabelului
//        String[] coloane = {"Cod Cursă", "Model Avion", "Oraș Plecare", "Oraș Destinație", "Tip Zbor", "Detalii"};
//        tabelZboruri = new JTable(new DefaultTableModel(coloane, 0));
//        JScrollPane scrollPane = new JScrollPane(tabelZboruri);
//
//        // Butoanele
//        btnAdaugare = new JButton("Adaugă Zbor");
//        btnStergere = new JButton("Șterge Zbor");
//
//        JPanel panelButoane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
//        panelButoane.add(btnAdaugare);
//        panelButoane.add(btnStergere);
//
//        // Adăugăm componentele în fereastră
//        add(scrollPane, BorderLayout.CENTER);
//        add(panelButoane, BorderLayout.SOUTH);
//
//        // Acțiune pentru butonul "Adaugă"
//        btnAdaugare.addActionListener(e -> {
//            AdaugareZboruriView adaugareView = new AdaugareZboruriView();
//            adaugareView.setVisible(true);
//
//            // Reîncărcăm datele după închiderea ferestrei de adăugare
//            adaugareView.addWindowListener(new java.awt.event.WindowAdapter() {
//                @Override
//                public void windowClosed(java.awt.event.WindowEvent e) {
//                    incarcaDate();
//                }
//            });
//        });
//
//        // TODO: Acțiunea pentru butonul de ștergere
//        // btnStergere.addActionListener(...)
//    }
//
//    /**
//     * Încarcă datele zborurilor din controller și le afișează în tabel.
//     */
//    private void incarcaDate() {
//        DefaultTableModel model = (DefaultTableModel) tabelZboruri.getModel();
//        model.setRowCount(0); // Golește tabelul
//
//        List<Zbor> zboruri = controller.getAllZboruri();
//
//        for (Zbor z : zboruri) {
//            String tipZbor = (z instanceof ZboruriRegulate) ? "Regulat" : "Sezonier";
//            String detalii = "";
//
//            if (z instanceof ZboruriRegulate zr) {
//                detalii = "Zile: " + zr.getZisaptamana().toString() + ", Ora: " + zr.getOra();
//            } else if (z instanceof ZboruriSezoniere zs) {
//                detalii = "Perioadă: " + zs.getPerioada_inceput() + " - " + zs.getPerioada_finala();
//            }
//
//            model.addRow(new Object[]{
//                    z.getCodCursa(),
//                    z.getModel(),
//                    z.getOrasPlecare(),
//                    z.getOrasDestinatie(),
//                    tipZbor,
//                    detalii
//            });
//        }
//    }
//
//    /**
//     * Punctul de intrare al aplicației pentru testare.
//     */
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GestionareZboruriView view = new GestionareZboruriView();
//            view.setVisible(true);
//        });
//    }
//}
//
//

package view;

import controller.ZborController;
import model.Zbor;
import model.ZboruriRegulate;
import model.ZboruriSezoniere;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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
                detalii = "Zile: " + zr.getZisaptamana() + ", Ora: " + zr.getOra();
            } else if (z instanceof ZboruriSezoniere zs) {
                detalii = "Perioadă: " + zs.getPerioada_inceput() + " - " + zs.getPerioada_finala();
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