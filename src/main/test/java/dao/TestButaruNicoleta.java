package dao;

import model.*;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestButaruNicoleta {

    @Test
    public void testCautareZborTurFaraRetur() {
        String rezultat = CautareZborDAO.cautaZboruri("Bucuresti", "Paris",  1);
        assertNotNull(rezultat);
        assertTrue(rezultat.contains("Zbor") || !rezultat.isEmpty());
    }

    @Test
    public void testCautareZborCuRetur() {
        String tur = CautareZborDAO.cautaZboruri("Bucuresti", "Paris",  1);
        String retur = CautareZborDAO.cautaZboruri("Paris", "Bucuresti",  1);
        assertNotNull(tur);
        assertNotNull(retur);
    }

    @Test
    public void testCalculPretRezervare() {
        Map<Clasa, Integer> locuri = Map.of(
                Clasa.ECONOMIE, 100,
                Clasa.BUSINESS, 50,
                Clasa.CLASA1, 10
        );

        Map<Clasa, Double> tarife = Map.of(
                Clasa.ECONOMIE, 300.0,
                Clasa.BUSINESS, 500.0,
                Clasa.CLASA1, 800.0
        );

        Zbor tur = new ZborStandard("RO123", "Boeing", "Bucuresti", "Paris", locuri, tarife);
        Zbor retur = new ZborStandard("RO124", "Boeing", "Paris", "Bucuresti", locuri, tarife);

        Rezervare rezervare = new Rezervare("Ana", "0712345678", 1, 0, 0,
                true, false, retur, tur, Plata.CARD, Clasa.ECONOMIE, 0.1);

        double pret = rezervare.calculPretBilet();
        assertEquals(567.0, pret, 0.1); // 600 - 10%
    }

    @Test
    public void testAdaugaRezervareInDAO() {
        Map<Clasa, Integer> locuri = Map.of(
                Clasa.ECONOMIE, 100,
                Clasa.BUSINESS, 50,
                Clasa.CLASA1, 10
        );

        Map<Clasa, Double> tarife = Map.of(
                Clasa.ECONOMIE, 300.0,
                Clasa.BUSINESS, 500.0,
                Clasa.CLASA1, 800.0
        );

        Zbor tur = new ZborStandard("RO123", "Boeing", "Bucuresti", "Paris", locuri, tarife);
        Zbor retur = new ZborStandard("RO124", "Boeing", "Paris", "Bucuresti", locuri, tarife);

        Rezervare rezervare = new Rezervare("Ana", "0712345678", 1, 0, 0,
                true, false, retur, tur, Plata.CARD, Clasa.ECONOMIE, 0.1);

        double pret = rezervare.calculPretBilet();

        assertDoesNotThrow(() -> RezervarePasageriDAO.adaugaRezervare(rezervare, pret));
    }

    @Test
    public void testValidareDateCautareIncomplete() {
        boolean valid = validareDate("Bucuresti", "", "2025-06-01", "1", false, "");
        assertFalse(valid);  // Oraș sosire lipsă => invalid
    }

    // Metodă auxiliară (simulăm validarea din View pentru testare)
    private boolean validareDate(String plecare, String sosire, String data, String nr, boolean retur, String dataRetur) {
        if (plecare.isEmpty() || sosire.isEmpty() || data.isEmpty() || nr.isEmpty())
            return false;
        if (retur && dataRetur.isEmpty())
            return false;
        return true;
    }
}
