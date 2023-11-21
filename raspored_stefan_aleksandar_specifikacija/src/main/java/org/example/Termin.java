package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Termin{

    private String mesto;
    private String datum;
    private String krajDatum;
    private String pocetakVreme;
    private String krajVreme;
    private Map<String, String> dodaci;
    private String tipDodataka;

    private String dan;

    public Termin(){
        this.dodaci = new HashMap<>();
    }

    public Termin(String mesto, String dan, String datum, String pocetakVreme, String krajVreme){
        this.mesto = mesto;
        this.datum = datum;
        this.krajDatum = datum;
        this.pocetakVreme = pocetakVreme;
        this.krajVreme = krajVreme;
        this.dodaci = new HashMap<>();
        this.dan = dan;
    }

    public Termin(String mesto, String dan, String datum, String krajDatum, String pocetakVreme, String krajVreme){
        this.mesto = mesto;
        this.datum = datum;
        this.krajDatum = krajDatum;
        this.pocetakVreme = pocetakVreme;
        this.krajVreme = krajVreme;
        this.dodaci = new HashMap<>();
        this.dan = dan;
    }
    public Termin(String mesto, String dan, String datum, String krajDatum, String pocetakVreme, String krajVreme, Map<String, String> dodaci){
        this.mesto = mesto;
        this.datum = datum;
        this.krajDatum = krajDatum;
        this.pocetakVreme = pocetakVreme;
        this.krajVreme = krajVreme;
        this.dodaci = new HashMap<>();
        this.dan = dan;
    }

    public Termin(String mesto, String dan, String datum, String pocetakVreme, String krajVreme, Map<String, String> dodaci){
        this.mesto = mesto;
        this.datum = datum;
        this.krajDatum = datum;
        this.pocetakVreme = pocetakVreme;
        this.krajVreme = krajVreme;
        this.dodaci = dodaci;
        this.dan = dan;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getKrajDatum() {
        return krajDatum;
    }

    public void setKrajDatum(String krajDatum) {
        this.krajDatum = krajDatum;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getPocetakVreme() {
        return pocetakVreme;
    }

    public void setPocetakVreme(String pocetakVreme) {
        this.pocetakVreme = pocetakVreme;
    }

    public String getKrajVreme() {
        return krajVreme;
    }

    public void setKrajVreme(String krajVreme) {
        this.krajVreme = krajVreme;
    }

    public Map<String, String> getDodaci() {
        return dodaci;
    }

    public void setDodaci(Map<String, String> dodaci) {
        this.dodaci = dodaci;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public boolean podudara (Termin termin, Termin o) {
        //30/10/2023,11:15

        LocalTime tPV = LocalTime.parse (termin.pocetakVreme);
        LocalTime tKV = LocalTime.parse (termin.krajVreme);
        LocalTime oPV = LocalTime.parse (o.pocetakVreme);
        LocalTime oKV = LocalTime.parse (o.krajVreme);

        LocalDate tDatum = LocalDate.parse(termin.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate oDatum = LocalDate.parse(o.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));


        if (termin.getMesto().equalsIgnoreCase(o.getMesto())
        && termin.getDatum().equals(o.getDatum())
        && termin.getDan().equalsIgnoreCase(o.getDan())
        && ((termin.getPocetakVreme().equalsIgnoreCase(o.getPocetakVreme())
                && termin.getKrajVreme().equalsIgnoreCase(o.getKrajVreme()))
        || (oPV.isAfter(tPV) && oPV.isBefore(tKV))
        || (oKV.isBefore(tKV)) && oKV.isAfter(tPV))) {
            return true;
        }


        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Termin t = (Termin) obj;
        return  Objects.equals(datum, t.datum)
                && Objects.equals(pocetakVreme, t.pocetakVreme)
                && mesto != null && t.mesto != null
                && mesto.equalsIgnoreCase(t.mesto);
    }

    @Override
    public String toString() {
        return dan + ", " + datum + " " + pocetakVreme + "-" + krajVreme + "h, " + mesto;
    }


    public static Comparator<Termin> getComparator() {
        return Comparator
                .comparing((Termin termin) -> {
                    switch (termin.getDan()) {
                        case "PON":
                            return 1;
                        case "UTO":
                            return 2;
                        case "SRE":
                            return 3;
                        case "CET":
                            return 4;
                        case "PET":
                            return 5;
                        default:
                            return 6;
                    }
                })
                .thenComparing(termin -> {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate datum = LocalDate.parse(termin.getDatum(), dateFormatter);
                    return datum;
                })
                .thenComparing(Termin::getMesto)
                .thenComparing(termin -> {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime vreme = LocalTime.parse(termin.getPocetakVreme(), timeFormatter);
                    return vreme;
                })
                .thenComparing(termin -> {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime krajVreme = LocalTime.parse(termin.getKrajVreme(), timeFormatter);
                    return krajVreme;
                });
    }


}
