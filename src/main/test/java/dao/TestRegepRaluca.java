package test;

import model.Clasa;
import model.Zbor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestRegepRaluca {

    // Clasă simplă pentru testare
    private static class ZborDummy extends Zbor {
        public ZborDummy(String codCursa, String model, String orasPlecare, String orasDestinatie,
                         Map<Clasa, Integer> locuriDisponibile, Map<Clasa, Double> tarife) {
            super(codCursa, model, orasPlecare, orasDestinatie, locuriDisponibile, tarife);
        }
    }

    private Zbor zbor;
    private Map<Clasa, Integer> locuri;
    private Map<Clasa, Double> tarife;

    @BeforeEach
    public void setup() {
        locuri = new HashMap<>();
        locuri.put(Clasa.BUSINESS, 10);
        locuri.put(Clasa.CLASA1, 20);
        locuri.put(Clasa.ECONOMIE, 100);

        tarife = new HashMap<>();
        tarife.put(Clasa.BUSINESS, 500.0);
        tarife.put(Clasa.CLASA1, 300.0);
        tarife.put(Clasa.ECONOMIE, 150.0);

        zbor = new ZborDummy("AB123", "Boeing 737", "Bucuresti", "Paris", locuri, tarife);
    }

    @Test
    public void testGetCodCursa() {
        assertEquals("AB123", zbor.getCodCursa());
    }

    @Test
    public void testSetCodCursa() {
        zbor.setCodCursa("CD456");
        assertEquals("CD456", zbor.getCodCursa());
    }

    @Test
    public void testGetTarif() {
        assertEquals(500.0, zbor.getTarif(Clasa.BUSINESS));
        assertEquals(150.0, zbor.getTarif(Clasa.ECONOMIE));
    }

    @Test
    public void testGetLocuriDisponibile() {
        assertEquals(20, zbor.getLocuridisponibile(Clasa.CLASA1));
    }

    @Test
    public void testToStringNotNull() {
        assertNotNull(zbor.toString());
    }
}