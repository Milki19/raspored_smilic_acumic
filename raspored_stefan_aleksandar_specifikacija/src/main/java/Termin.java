import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Termin {

    private String mesto;
    private String datum;
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Termin t = (Termin) obj;
        return Objects.equals(datum, t.datum) && Objects.equals(pocetakVreme, t.pocetakVreme) && Objects.equals(mesto, t.mesto);
    }

    @Override
    public String toString() {
        return dan + ", " + datum + " " + pocetakVreme + "-" + krajVreme + "h, " + mesto;
    }
}
