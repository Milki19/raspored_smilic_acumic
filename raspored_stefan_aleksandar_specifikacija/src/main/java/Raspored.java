import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class Raspored {

    protected List<Termin> sviTermini;

    public abstract boolean ucitajPodatke(String path, String configPath) throws IOException;

    public abstract boolean exportujPodatke(String path) throws IOException;

    public List<Termin> getSviTermini() {
        if(sviTermini == null) return new ArrayList<>();
        return sviTermini;
    }

    public void setSviTermini(List<Termin> sviTermini) {
        this.sviTermini = sviTermini;
    }
}
