import java.io.IOException;
import java.util.List;

public abstract class RasporedA {

    Raspored raspored;

    public abstract boolean ucitajPodatke(String path) throws IOException;

    public abstract boolean exportujPodatke(String path) throws IOException;

    public abstract void dodajTermin();
    
    public abstract List<Termin> pretraziDodatak(String dodatak);
    public abstract List<Termin> pretraziMesto(String mesto);
    public abstract List<Termin> pretraziMestoDatum(String mesto, String datum);
    public abstract List<Termin> pretraziMestoDan(String mesto, String dan);
    public abstract List<Termin> pretraziMestoDatumPocetak(String mesto, String datum, String pocetakVreme);
    public abstract List<Termin> pretraziMestoDatumDan(String mesto, String datum, String dan);
    public abstract List<Termin> pretraziMestoPocetakDan(String mesto, String pocetakVreme, String dan);
    public abstract List<Termin> pretraziMestoDatumPocetakKraj(String mesto, String datum, String pocetakVreme, String krajVreme);
    public abstract List<Termin> pretraziMestoDatumPocetakKrajDan(String mesto, String datum, String pocetakVreme, String krajVreme, String dan);
    public abstract List<Termin> pretraziDatum(String datum);
    public abstract List<Termin> pretraziDatumPocetak(String datum, String pocetakVreme);
    public abstract List<Termin> pretraziDatumDan(String datum, String dan);
    public abstract List<Termin> pretraziDatumPocetakKraj(String datum, String pocetakVreme, String krajVreme);
    public abstract List<Termin> pretraziDatumPocetakDan(String datum, String pocetakVreme, String dan);
    public abstract List<Termin> pretraziDatumPocetakKrajDan(String datum, String pocetakVreme, String krajVreme, String dan);
    public abstract List<Termin> pretraziPocetak(String pocetakVreme);
    public abstract List<Termin> pretraziPocetakKraj(String pocetakVreme, String krajVreme);
    public abstract List<Termin> pretraziPocetakKrajDan(String pocetakVreme, String krajVreme, String dan);
    public abstract List<Termin> pretraziKraj(String krajVreme);
    public abstract List<Termin> pretraziDan(String dan);

    public abstract void proveri();
    public abstract void proveri23456(String pocetakDatum, String krajDatum, String pocetakVreme, String krajVrene, String dan);

}
