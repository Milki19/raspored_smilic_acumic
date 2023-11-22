package org.example;

import java.io.IOException;
import java.util.List;

public abstract class RasporedA {

    Raspored raspored;

    public RasporedA(){

    }

    /**
     * This method is used to readCSV file as well as config file
     * @param path This is the path to CSV file
     * @param konfig This is the path to config file
     * @throws java.io.IOException Throws IO Exception
     */
    public abstract void ucitajCSV(String path, String konfig) throws IOException;

    /**
     * This method is used to readCSV file as well as config file
     * @param path This is the path to JSON file
     * @throws java.io.IOException Throws IO Exception
     */
    public abstract void ucitajJSON(String path) throws IOException;

    /**
     * This method is used to export CSV file
     * @param path This is the name you want to pick for your to CSV file
     * @param termini This is the list of termini you want to export
     * @param location This is the path to your exported CSV file
     * @throws java.io.IOException Throws IO Exception
     */
    public abstract void exportCSV(String path, List<Termin> termini, String location) throws IOException;

    /**
     * This method is used to export to JSON file
     * @param path This is the name you want to pick for your to JSON file
     * @param termini This is the list of termini you want to export
     * @param location This is the path to your exported JSON file
     * @throws java.io.IOException Throws IO Exception
     */
    public abstract void exportJSON(String path, List<Termin> termini, String location) throws IOException;

    /**
     * This method is used to export to PDF file
     * @param path This is the name you want to pick for your to PDF file
     * @param termini This is the list of termini you want to export
     * @param location This is the path to your exported PDF file
     * @throws java.io.IOException Throws IO Exception
     */
    public abstract void exportPDF(String path, List<Termin> termini, String location) throws IOException;

    /**
     * This method is used to add new Termin to your Raspored
     * @param raspored This is the raspored you want to add your termin to
     * @param termin This is the termin you want to add to your raspored
     */
    public abstract void dodajTermin(Raspored raspored, Termin termin);

    /**
     * This method is used to change Termin in your Raspored
     * @param raspored This is the raspored you want to change your termin in
     * @param kojiHocemoDaMenjamo This is the termin you want to change
     * @param saCimeMenjamo This is the termin you want to replace your old one with
     */
    public abstract void izmeniTermin (Raspored raspored, Termin kojiHocemoDaMenjamo, Termin saCimeMenjamo);

    /**
     * This method is used to delete Termin from your Raspored
     * @param raspored This is the raspored you want to deleter your termin from
     * @param termin This is the termin you want to delete from raspored
     */
    public abstract void obrisiTermin(Raspored raspored, Termin termin);

    /**
     * This method is used to search through raspored using next parameters
     * @param dodatak This is the dodatak you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDodatak(String dodatak);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMesto(String mesto);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @param datum This is the date  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMestoDatum(String mesto, String datum);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMestoDan(String mesto, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @param datum This is the datum  you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMestoDatumPocetak(String mesto, String datum, String pocetakVreme);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @param datum This is the datum  you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMestoDatumDan(String mesto, String datum, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMestoPocetakDan(String mesto, String pocetakVreme, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @param datum This is the mesto you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param krajVreme This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMestoDatumPocetakKraj(String mesto, String datum, String pocetakVreme, String krajVreme);

    /**
     * This method is used to search through raspored using next parameters
     * @param mesto This is the mesto you want to search your raspored with
     * @param datum This is the mesto you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param krajVreme This is the dan  you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziMestoDatumPocetakKrajDan(String mesto, String datum, String pocetakVreme, String krajVreme, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param datum This is the datum you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDatum(String datum);

    /**
     * This method is used to search through raspored using next parameters
     * @param datum This is the datum you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDatumPocetak(String datum, String pocetakVreme);

    /**
     * This method is used to search through raspored using next parameters
     * @param datum This is the datum you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDatumDan(String datum, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param datum This is the datum you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param krajVreme This is the krajVreme  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDatumPocetakKraj(String datum, String pocetakVreme, String krajVreme);

    /**
     * This method is used to search through raspored using next parameters
     * @param datum This is the datum you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDatumPocetakDan(String datum, String pocetakVreme, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param datum This is the datum you want to search your raspored with
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param krajVreme This is the krajVreme  you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDatumPocetakKrajDan(String datum, String pocetakVreme, String krajVreme, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziPocetak(String pocetakVreme);

    /**
     * This method is used to search through raspored using next parameters
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param krajVreme This is the krajVreme  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziPocetakKraj(String pocetakVreme, String krajVreme);

    /**
     * This method is used to search through raspored using next parameters
     * @param pocetakVreme This is the pocetakVreme  you want to search your raspored with
     * @param krajVreme This is the krajVreme  you want to search your raspored with
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziPocetakKrajDan(String pocetakVreme, String krajVreme, String dan);

    /**
     * This method is used to search through raspored using next parameters
     * @param krajVreme This is the krajVreme  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziKraj(String krajVreme);

    /**
     * This method is used to search through raspored using next parameters
     * @param dan This is the dan  you want to search your raspored with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> pretraziDan(String dan);



    //Slobodni termini

    /**
     * This method is used to generate slobodniTermini
     * @param pocetakRadnogVremena This is the starting hours of your raspored
     * @param krajRadnogVremena This is the ending hours of your raspored
     * @param pocetakDatum This is the starting date of your raspored
     * @param krajDatum This is the ending date of your raspored
     */
    public abstract void generisiSlobodneTermine(String pocetakRadnogVremena, String krajRadnogVremena, String pocetakDatum, String krajDatum);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param pocetakDatum This is the pocetakDatum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param krajVreme This is the krajVreme you want to search your slobodniTermini with
     * @param dan This is the dan you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDatumiPocetakVremeKrajVremeDan (String pocetakDatum, String krajDatum, String pocetakVreme, String krajVreme, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param datum This is the datum you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDatum(String datum);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param pDatum This is the pocetakDatum you want to search your slobodniTermini with
     * @param kDatum This is the krajDatum you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDatumi(String pDatum, String kDatum);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param dodatak This is the dodatak you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDodatak(String dodatak);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMesto(String mesto);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMestoDatumi(String mesto, String datum, String krajDatum);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @param dan This is the dan you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMestoDan(String mesto, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMestoDatumiPocetak(String mesto, String datum, String krajDatum, String pocetakVreme);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param dan This is the dan you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMestoDatumiDan(String mesto, String datum, String krajDatum, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param dan This is the dan you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMestoPocetakDan(String mesto, String pocetakVreme, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param krajVreme This is the krajVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMestoDatumiPocetakKraj(String mesto, String datum, String krajDatum, String pocetakVreme, String krajVreme);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param mesto This is the mesto you want to search your slobodniTermini with
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param krajVreme This is the krajVreme you want to search your slobodniTermini with
     * @param dan This is the krajVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniMestoDatumiPocetakKrajDan(String mesto, String datum, String krajDatum, String pocetakVreme, String krajVreme, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDatumiPocetak(String datum, String krajDatum, String pocetakVreme);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param dan This is the pocetakVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDatumiDan(String datum, String krajDatum, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param krajVreme This is the krajVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDatumiPocetakKraj(String datum, String krajDatum, String pocetakVreme, String krajVreme);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param datum This is the datum you want to search your slobodniTermini with
     * @param krajDatum This is the krajDatum you want to search your slobodniTermini with
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param dan This is the dan you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDatumiPocetakDan(String datum, String krajDatum, String pocetakVreme, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniPocetak(String pocetakVreme);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param krajVreme This is the krajVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniPocetakKraj(String pocetakVreme, String krajVreme);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param pocetakVreme This is the pocetakVreme you want to search your slobodniTermini with
     * @param krajVreme This is the krajVreme you want to search your slobodniTermini with
     * @param dan This is the dan you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniPocetakKrajDan(String pocetakVreme, String krajVreme, String dan);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param krajVreme This is the krajVreme you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniKraj(String krajVreme);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param dan1 This is the dan1 you want to search your slobodniTermini with
     * @param dan2 This is the dan2 you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDani(String dan1, String dan2);

    /**
     * This method is used to search through slobodniTermini using next parameters
     * @param dan This is the dan you want to search your slobodniTermini with
     * @return List This returns list of your searches
     */
    public abstract List<Termin> slobodniDan(String dan);

    
    
    public Raspored getRaspored() {
        return raspored;
    }

    /**
     * This method is used to read pocetak and kraj datum when importing CSV file
     * @param linija This is string which contains pocetak and kraj
     */
    public abstract void ucitajPocetakKraj(String linija);

    public abstract void ispisiNeradneDane();


    /**
     * This method is used to read neradne dane when importing CSV file
     * @param linija This is string which contains neradne dane
     */
    public abstract void ucitajNeradneDane(String linija);

    /**
     * This method is used to read radno vreme dane when importing CSV file
     * @param linija This is string which contains radno vreme
     */
    public abstract void ucitajRadnoVreme(String linija);
}
