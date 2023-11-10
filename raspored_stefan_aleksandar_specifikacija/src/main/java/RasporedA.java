import java.io.IOException;
import java.util.List;

public abstract class RasporedA {

    Raspored raspored;

    public abstract boolean ucitajPodatke(String path) throws IOException;

    public abstract boolean exportujPodatke(String path) throws IOException;
}
