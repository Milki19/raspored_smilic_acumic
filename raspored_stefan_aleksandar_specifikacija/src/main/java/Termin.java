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

    public Termin(String mesto, String dan, String datum, String pocetakVreme, String krajVreme, Map<String, String> dodaci){
        this.mesto = mesto;
        this.datum = datum;
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

    public boolean podudara (Termin termin) {
        //30/10/2023,11:15
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy,hh:mm");

        Date oPV = new Date();
        Date oKV = new Date();
        Date tPV = new Date();
        Date tKV = new Date();

        try {
            oPV = dateFormat.parse(this.getDatum() + "," + this.getPocetakVreme());
            oKV = dateFormat.parse(this.getDatum() + "," +this.getKrajVreme());
            tPV = dateFormat.parse(termin.getDatum() + "," +termin.getPocetakVreme());
            tKV = dateFormat.parse(termin.getDatum()+ "," +termin.getKrajVreme());
        }catch (Exception e) {
            e.printStackTrace();
        }


        if (tPV.getTime() > tKV.getTime()) {
            System.err.println("Niste uneli dobro vreme, pocetak mora biti pre kraja");
            return true;
        }

//        System.out.println("tPV.getTime() > oPV.getTime()" + (tPV.getTime() > oPV.getTime()));
//        System.out.println("tPV.getTime() < oKV.getTime()" + (tPV.getTime() < oKV.getTime()));
//        System.out.println("tKV.getTime() > oPV.getTime()" + (tKV.getTime() > oPV.getTime()));
//        System.out.println("tKV.getTime() < oKV.getTime()" + (tKV.getTime() < oKV.getTime()));

        if (((tPV.getTime() > oPV.getTime() &&
                tPV.getTime() < oKV.getTime()) ||
                (tKV.getTime() > oPV.getTime() &&
                        tKV.getTime() < oKV.getTime())) &&
                termin.getMesto().equalsIgnoreCase(this.mesto) &&
                termin.getDatum().equals(this.datum)) {
            return true;
        }


        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Termin t = (Termin) obj;
        return Objects.equals(datum, t.datum) && Objects.equals(pocetakVreme, t.pocetakVreme) && Objects.equals(mesto.toUpperCase(), t.mesto.toUpperCase());
    }

    @Override
    public String toString() {
        return dan + ", " + datum + " " + pocetakVreme + "-" + krajVreme + "h, " + mesto;
    }
}
