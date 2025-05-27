package view;

import controller.PersonalController;
import dao.RezervareDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ValidareRezervariView extends JFrame {
    private final DefaultTableModel model;
    private final JTable table;
    private final PersonalController controller;

    public ValidareRezervariView(PersonalController controller) throws SQLException {
        this.controller = controller;
        setTitle("Validare plăți cash");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"ID", "Nume", "Plată", "Validat"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JTextField searchField = new JTextField(10);
        JButton searchBtn = new JButton("Caută");
        JButton logoutBtn = new JButton("Logout");
        JButton btnValidare = new JButton("Validează");

        JPanel actiuniPanel = new JPanel(new FlowLayout());
        actiuniPanel.add(new JLabel("Caută după nume:"));
        actiuniPanel.add(searchField);
        actiuniPanel.add(searchBtn);
        actiuniPanel.add(btnValidare);
        actiuniPanel.add(logoutBtn);
        add(actiuniPanel, BorderLayout.NORTH);

        searchBtn.addActionListener(e -> {
            String nume = searchField.getText();
            try {
                List<RezervareDAO> rezultate = controller.cautaRezervariDupaNume(nume);
                model.setRowCount(0);
                for (RezervareDAO r : rezultate) {
                    model.addRow(new Object[]{r.getId(), r.getNume(), r.getModalitatePlata(), r.isPlataCacheValidata() ? "Da" : "Nu"});
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        logoutBtn.addActionListener(e -> {
            this.dispose();
            new PersonalLoginView();
        });

        btnValidare.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                try {
                    controller.valideazaPlata(id);
                    refreshTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        refreshTable();
        setVisible(true);
    }

    private void refreshTable() throws SQLException {
        model.setRowCount(0);
        List<RezervareDAO> rezervari = controller.getRezervariCacheNevalidate();
        for (RezervareDAO r : rezervari) {
            model.addRow(new Object[]{r.getId(), r.getNume(), r.getModalitatePlata(), r.isPlataCacheValidata() ? "Da" : "Nu"});
        }
    }
}
