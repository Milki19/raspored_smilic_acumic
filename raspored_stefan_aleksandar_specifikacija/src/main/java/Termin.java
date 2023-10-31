import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Termin {

    private LocalDateTime pocetak;
    private LocalDateTime kraj;
    private String mesto;
    private Map<String, String> dodaci;
    public Termin(){
        this.dodaci = new HashMap<>();
    }

    public Termin(LocalDateTime pocetak, LocalDateTime kraj, String mesto){
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.mesto = mesto;
        this.dodaci = new HashMap<>();
    }

    public Termin(LocalDateTime pocetak, LocalDateTime kraj, String mesto, Map<String, String> dodaci){
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.mesto = mesto;
        this.dodaci = dodaci;
    }

    public LocalDateTime getPocetak() {
        return pocetak;
    }

    public void setPocetak(LocalDateTime pocetak) {
        this.pocetak = pocetak;
    }

    public LocalDateTime getKraj() {
        return kraj;
    }

    public void setKraj(LocalDateTime kraj) {
        this.kraj = kraj;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
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
        return Objects.equals(pocetak, t.pocetak) && Objects.equals(kraj, t.kraj) && Objects.equals(mesto, t.mesto);
    }

    @Override
    public String toString() {
        return "Termin: pocetak = " + pocetak + ", kraj = " + kraj + ", mesto = " + mesto + ", dodatno = " + dodaci;
    }
}
