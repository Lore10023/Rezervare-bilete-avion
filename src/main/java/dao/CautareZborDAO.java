package dao;

import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CautareZborDAO {
    public static void cautaZboruri(String plecare, String sosire) {
        String query = "SELECT * FROM zboruri WHERE oras_plecare = ? AND oras_sosire = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, plecare);
            st.setString(2, sosire);

            ResultSet rs = st.executeQuery();

            System.out.println("Zboruri găsite:");
            while (rs.next()) {
                System.out.println(rs.getString("cod_cursa") + " | " +
                        rs.getString("model_avion") + " | " +
                        rs.getString("tip"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}