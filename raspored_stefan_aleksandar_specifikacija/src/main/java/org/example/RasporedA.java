package org.example;

import java.io.IOException;
import java.util.List;

public abstract class RasporedA {

    Raspored raspored;

    public RasporedA(){

    }

    public abstract void ucitajCSV(String path, String konfig) throws IOException;
    public abstract void ucitajJSON(String path) throws IOException;

    public abstract void exportCSV(String path, List<Termin> termini, String location) throws IOException;
    public abstract void exportJSON(String path, List<Termin> termini, String location) throws IOException;
    public abstract void exportPDF(String path, List<Termin> termini, String location) throws IOException;
    public abstract void dodajTermin(Raspored raspored, Termin termin);
    public abstract void izmeniTermin (Raspored raspored, Termin kojiHocemoDaMenjamo, Termin saCimeMenjamo);
    public abstract void obrisiTermin(Raspored raspored, Termin termin);

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



    //Slobodni termini
    public abstract void generisiSlobodneTermine(String pocetakRadnogVremena, String krajRadnogVremena, String pocetakDatum, String krajDatum);
    public abstract List<Termin> slobodniDatumiPocetakVremeKrajVremeDan (String pocetakDatum, String krajDatum, String pocetakVreme, String krajVreme, String dan);
    public abstract List<Termin> slobodniDatum(String datum);
    public abstract List<Termin> slobodniDatumi(String pDatum, String kDatum);
    public abstract List<Termin> slobodniDodatak(String dodatak);
    public abstract List<Termin> slobodniMesto(String mesto);
    public abstract List<Termin> slobodniMestoDatumi(String mesto, String datum, String krajDatum);
    public abstract List<Termin> slobodniMestoDan(String mesto, String dan);
    public abstract List<Termin> slobodniMestoDatumiPocetak(String mesto, String datum, String krajDatum, String pocetakVreme);
    public abstract List<Termin> slobodniMestoDatumiDan(String mesto, String datum, String krajDatum, String dan);
    public abstract List<Termin> slobodniMestoPocetakDan(String mesto, String pocetakVreme, String dan);
    public abstract List<Termin> slobodniMestoDatumiPocetakKraj(String mesto, String datum, String krajDatum, String pocetakVreme, String krajVreme);
    public abstract List<Termin> slobodniMestoDatumiPocetakKrajDan(String mesto, String datum, String krajDatum, String pocetakVreme, String krajVreme, String dan);
    public abstract List<Termin> slobodniDatumiPocetak(String datum, String krajDatum, String pocetakVreme);
    public abstract List<Termin> slobodniDatumiDan(String datum, String krajDatum, String dan);
    public abstract List<Termin> slobodniDatumiPocetakKraj(String datum, String krajDatum, String pocetakVreme, String krajVreme);
    public abstract List<Termin> slobodniDatumiPocetakDan(String datum, String krajDatum, String pocetakVreme, String dan);
    public abstract List<Termin> slobodniPocetak(String pocetakVreme);
    public abstract List<Termin> slobodniPocetakKraj(String pocetakVreme, String krajVreme);
    public abstract List<Termin> slobodniPocetakKrajDan(String pocetakVreme, String krajVreme, String dan);
    public abstract List<Termin> slobodniKraj(String krajVreme);
    public abstract List<Termin> slobodniDani(String dan1, String dan2);
    public abstract List<Termin> slobodniDan(String dan);

    
    
    public Raspored getRaspored() {
        return raspored;
    }

    public abstract void ucitajPocetakKraj(String linija);

    public abstract void ispisiNeradneDane();

    public abstract void ucitajNeradneDane(String linija);

    public abstract void ucitajRadnoVreme(String linija);
}
