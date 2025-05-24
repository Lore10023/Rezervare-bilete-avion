package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanieDAO {

    public boolean autentifica(String nume, String parola) {
        String sql = "SELECT * FROM CampaniiAeriene WHERE nume = ? AND parola = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nume);
            stmt.setString(2, parola);
            ResultSet rs = stmt.executeQuery();

            return rs.next();  // dacă există un rând, autentificarea e validă

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
