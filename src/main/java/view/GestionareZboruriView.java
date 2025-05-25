package view;

import controller.ZborController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionareZboruriView extends JFrame {

    private ZborController controller;
    private JTable tabelZboruri;

    public GestionareZboruriView() {
        controller = new ZborController();
        initUI();
        incarcaDate();
    }

    private void initUI() {
        setTitle("Lista Zboruri");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] coloane = {"Cod Cursa", "Model Avion", "Oraș Plecare", "Oraș Destinație", "Tip Zbor", "Detalii"};

        DefaultTableModel model = new DefaultTableModel(coloane, 0);
        tabelZboruri = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tabelZboruri);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void incarcaDate() {
        DefaultTableModel model = (DefaultTableModel) tabelZboruri.getModel();
        model.setRowCount(0);

        List<Zbor> zboruri = controller.getAllZboruri();

        for (Zbor z : zboruri) {
            String tipZbor = (z instanceof ZboruriRegulate) ? "Regulat" : "Sezonier";
            String detalii = "";

            if (z instanceof ZboruriRegulate) {
                ZboruriRegulate zr = (ZboruriRegulate) z;
                detalii = "Zile: " + zr.getZisaptamana().toString() + ", Ora: " + zr.getOra();
            } else if (z instanceof ZboruriSezoniere) {
                ZboruriSezoniere zs = (ZboruriSezoniere) z;
                detalii = "Perioada: " + zs.getPerioada_inceput() + " - " + zs.getPerioada_finala();
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
