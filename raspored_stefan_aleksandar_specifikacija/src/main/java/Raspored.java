import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

import java.util.ArrayList;
import java.util.List;

public class Raspored {


    protected List<Termin> sviTermini;
    protected List<String> neradniDani;
    protected String pocetakRasporeda;
    protected String krajRasporeda;
    protected String pocetakRadnogVremena;
    protected String krajRadnogVremena;

    public Raspored(){
        sviTermini = new ArrayList<>();
        neradniDani = new ArrayList<>();
    }

    public List<Termin> getSviTermini() {
        return sviTermini;
    }

    public void setSviTermini(List<Termin> sviTermini) {
        this.sviTermini = sviTermini;
    }

    public List<String> getNeradniDani() {
        return neradniDani;
    }

    public void setNeradniDani(List<String> neradniDani) {
        this.neradniDani = neradniDani;
    }

    public String getPocetakRasporeda() {
        return pocetakRasporeda;
    }

    public void setPocetakRasporeda(String pocetakRasporeda) {
        this.pocetakRasporeda = pocetakRasporeda;
    }

    public String getKrajRasporeda() {
        return krajRasporeda;
    }

    public void setKrajRasporeda(String krajRasporeda) {
        this.krajRasporeda = krajRasporeda;
    }

    public String getPocetakRadnogVremena() {
        return pocetakRadnogVremena;
    }

    public void setPocetakRadnogVremena(String pocetakRadnogVremena) {
        this.pocetakRadnogVremena = pocetakRadnogVremena;
    }

    public String getKrajRadnogVremena() {
        return krajRadnogVremena;
    }

    public void setKrajRadnogVremena(String krajRadnogVremena) {
        this.krajRadnogVremena = krajRadnogVremena;
    }
}
