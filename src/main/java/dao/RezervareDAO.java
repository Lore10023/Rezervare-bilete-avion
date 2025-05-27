package dao;

import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RezervareDAO {

    public static void adaugaRezervare(String nume, String nrTel, int nrAdulti, int nrCopii, int nrSeniori,
                                       boolean masaInclusa, boolean bagajSuplimentar, boolean discount,
                                       double pretTotal, String modalitatePlata, boolean plataCacheValidata) {

        String sql = "INSERT INTO Rezervare (nume, nr_tel, nr_adulti, nr_copii, nr_seniori, masa_inclusa, " +
                "bagaj_suplimentar, discount_tur_retur_aplicat, pret_total, modalitate_plata, plata_cache_validata) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nume);
            ps.setString(2, nrTel);
            ps.setInt(3, nrAdulti);
            ps.setInt(4, nrCopii);
            ps.setInt(5, nrSeniori);
            ps.setBoolean(6, masaInclusa);
            ps.setBoolean(7, bagajSuplimentar);
            ps.setBoolean(8, discount);
            ps.setDouble(9, pretTotal);
            ps.setString(10, modalitatePlata);
            ps.setBoolean(11, plataCacheValidata);

            int rowsInserted = ps.executeUpdate(); //returnează numărul de rânduri inserate
            if (rowsInserted > 0) {
                System.out.println("Rezervarea a fost adăugată cu succes!");
            } else {
                System.out.println("Nu s-a putut adăuga rezervarea.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Eroare la inserarea rezervării: " + e.getMessage());
        }
    }
}