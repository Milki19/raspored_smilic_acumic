import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Raspored {

    protected List<Termin> sviTermini;

    public abstract boolean ucitajPodatke(String path, String configPath) throws IOException;

    public abstract boolean exportujPodatke(String path) throws IOException;

    public List<Termin> getSviTermini() {
        return sviTermini;
    }

    public void setSviTermini(List<Termin> sviTermini) {
        this.sviTermini = sviTermini;
    }
}
