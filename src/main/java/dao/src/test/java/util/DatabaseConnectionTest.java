package util;

import dao.CompanieDAO;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        try {
            CompanieDAO dao = new CompanieDAO();

            boolean rezultat = dao.autentifica("exempluNume", "exempluParola");
            if (rezultat) {
                System.out.println("Autentificare reușită!");
            } else {
                System.out.println("Autentificare eșuată!");
            }
        } catch (Exception e) {
            System.err.println("Eroare la autentificare: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
