package dao;

import model.*;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
//import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


public class ZborDAO {
    public List<Zbor> getAllZboruri() {
        List<Zbor> zboruri = new ArrayList<>();
        String query = "SELECT z.*, zr.zi_saptamana, zr.ora, zs.perioada_inceput, zs.perioada_sfarsit " +
                "FROM Zboruri z " +
                "LEFT JOIN ZboruriRegulate zr ON z.id = zr.zbor_id " +
                "LEFT JOIN ZboruriSezoniere zs ON z.id = zs.zbor_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codCursa = String.valueOf(rs.getInt("cod_cursa"));
                String model = rs.getString("model_avion");
                String tip = rs.getString("tip");
                String plecare = rs.getString("oras_plecare");
                String destinatie = rs.getString("oras_destinatie");
                int zborId = rs.getInt("id");

                Map<Clasa, Integer> locuri = new HashMap<>();
                Map<Clasa, Double> tarife = new HashMap<>();
                incarcaClaseZbor(conn, zborId, locuri, tarife);

                if ("regulat".equalsIgnoreCase(tip)) {
                    List<ZiSaptamana> zile = new ArrayList<>();
                    String zi = rs.getString("zi_saptamana");
                    if (zi != null) {
                        zile.add(ZiSaptamana.valueOf(zi.toUpperCase()));
                    }

                    LocalTime ora = null;
                    Time time = rs.getTime("ora");
                    if (time != null) {
                        ora = time.toLocalTime();
                    }

                    zboruri.add(new ZboruriRegulate(codCursa, model, plecare, destinatie, locuri, tarife, zile, ora));
                } else if ("sezonier".equalsIgnoreCase(tip)) {
                    LocalDate inceput = null;
                    java.sql.Date start = rs.getDate("perioada_inceput");
                    if (start != null) {
                        inceput = start.toLocalDate();
                    }

                    LocalDate sfarsit = null;
                    java.sql.Date end = rs.getDate("perioada_sfarsit");
                    if (end != null) {
                        sfarsit = end.toLocalDate();
                    }

                    zboruri.add(new ZboruriSezoniere(codCursa, model, plecare, destinatie, locuri, tarife, inceput, sfarsit));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zboruri;
    }

    private void incarcaClaseZbor(Connection conn, int zborId,
                                  Map<Clasa, Integer> locuri,
                                  Map<Clasa, Double> tarife) throws SQLException {
        String query = "SELECT * FROM ClaseZbor WHERE zbor_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, zborId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Clasa clasa = Clasa.valueOf(rs.getString("clasa").toUpperCase());
                    locuri.put(clasa, rs.getInt("nr_locuriClasa"));
                    tarife.put(clasa, rs.getDouble("tarif"));
                }
            }
        }
    }

    public boolean stergeZbor(String codCursa) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            int zborId = -1;
            String getZborIdQuery = "SELECT id FROM Zboruri WHERE cod_cursa = ?";
            try (PreparedStatement stmt = conn.prepareStatement(getZborIdQuery)) {
                stmt.setInt(1, Integer.parseInt(codCursa));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    zborId = rs.getInt("id");
                } else {
                    return false;
                }
            }

            String deleteRezervariQuery = "DELETE FROM RezervariZboruri WHERE zbor_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteRezervariQuery)) {
                stmt.setInt(1, zborId);
                stmt.executeUpdate();
            }

            String deleteClaseQuery = "DELETE FROM ClaseZbor WHERE zbor_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteClaseQuery)) {
                stmt.setInt(1, zborId);
                stmt.executeUpdate();
            }

            String deleteRegulateQuery = "DELETE FROM ZboruriRegulate WHERE zbor_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteRegulateQuery)) {
                stmt.setInt(1, zborId);
                stmt.executeUpdate();
            }

            String deleteSezoniereQuery = "DELETE FROM ZboruriSezoniere WHERE zbor_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSezoniereQuery)) {
                stmt.setInt(1, zborId);
                stmt.executeUpdate();
            }

            String deleteZborQuery = "DELETE FROM Zboruri WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteZborQuery)) {
                stmt.setInt(1, zborId);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    conn.commit();
                    return true;
                }
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean adaugaZborRegulat(String codCursa, String modelAvion, String orasPlecare,
                                     String orasDestinatie, String clasa, int nrLocuri,
                                     double tarif, String[] zileSaptamana, String ora) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Adaugă în Zboruri
            String sqlZbor = "INSERT INTO Zboruri (companie_id, cod_cursa, model_avion, tip, oras_plecare, oras_destinatie) " +
                    "VALUES (1, ?, ?, 'regulat', ?, ?)";
            int zborId;

            try (PreparedStatement stmt = conn.prepareStatement(sqlZbor, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, Integer.parseInt(codCursa));
                stmt.setString(2, modelAvion);
                stmt.setString(3, orasPlecare);
                stmt.setString(4, orasDestinatie);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        zborId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // 2. Adaugă în ZboruriRegulate
            String sqlZborRegulat = "INSERT INTO ZboruriRegulate (zbor_id, zi_saptamana, ora) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlZborRegulat)) {
                for (String zi : zileSaptamana) {
                    stmt.setInt(1, zborId);
                    stmt.setString(2, zi.trim());
                    stmt.setTime(3, Time.valueOf(ora + ":00"));
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            // 3. Adaugă în ClaseZbor
            String sqlClasa = "INSERT INTO ClaseZbor (zbor_id, clasa, nr_locuriClasa, tarif) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlClasa)) {
                stmt.setInt(1, zborId);
                stmt.setString(2, clasa);
                stmt.setInt(3, nrLocuri);
                stmt.setDouble(4, tarif);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean adaugaZborSezonier(String codCursa, String modelAvion, String orasPlecare,
                                      String orasDestinatie, String clasa, int nrLocuri,
                                      double tarif, String perioadaInceput, String perioadaSfarsit) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Adaugă în Zboruri
            String sqlZbor = "INSERT INTO Zboruri (companie_id, cod_cursa, model_avion, tip, oras_plecare, oras_destinatie) " +
                    "VALUES (1, ?, ?, 'sezonier', ?, ?)";
            int zborId;

            try (PreparedStatement stmt = conn.prepareStatement(sqlZbor, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, Integer.parseInt(codCursa));
                stmt.setString(2, modelAvion);
                stmt.setString(3, orasPlecare);
                stmt.setString(4, orasDestinatie);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        zborId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            // 2. Adaugă în ZboruriSezoniere
            String sqlZborSezonier = "INSERT INTO ZboruriSezoniere (zbor_id, perioada_inceput, perioada_sfarsit) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlZborSezonier)) {
                stmt.setInt(1, zborId);
                stmt.setDate(2, Date.valueOf(perioadaInceput));
                stmt.setDate(3, Date.valueOf(perioadaSfarsit));
                stmt.executeUpdate();
            }

            // 3. Adaugă în ClaseZbor
            String sqlClasa = "INSERT INTO ClaseZbor (zbor_id, clasa, nr_locuriClasa, tarif) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlClasa)) {
                stmt.setInt(1, zborId);
                stmt.setString(2, clasa);
                stmt.setInt(3, nrLocuri);
                stmt.setDouble(4, tarif);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

