package dao;

import model.*;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ZborDAO {

    public List<Zbor> getAllZboruri() {
        List<Zbor> zboruri = new ArrayList<>();
        String queryZboruri = "SELECT * FROM Zboruri";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryZboruri);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int zborId = rs.getInt("id");
                int codCursaInt = rs.getInt("cod_cursa");
                String codCursa = String.valueOf(codCursaInt);
                String modelAvion = rs.getString("model_avion");
                String tip = rs.getString("tip");
                String orasPlecare = rs.getString("oras_plecare");
                String orasDestinatie = rs.getString("oras_destinatie");

                Map<Clasa, Integer> locuri = new HashMap<>();
                Map<Clasa, Double> tarife = new HashMap<>();

                String claseQuery = "SELECT * FROM ClaseZbor WHERE zbor_id = ?";
                try (PreparedStatement claseStmt = conn.prepareStatement(claseQuery)) {
                    claseStmt.setInt(1, zborId);
                    try (ResultSet claseRs = claseStmt.executeQuery()) {
                        while (claseRs.next()) {
                            Clasa clasa = Clasa.valueOf(claseRs.getString("clasa").toUpperCase());
                            locuri.put(clasa, claseRs.getInt("nr_locuriClasa"));
                            tarife.put(clasa, claseRs.getDouble("tarif"));
                        }
                    }
                }

                if ("regulat".equalsIgnoreCase(tip)) {
                    List<ZiSaptamana> zile = new ArrayList<>();
                    LocalTime ora = null;

                    String regulatQuery = "SELECT * FROM ZboruriRegulate WHERE zbor_id = ?";
                    try (PreparedStatement regStmt = conn.prepareStatement(regulatQuery)) {
                        regStmt.setInt(1, zborId);
                        try (ResultSet regRs = regStmt.executeQuery()) {
                            while (regRs.next()) {
                                zile.add(ZiSaptamana.valueOf(regRs.getString("zi_saptamana").toUpperCase()));
                                ora = regRs.getTime("ora").toLocalTime();
                            }
                        }
                    }

                    ZboruriRegulate zborRegulat = new ZboruriRegulate(codCursa, modelAvion, orasPlecare, orasDestinatie, locuri, tarife, zile, ora);
                    zboruri.add(zborRegulat);

                } else if ("sezonier".equalsIgnoreCase(tip)) {
                    LocalDate perioadaInceput = null;
                    LocalDate perioadaSfarsit = null;

                    String sezonierQuery = "SELECT * FROM ZboruriSezoniere WHERE zbor_id = ?";
                    try (PreparedStatement sezStmt = conn.prepareStatement(sezonierQuery)) {
                        sezStmt.setInt(1, zborId);
                        try (ResultSet sezRs = sezStmt.executeQuery()) {
                            if (sezRs.next()) {
                                perioadaInceput = sezRs.getDate("perioada_inceput").toLocalDate();
                                perioadaSfarsit = sezRs.getDate("perioada_sfarsit").toLocalDate();
                            }
                        }
                    }

                    ZboruriSezoniere zborSezonier = new ZboruriSezoniere(codCursa, modelAvion, orasPlecare, orasDestinatie, locuri, tarife, perioadaInceput, perioadaSfarsit);
                    zboruri.add(zborSezonier);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zboruri;
    }
}