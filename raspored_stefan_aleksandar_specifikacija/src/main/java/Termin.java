import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Termin {

    private String mesto;
    private String datum;
    private String krajDatum;
    private String pocetakVreme;
    private String krajVreme;
    private Map<String, String> dodaci;

    private String dan;
    // u dodatke moze da spada i predmet koji se predaje
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

        System.out.println("Termin.datum = " + termin.getDatum() + "\n this.datum = " + o.getDatum());

        //Ovo moze ako je drugacije mesto
        if (tDatum.isEqual(oDatum) && ((tPV.isBefore(oKV) && tKV.isAfter(oPV)) || (oPV.isBefore(tKV) && oKV.isAfter(tPV)))) {
            return true;
        }

        if (termin.getMesto().equalsIgnoreCase(o.getMesto())) {
            return true;
        }

        if (termin.getDatum().equals(o.getDatum())) {
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
}
