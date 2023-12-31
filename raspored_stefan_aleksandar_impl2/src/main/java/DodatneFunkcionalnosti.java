import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DodatneFunkcionalnosti {

    private Map<String, String> dodaci;

    public DodatneFunkcionalnosti () {
        dodaci = new HashMap<>();
    }


    public void dodajNoviTermin(Raspored raspored, String mesto, String dan, String datum, String krajDatum, String pocetakVreme, String krajVreme) {

        if (krajDatum.isEmpty() || krajDatum == null)
            krajDatum = datum;

        LocalTime tPV = LocalTime.parse(pocetakVreme);
        LocalTime tKV = LocalTime.parse(krajVreme);
        LocalDate tDatum = LocalDate.parse(datum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate kDatum = LocalDate.parse(krajDatum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));



        for (Termin postojeciTermin : raspored.getSviTermini()) {
            LocalDate oPDatum = LocalDate.parse(postojeciTermin.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate oKDatum = LocalDate.parse(postojeciTermin.getKrajDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if((tDatum.isBefore(oKDatum) || tDatum.equals(oKDatum)) && (!kDatum.isAfter(oPDatum) || kDatum.equals(oPDatum))) {
                System.out.println(tDatum.isBefore(oKDatum) || tDatum.equals(oKDatum));
//                System.out.println("tDatum = " + tDatum.toString() + oKDatum.toString() + "tDatum.isBefore(oKDatum)");
                System.out.println("Datum if");
                if(postojeciTermin.getMesto().equals(mesto)) {
                    System.out.println("Mesto if");
                    if(tPV.isBefore(LocalTime.parse(postojeciTermin.getKrajVreme())) && tKV.isAfter(LocalTime.parse(postojeciTermin.getPocetakVreme())) ) {
                        System.out.println("Termin postoji");
                        return;
                    }
                }
            }
        }


        Termin noviTermin = new Termin(mesto, dan, datum, krajDatum, pocetakVreme, krajVreme);
        raspored.getSviTermini().add(noviTermin);
        System.out.println("Novi termin uspešno dodat u raspored.");
    }

    public Map<String, String> getDodaci() {
        return dodaci;
    }

    public void setDodaci(Map<String, String> dodaci) {
        this.dodaci = dodaci;
    }
}