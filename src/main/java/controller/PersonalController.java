package controller;

import dao.RezervareDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalController {
    private final Connection connection;

    public PersonalController(Connection connection) {
        this.connection = connection;
    }

    public boolean autentificare(String cod) throws SQLException {
        String sql = "SELECT * FROM PersonalAeroport WHERE cod_acces = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cod);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public List<RezervareDAO> getRezervariCacheNevalidate() throws SQLException {
        String sql = "SELECT id, nume, modalitate_plata, plata_cache_validata FROM Rezervare WHERE modalitate_plata = 'cache' AND plata_cache_validata = false";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<RezervareDAO> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new RezervareDAO(
                    rs.getInt("id"),
                    rs.getString("nume"),
                    rs.getString("modalitate_plata"),
                    rs.getBoolean("plata_cache_validata")
            ));
        }
        return list;
    }

    public void valideazaPlata(int id) throws SQLException {
        String sql = "UPDATE Rezervare SET plata_cache_validata = true WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public List<RezervareDAO> cautaRezervariDupaNume(String nume) throws SQLException {
        String sql = "SELECT id, nume, modalitate_plata, plata_cache_validata FROM Rezervare WHERE LOWER(nume) LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, "%" + nume.toLowerCase() + "%");
        ResultSet rs = stmt.executeQuery();
        List<RezervareDAO> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new RezervareDAO(
                    rs.getInt("id"),
                    rs.getString("nume"),
                    rs.getString("modalitate_plata"),
                    rs.getBoolean("plata_cache_validata")
            ));
        }
        return list;
    }
}
