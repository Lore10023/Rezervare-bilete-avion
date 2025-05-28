package view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestBanRuana {
    private MainLoginView view;

    @BeforeEach
    public void setUp() {
        view = new MainLoginView();
    }

    @AfterEach
    public void tearDown() {
        view.dispose(); // Închide fereastra după fiecare test
    }

    // Test 1: verifică dacă titlul ferestrei este corect
    @Test
    @Order(1)
    public void testTitluFereastra() {
        assertEquals("Sistem Rezervări - Autentificare", view.getTitle());
    }

    // Test 2: verifică dacă dimensiunea ferestrei este cea așteptată (300x200 pixeli)
    @Test
    @Order(2)
    public void testDimensiuneFereastra() {
        Dimension expectedSize = new Dimension(300, 200);
        assertEquals(expectedSize.width, view.getSize().width);
        assertEquals(expectedSize.height, view.getSize().height);
    }

    // Test 3: verifică dacă în fereastră există exact 3 butoane
    @Test
    @Order(3)
    public void testNumarButoane() {
        int buttonCount = countButtons(view.getContentPane());
        assertEquals(3, buttonCount);
    }

    // Test 4: verifică dacă există un buton cu textul "Login Companie"
    @Test
    @Order(4)
    public void testExistaButonCompanie() {
        JButton btn = getButtonByText(view.getContentPane(), "Login Companie");
        assertNotNull(btn);
    }

    // Test 5: verifică dacă există un buton cu textul "Căutare Zboruri"
    @Test
    @Order(5)
    public void testExistaButonCautareZboruri() {
        JButton btn = getButtonByText(view.getContentPane(), "Căutare Zboruri");
        assertNotNull(btn);
    }

    private int countButtons(Container container) {
        int count = 0;
        for (Component c : container.getComponents()) {
            if (c instanceof JButton) {
                count++;
            } else if (c instanceof Container) {
                count += countButtons((Container) c);
            }
        }
        return count;
    }

    private JButton getButtonByText(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton && ((JButton) c).getText().equals(text)) {
                return (JButton) c;
            } else if (c instanceof Container) {
                JButton result = getButtonByText((Container) c, text);
                if (result != null) return result;
            }
        }
        return null;
    }
}