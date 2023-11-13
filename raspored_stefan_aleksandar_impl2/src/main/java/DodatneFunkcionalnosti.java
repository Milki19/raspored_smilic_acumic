import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DodatneFunkcionalnosti {

    private Map<String, String> dodaci;

    public DodatneFunkcionalnosti () {
        dodaci = new HashMap<>();
    }


    public void dodajNoviTermin(Raspored raspored, String mesto, String dan, String datum, String krajDatum, String pocetakVreme, String krajVreme) {

        Termin termin = new Termin(mesto, dan, datum, pocetakVreme, krajVreme, dodaci);



//        termin.setMesto(mesto);
//        termin.setDatum(datum);
//        termin.setPocetakVreme(pocetakVreme);
//        termin.setKrajVreme(krajVreme);
//        termin.setDodaci(dodaci);
//
        for (Termin t : raspored.getSviTermini()) {
            if (t.equals(termin) || t.podudara(termin)) {
                System.err.println("Nije moguce dodati vas termin u raspored jer se podudara sa postojecim terminom");
                return;
            }
        }
        raspored.getSviTermini().add(termin);
    }

    public Map<String, String> getDodaci() {
        return dodaci;
    }

    public void setDodaci(Map<String, String> dodaci) {
        this.dodaci = dodaci;
    }
}
