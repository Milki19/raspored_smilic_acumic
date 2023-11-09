import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Raspored {

    protected List<Termin> sviTermini;
    protected List<String> neradniDani;
    protected String pocetakRasporeda;
    protected String krajRasporeda;
    protected String pocetakRadnogVremena;
    protected String krajRadnogVremena;

    public abstract boolean ucitajPodatke(String path) throws IOException;

    public abstract boolean exportujPodatke(String path) throws IOException;

    public List<Termin> getSviTermini() {
        return sviTermini;
    }

    public List<String> getNeradniDani() {
        return neradniDani;
    }

    public void setPocetakRasporeda(String pocetakRasporeda) {
        this.pocetakRasporeda = pocetakRasporeda;
    }

    public void setKrajRasporeda(String krajRasporeda) {
        this.krajRasporeda = krajRasporeda;
    }

    public void setPocetakRadnogVremena(String pocetakRadnogVremena) {
        this.pocetakRadnogVremena = pocetakRadnogVremena;
    }

    public void setKrajRadnogVremena(String krajRadnogVremena) {
        this.krajRadnogVremena = krajRadnogVremena;
    }

    public String getPocetakRasporeda() {
        return pocetakRasporeda;
    }

    public String getKrajRasporeda() {
        return krajRasporeda;
    }

    public String getPocetakRadnogVremena() {
        return pocetakRadnogVremena;
    }

    public String getKrajRadnogVremena() {
        return krajRadnogVremena;
    }
}
