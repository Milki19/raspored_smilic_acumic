import java.io.IOException;
import java.util.List;

public abstract class RasporedA {

    Raspored raspored;

    public abstract boolean ucitajPodatke(String path) throws IOException;

    public abstract boolean exportujPodatke(String path) throws IOException;

    public abstract void dodajTermin();

    public abstract void pretrazi();
    public abstract void pretrazi1(String mesto);
    public abstract void pretrazi12(String mesto, String datum);
    public abstract void pretrazi15(String mesto, String dan);
    public abstract void pretrazi123(String mesto, String datum, String pocetakVreme);
    public abstract void pretrazi125(String mesto, String datum, String dan);
    public abstract void pretrazi135(String mesto, String pocetakVreme, String dan);
    public abstract void pretrazi1234(String mesto, String datum, String pocetakVreme, String krajVreme);
    public abstract void pretrazi12345(String mesto, String datum, String pocetakVreme, String krajVreme, String dan);
    public abstract void pretrazi2(String datum);
    public abstract void pretrazi23(String datum, String pocetakVreme);
    public abstract void pretrazi25(String datum, String dan);
    public abstract void pretrazi234(String datum, String pocetakVreme, String krajVreme);
    public abstract void pretrazi235(String datum, String pocetakVreme, String dan);
    public abstract void pretrazi2345(String datum, String pocetakVreme, String krajVreme, String dan);
    public abstract void pretrazi3(String pocetakVreme);
    public abstract void pretrazi34(String pocetakVreme, String krajVreme);
    public abstract void pretrazi345(String pocetakVreme, String krajVreme, String dan);
    public abstract void pretrazi4(String krajVreme);
    public abstract void pretrazi5(String dan);
}
