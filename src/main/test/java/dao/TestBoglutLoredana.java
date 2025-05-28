package dao;
import model.Clasa;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBoglutLoredana {
        private static ZborDAO zborDAO;

        @BeforeAll
        public static void setup() {
            zborDAO = new ZborDAO();
        }

        // 1. Test: Adăugare zbor regulat valid
        @Test
        @Order(1)
        public void testAdaugareZborRegulatValid() {
            boolean rezultat = zborDAO.adaugaZborRegulat(
                    "1006", "Airbus A320", "Bucuresti", "Madrid",
                    Clasa.ECONOMIE.name(), 120, 200.0,
                    new String[]{"LUNI"}, "10:15"
            );
            assertTrue(rezultat, "Zborul este adăugat cu succes");
        }

        // 2. Test: Adăugare zbor regulat cu cod duplicat
        @Test
        @Order(2)
        public void testAdaugareZborRegulatDuplicat() {
            boolean rezultat = zborDAO.adaugaZborRegulat(
                    "1006", "Airbus A321", "Iasi", "Berlin",
                    Clasa.BUSINESS.name(), 30, 500.0,
                    new String[]{"MARTI"}, "14:00"
            );
            assertFalse(rezultat, "Adăugarea trebuie să eșueze din cauza codului duplicat");
        }

        // 3. Test: Ștergere zbor existent
        @Test
        @Order(3)
        public void testStergereZborExistent() {
            boolean rezultat = zborDAO.stergeZbor("1006");
            assertTrue(rezultat, "Zborul cu cod 1006 este șters cu succes");
        }

        // 4. Test: Ștergere zbor deja șters
        @Test
        @Order(4)
        public void testStergereZborDejaSters() {
            boolean rezultat = zborDAO.stergeZbor("1006");
            assertFalse(rezultat, "Zborul deja șters nu ar trebui să mai existe");
        }

        // 5. Test: Ștergere zbor inexistent (cod neverosimil)
        @Test
        @Order(5)
        public void testStergereZborInexistent() {
            boolean rezultat = zborDAO.stergeZbor("999999");
            assertFalse(rezultat, "Zborul cu cod inexistent nu ar trebui să fie șters");
        }


}
