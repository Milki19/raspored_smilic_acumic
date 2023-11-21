package org.example;

import java.util.ArrayList;
import java.util.List;

public class Raspored {


    protected List<Termin> sviTermini;
    private List<Termin> slobodniTermini;
    protected List<String> neradniDani;
    protected String pocetakRasporeda;
    protected String krajRasporeda;
    protected String pocetakRadnogVremena;
    protected String krajRadnogVremena;
    private List<String> dani;
    private List<String> datumi;
    private List<String> mesta;

    private String pocetakDatum;
    private String krajDatum;
    private List<Termin> pretrazeno;
    public Raspored(){
        sviTermini = new ArrayList<>();
        neradniDani = new ArrayList<>();
        slobodniTermini = new ArrayList<>();
        dani = new ArrayList<>();
        datumi = new ArrayList<>();
        mesta = new ArrayList<>();
        pretrazeno = new ArrayList<>();

    }
    public String getPocetakDatum() {
        return pocetakDatum;
    }
    public void setPocetakDatum(String pocetakDatum) {
        this.pocetakDatum = pocetakDatum;
    }
    public String getKrajDatum() {
        return krajDatum;
    }
    public void setKrajDatum(String krajDatum) {
        this.krajDatum = krajDatum;
    }
    public List<Termin> getPretrazeno() {
        return pretrazeno;
    }

    public void setPretrazeno(List<Termin> pretrazeno) {
        this.pretrazeno = pretrazeno;
    }

    public List<Termin> getSlobodniTermini() {
        return slobodniTermini;
    }

    public void setSlobodniTermini(List<Termin> slobodniTermini) {
        this.slobodniTermini = slobodniTermini;
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
    public List<String> getDani() {
        return dani;
    }

    public void setDani(List<String> dani) {
        this.dani = dani;
    }

    public List<String> getDatumi() {
        return datumi;
    }

    public void setDatumi(List<String> datumi) {
        this.datumi = datumi;
    }

    public List<String> getMesta() {
        return mesta;
    }

    public void setMesta(List<String> mesta) {
        this.mesta = mesta;
    }

}