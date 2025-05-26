package view;

import controller.ZborController;
import model.Zbor;
import model.ZboruriRegulate;
import model.ZboruriSezoniere;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import view.AdaugareZboruriView;

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

    /**
     * Inițializează interfața grafică a ferestrei principale.
     */
    private void initUI() {
        setTitle("Gestionare Zboruri");
        setSize(950, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Coloanele tabelului
        String[] coloane = {"Cod Cursă", "Model Avion", "Oraș Plecare", "Oraș Destinație", "Tip Zbor", "Detalii"};
        tabelZboruri = new JTable(new DefaultTableModel(coloane, 0));
        JScrollPane scrollPane = new JScrollPane(tabelZboruri);

        // Butoanele
        btnAdaugare = new JButton("Adaugă Zbor");
        btnStergere = new JButton("Șterge Zbor");

        JPanel panelButoane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelButoane.add(btnAdaugare);
        panelButoane.add(btnStergere);

        // Adăugăm componentele în fereastră
        add(scrollPane, BorderLayout.CENTER);
        add(panelButoane, BorderLayout.SOUTH);

        // Acțiune pentru butonul "Adaugă"
        btnAdaugare.addActionListener(e -> {
            AdaugareZboruriView adaugareView = new AdaugareZboruriView();
            adaugareView.setVisible(true);

            // Reîncărcăm datele după închiderea ferestrei de adăugare
            adaugareView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    incarcaDate();
                }
            });
        });

        // TODO: Acțiunea pentru butonul de ștergere
        // btnStergere.addActionListener(...)
    }

    /**
     * Încarcă datele zborurilor din controller și le afișează în tabel.
     */
    private void incarcaDate() {
        DefaultTableModel model = (DefaultTableModel) tabelZboruri.getModel();
        model.setRowCount(0); // Golește tabelul

        List<Zbor> zboruri = controller.getAllZboruri();

        for (Zbor z : zboruri) {
            String tipZbor = (z instanceof ZboruriRegulate) ? "Regulat" : "Sezonier";
            String detalii = "";

            if (z instanceof ZboruriRegulate zr) {
                detalii = "Zile: " + zr.getZisaptamana().toString() + ", Ora: " + zr.getOra();
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

    /**
     * Punctul de intrare al aplicației pentru testare.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestionareZboruriView view = new GestionareZboruriView();
            view.setVisible(true);
        });
    }
}

