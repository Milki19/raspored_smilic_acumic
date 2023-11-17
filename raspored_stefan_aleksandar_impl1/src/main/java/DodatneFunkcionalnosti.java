import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DodatneFunkcionalnosti {

    private Map<String, String> dodaci;

    public DodatneFunkcionalnosti () {
        dodaci = new HashMap<>();
    }


    public void dodajNoviTermin(Raspored raspored, String mesto, String dan, String datum, String krajDatum, String pocetakVreme, String krajVreme, String extrass) {

        if (krajDatum.isEmpty())
            krajDatum = datum;

        LocalTime tPV = LocalTime.parse(pocetakVreme);  //terminpocetnovreme
        LocalTime tKV = LocalTime.parse(krajVreme);     //kraj
        LocalDate tDatum = LocalDate.parse(datum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));   //pocetnidatum
        LocalDate kDatum = LocalDate.parse(krajDatum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));   //krajdatunnovog


        for (Termin postojeciTermin : raspored.getSviTermini()) {
            LocalDate oPDatum = LocalDate.parse(postojeciTermin.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            //LocalDate oKDatum = LocalDate.parse(postojeciTermin.getKrajDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime oPVreme = LocalTime.parse(postojeciTermin.getPocetakVreme());
            LocalTime oKVreme = LocalTime.parse(postojeciTermin.getKrajVreme());
            LocalDate oKDatum = LocalDate.parse(postojeciTermin.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if ((tDatum.isBefore(oKDatum) || tDatum.equals(oKDatum)) && (kDatum.isAfter(oPDatum) || kDatum.equals(oPDatum))) { // izbacio !
//                System.out.println(tDatum.isBefore(oKDatum) || tDatum.equals(oKDatum));
//                System.out.println("tDatum = " + tDatum.toString() + oKDatum.toString() + "tDatum.isBefore(oKDatum)");
                System.out.println("Datum if");
                if (postojeciTermin.getMesto().equalsIgnoreCase(mesto)) {
                    System.out.println("Mesto if");
                    if (tPV.isBefore(oKVreme) && tKV.isAfter(oPVreme)) {
                        //Raf04 PON 22/10/2023 09:15 11:00
                        //Rg04 CET 25/01/2024 15:15 17:00
                        //raspored_stefan_aleksandar_test/resursi/noviTermini.json
                        System.out.println("Termin postoji");
                        return;
                    }
                }
            }
        }

        if (!extrass.isEmpty()) {
            String[] split = extrass.split(" ");
            //Raf04 PON 22/10/2023 09:15 11:00 Poslovne aplikacije Igor Mijatovic DA
            dodaci.put(split[0], "Predmet");
            dodaci.put(split[1], "Profesor");
            dodaci.put(split[2], "Racunar");
            Termin noviTermin = new Termin(mesto, dan, datum, krajDatum, pocetakVreme, krajVreme, dodaci);
            obrisiSlobodanTermin(raspored, noviTermin);
            System.out.println(noviTermin);
            raspored.getSviTermini().add(noviTermin);
        } else {
            Termin noviTermin = new Termin(mesto, dan, datum, krajDatum, pocetakVreme, krajVreme);
            obrisiSlobodanTermin(raspored, noviTermin);
            System.out.println(noviTermin);
            raspored.getSviTermini().add(noviTermin);
        }




        System.out.println("Novi termin uspešno dodat u raspored.");
        for (Termin t : raspored.getSlobodniTermini()) {
            System.out.println(t);
        }
    }

    public boolean obrisiSlobodanTermin (Raspored raspored, Termin termin) {

        // RAF20 PON 02/10/2023 09:15 11:00 Igro Mijatovic

        List<Termin> slobodniTermnini = raspored.getSlobodniTermini();

        LocalTime tPV = LocalTime.parse(termin.getPocetakVreme());
        LocalTime tKV = LocalTime.parse(termin.getKrajVreme());

        for (Termin t : slobodniTermnini) {
            LocalTime oPV = LocalTime.parse(t.getPocetakVreme());
            LocalTime oKV = LocalTime.parse(t.getKrajVreme());

            if (t.getMesto().equals(termin.getMesto()) &&
                t.getDatum().equals(termin.getDatum()) &&
                t.getDan().equals(termin.getDan())) {
                System.err.println("PRVI IF");

                if ((tPV.equals(oPV) || t.getPocetakVreme().equals(termin.getPocetakVreme()))&& tKV.isBefore(oKV)) {
                    System.err.println("tPV.equals(oPV) && tKV.isBefore(oKV)");
                    t.setPocetakVreme(termin.getKrajVreme());
                    System.err.println(t);
                    slobodniTermnini.remove(termin);
                    return true;
                }else if (tKV.equals(oKV) && tPV.isAfter(oPV)) {
                    t.setKrajVreme(termin.getPocetakVreme());
                    System.err.println(t);
                    slobodniTermnini.remove(termin);
                    return true;
                }else if (tPV.equals(oPV) && tKV.equals(oKV)) {
                    System.err.println(t);
                    slobodniTermnini.remove(t);
                    return true;
                }else if (tPV.isAfter(oPV) && tKV.isBefore(oKV)) {

                }

            }
        }

        //raspored.getSlobodniTermini().remove(termin);
        return false;
    }


    public Map<String, String> getDodaci() {

        return dodaci;
    }

    public void setDodaci(Map<String, String> dodaci) {
        this.dodaci = dodaci;
    }
}