package dao;

import model.Plata;
import model.Rezervare;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//public class RezervarePasageriDAO {
//
//    public static void adaugaRezervare(String nume, String nrTel, int nrAdulti, int nrCopii, int nrSeniori,
//                                       boolean masaInclusa, boolean bagajSuplimentar, boolean discount,
//                                       double pretTotal, String modalitatePlata, boolean plataCacheValidata) {
//
//        String sql = "INSERT INTO Rezervare (nume, nr_tel, nr_adulti, nr_copii, nr_seniori, masa_inclusa, " +
//                "bagaj_suplimentar, discount_tur_retur_aplicat, pret_total, modalitate_plata, plata_cache_validata) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, nume);
//            ps.setString(2, nrTel);
//            ps.setInt(3, nrAdulti);
//            ps.setInt(4, nrCopii);
//            ps.setInt(5, nrSeniori);
//            ps.setBoolean(6, masaInclusa);
//            ps.setBoolean(7, bagajSuplimentar);
//            ps.setBoolean(8, discount);
//            ps.setDouble(9, pretTotal);
//            ps.setString(10, modalitatePlata);
//            ps.setBoolean(11, plataCacheValidata);
//
//            int rowsInserted = ps.executeUpdate(); //returnează numărul de rânduri inserate
//            if (rowsInserted > 0) {
//                System.out.println("Rezervarea a fost adăugată cu succes!");
//            } else {
//                System.out.println("Nu s-a putut adăuga rezervarea.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Eroare la inserarea rezervării: " + e.getMessage());
//        }
//    }
//}

public class RezervarePasageriDAO {

    public static void adaugaRezervare(Rezervare rezervare, double pretFinal) {
        String sql = "INSERT INTO Rezervare (nume, nr_tel, nr_adulti, nr_copii, nr_seniori, masa_inclusa, bagaj_suplimentar, discount_tur_retur_aplicat, pret_total, modalitate_plata, plata_cache_validata) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rezervare.getNume());
            stmt.setString(2, rezervare.getNr_tel());
            stmt.setInt(3, rezervare.getAdulti());
            stmt.setInt(4, rezervare.getCopii());
            stmt.setInt(5, rezervare.getSeniori());
            stmt.setBoolean(6, rezervare.isMasa_inclusa());
            stmt.setBoolean(7, rezervare.isBagaje_suplimentare());
            stmt.setBoolean(8, rezervare.getZbor_return() != null); // discount aplicat dacă este retur
            stmt.setDouble(9, pretFinal);
            stmt.setString(10, rezervare.getPlata().name().toLowerCase());
            stmt.setBoolean(11, rezervare.getPlata() == Plata.CARD); // ex. doar card e validat

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

