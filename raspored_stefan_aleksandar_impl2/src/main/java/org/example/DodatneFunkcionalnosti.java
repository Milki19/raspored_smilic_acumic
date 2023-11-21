package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DodatneFunkcionalnosti {

    private Map<String, String> dodaci;

    public DodatneFunkcionalnosti() {
        dodaci = new HashMap<>();
    }


    public void dodajNoviTermin(Raspored raspored, Termin termin) {

        LocalTime tPV = LocalTime.parse(termin.getPocetakVreme());
        LocalTime tKV = LocalTime.parse(termin.getKrajVreme());
        LocalDate tDatum = LocalDate.parse(termin.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));   //pocetnidatum
        LocalDate kDatum = LocalDate.parse(termin.getKrajDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));   //krajdatunnovog


        for (Termin postojeciTermin : raspored.getSviTermini()) {
            LocalDate oPDatum = LocalDate.parse(postojeciTermin.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalTime oPVreme = LocalTime.parse(postojeciTermin.getPocetakVreme());
            LocalTime oKVreme = LocalTime.parse(postojeciTermin.getKrajVreme());
            LocalDate oKDatum = LocalDate.parse(postojeciTermin.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (raspored.getNeradniDani().contains(postojeciTermin.getDatum())) {
                continue;
            }

            if ((tDatum.isBefore(oKDatum) || tDatum.equals(oKDatum)) && (kDatum.isAfter(oPDatum) || kDatum.equals(oPDatum))) {
//                System.out.println(tDatum.isBefore(oKDatum) || tDatum.equals(oKDatum));
//                System.out.println("tDatum = " + tDatum.toString() + oKDatum.toString() + "tDatum.isBefore(oKDatum)");
                System.out.println("Datum if");
                if (postojeciTermin.getMesto().equalsIgnoreCase(termin.getMesto())) {
                    System.out.println("Mesto if");
                    if (tPV.isBefore(oKVreme) && tKV.isAfter(oPVreme)) {
                        //Raf04 PON 22/10/2023 09:15 11:00
                        //Rg04 CET 25/01/2024 15:15 17:00
                        //raspored_stefan_aleksandar_test/resursi/noviTermini.json
                        System.out.println("org.example.Termin postoji");
                        return;
                    }
                }
            }
        }



        obrisiSlobodanTermin(raspored, termin);
        System.err.println(termin);
        raspored.getSviTermini().add(termin);

        System.out.println("Novi termin uspešno dodat u raspored.");
    }

    public boolean obrisiSlobodanTermin(Raspored raspored, Termin termin) {

        // RAF20 PON 02/10/2023 11:15 13:00 Igro Mijatovic

        List<Termin> slobodniTermnini = raspored.getSlobodniTermini();

        LocalTime tPV = LocalTime.parse(termin.getPocetakVreme());
        LocalTime tKV = LocalTime.parse(termin.getKrajVreme());

        for (Termin t : slobodniTermnini) {
            LocalTime oPV = LocalTime.parse(t.getPocetakVreme());
            LocalTime oKV = LocalTime.parse(t.getKrajVreme());

            if (t.getMesto().equalsIgnoreCase(termin.getMesto()) &&
                    t.getDatum().equals(termin.getDatum()) &&
                    t.getDan().equalsIgnoreCase(termin.getDan())) {

                if ((tPV.equals(oPV) || t.getPocetakVreme().equals(termin.getPocetakVreme())) && tKV.isBefore(oKV)) {
                    t.setPocetakVreme(termin.getKrajVreme());
                    System.err.println(t);
                    slobodniTermnini.remove(termin);
                    return true;
                } else if (tKV.equals(oKV) && tPV.isAfter(oPV)) {
                    t.setKrajVreme(termin.getPocetakVreme());
                    slobodniTermnini.remove(termin);
                    return true;
                } else if (tPV.equals(oPV) && tKV.equals(oKV)) {
                    slobodniTermnini.remove(t);
                    return true;
                } else if (tPV.isAfter(oPV) && tKV.isBefore(oKV)) {
                    String kraj = t.getKrajVreme();
                    t.setKrajVreme(termin.getPocetakVreme());
                    //String mesto, String dan, String datum, String pocetakVreme, String krajVreme
                    Termin noviTermin = new Termin(t.getMesto(), t.getDan(), t.getDatum(), termin.getKrajVreme(), kraj);
                    slobodniTermnini.add(noviTermin);
                    return true;
                }

            }
        }

        return false;
    }

    public void obrisiTermin(Raspored raspored, Termin termin) {

        //RAF20 PON 02/10/2023 13:15 16:00

        List<Termin> slobodniTermnini = raspored.getSlobodniTermini();
        LocalTime tPV = LocalTime.parse(termin.getPocetakVreme());
        LocalTime tKV = LocalTime.parse(termin.getKrajVreme());
        LocalTime prosaoVreme = LocalTime.parse("00:15");
        Termin prethodni = null;
        //LocalTime pretPoc = LocalTime.parse("00:00");
        boolean prosao = false;

        for (Termin t : slobodniTermnini) {
            if (termin.getMesto().equalsIgnoreCase(t.getMesto()) && termin.getDan().equalsIgnoreCase(t.getDan())) {
                System.out.println("```Prvi if" + t.getKrajVreme());
                LocalTime oPV = LocalTime.parse(t.getPocetakVreme());
                LocalTime oKV = LocalTime.parse(t.getKrajVreme());


                if (prosao && LocalTime.parse(prethodni.getKrajVreme()).getHour() == oPV.getHour()) {
                    t.setPocetakVreme(prethodni.getPocetakVreme());
                    slobodniTermnini.remove(prethodni);
                    prosao = false;
                    return;
                } else if (tPV.getHour() == oKV.getHour() && !prosao) {
                    System.out.println("```Usao u drugi if");
                    prosao = true;
                    prethodni = t;
                    System.err.println("`Prethodni: " + prethodni );
                    t.setKrajVreme(termin.getKrajVreme());
                    raspored.getSviTermini().remove(termin);
                }


            }


        }

    }


    public Map<String, String> getDodaci() {
        return dodaci;
    }

    public void setDodaci(Map<String, String> dodaci) {
        this.dodaci = dodaci;
    }
}