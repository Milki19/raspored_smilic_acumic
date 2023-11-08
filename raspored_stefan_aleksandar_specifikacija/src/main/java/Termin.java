import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Termin {

    private String mesto;
    private String pocetakDan;
    private String pocetakVreme;
    private String krajDan;
    private String krajVreme;
    private Map<String, String> dodaci;
    // u dodatke moze da spada i predmet koji se predaje
    public Termin(){
        this.dodaci = new HashMap<>();
    }

    public Termin(String mesto, String pocetakDan, String pocetakVreme, String krajDan, String krajVreme){
        this.mesto = mesto;
        this.pocetakDan = pocetakDan;
        this.pocetakVreme = pocetakVreme;
        this.krajDan = krajDan;
        this.krajVreme = krajVreme;
        this.dodaci = new HashMap<>();
    }

    public Termin(String mesto, String pocetakDan, String pocetakVreme, String krajDan, String krajVreme, Map<String, String> dodaci){
        this.mesto = mesto;
        this.pocetakDan = pocetakDan;
        this.pocetakVreme = pocetakVreme;
        this.krajDan = krajDan;
        this.krajVreme = krajVreme;
        this.dodaci = dodaci;
    }

    public String getPocetakDan() {
        return pocetakDan;
    }

    public void setPocetakDan(String pocetakDan) {
        this.pocetakDan = pocetakDan;
    }

    public String getKrajDan() {
        return krajDan;
    }

    public void setKrajDan(String krajDan) {
        this.krajDan = krajDan;
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Termin t = (Termin) obj;
        return Objects.equals(pocetakDan, t.pocetakDan) && Objects.equals(krajDan, t.krajDan) && Objects.equals(mesto, t.mesto);
    }

    @Override
    public String toString() {
        return "Termin: pocetak = " + pocetakDan + ", kraj = " + krajDan + ", mesto = " + mesto + ", dodatno = " + dodaci;
    }
}
