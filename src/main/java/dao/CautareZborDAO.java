package dao;

import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CautareZborDAO {
    public static String cautaZboruriRetur(String plecare, String sosire, int nrPersoane) {
        StringBuilder rezultate = new StringBuilder();

        String query = "SELECT * FROM zboruri z join RezervariZboruri r on r.zbor_id=z.id WHERE z.oras_plecare = ? AND z.oras_destinatie = ? and r.tip_zbor='retur'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, plecare);
            st.setString(2, sosire);

            ResultSet rs = st.executeQuery();

            boolean gasit = false;
            while (rs.next()) {
                gasit = true;
                rezultate.append("Cod: ").append(rs.getString("cod_cursa")).append(" | ")
                        .append("Model: ").append(rs.getString("model_avion")).append(" | ")
                        .append("Tip: ").append(rs.getString("tip")).append(" | ");
            }

            if (!gasit) {
                rezultate.append("Nu există curse disponibile pentru această rută.\n");
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            rezultate.append("Eroare la căutarea zborurilor.\n");
        }

        return rezultate.toString();
    }

    public static String cautaZboruriTur(String plecare, String sosire, int nrPersoane) {
        StringBuilder rezultate = new StringBuilder();

        String query = "SELECT * FROM zboruri WHERE oras_plecare = ? AND oras_destinatie = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, plecare);
            st.setString(2, sosire);

            ResultSet rs = st.executeQuery();

            boolean gasit = false;
            while (rs.next()) {
                gasit = true;
                rezultate.append("Cod: ").append(rs.getString("cod_cursa")).append(" | ")
                        .append("Model: ").append(rs.getString("model_avion")).append(" | ")
                        .append("Tip: ").append(rs.getString("tip")).append(" | ");
            }

            if (!gasit) {
                rezultate.append("Nu există curse disponibile pentru această rută.\n");
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            rezultate.append("Eroare la căutarea zborurilor.\n");
        }

        return rezultate.toString();
    }
}