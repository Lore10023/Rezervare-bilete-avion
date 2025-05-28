package model;

import java.util.Map;

public class ZborStandard extends Zbor{
    public ZborStandard(String codCursa, String model, String orasPlecare, String orasDestinatie,
                        Map<Clasa, Integer> locuriDisponibile, Map<Clasa, Double> tarife) {
        super(codCursa, model, orasPlecare, orasDestinatie, locuriDisponibile, tarife);
    }
}
