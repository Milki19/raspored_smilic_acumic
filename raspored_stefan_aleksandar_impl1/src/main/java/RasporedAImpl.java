import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RasporedAImpl extends RasporedA{

    public RasporedAImpl(){
        this.raspored = new Raspored();
    }


    @Override
    public void generisiSlobodneTermine(String pocetakRadnogVremena, String krajRadnogVremena, String pocetakDatum, String krajDatum) {
        LocalTime pocetak = LocalTime.parse(pocetakRadnogVremena);
        LocalTime kraj = LocalTime.parse(krajRadnogVremena);
        List<Termin> slobodniTermini = raspored.getSlobodniTermini();
        dodajDaneiMestoDatum();


        for (String d : raspored.getDani()) {
            for (String dat : raspored.getDatumi()) {
                for (String m : raspored.getMesta()) {
                    for (Termin t : raspored.getSviTermini()) {
                        LocalTime pocetniSledeci = pocetak;
                        LocalTime tPV = LocalTime.parse(t.getPocetakVreme());
                        LocalTime tKV = LocalTime.parse(t.getKrajVreme());
                        if (d.equals(t.getDan()) && m.equals(t.getMesto()) && dat.equals(t.getDatum())) {
                            if (pocetniSledeci.isBefore(tPV) && !(kraj.equals(tKV))) {
                                //String mesto, String dan, String datum, String pocetakVreme, String krajVreme
                                Termin slobodanTermin = new Termin(m, d, t.getDatum(), pocetniSledeci.toString(), tPV.toString());
                                if (!slobodniTermini.contains(slobodanTermin)) {
                                    slobodniTermini.add(slobodanTermin);

                                }
                                pocetniSledeci = tKV;
                            }
                            if (!(pocetniSledeci.isBefore(tKV) && pocetniSledeci.isAfter(tPV))) {
                                Termin slobodanTermin = new Termin(m, d, t.getDatum(), pocetniSledeci.toString(), krajRadnogVremena);
                                if (!slobodniTermini.contains(slobodanTermin)) {
                                    slobodniTermini.add(slobodanTermin);
                                }
                            }
                        }
                        pocetniSledeci = tKV;

                    }
                }
            }
        }

        slobodniTermini.removeIf(s -> s.getPocetakVreme().equals(pocetakRadnogVremena) && s.getKrajVreme().equals(krajRadnogVremena));

        List<Termin> temp = new ArrayList<>();

        for(String dat : raspored.getDatumi()){
            for(String mesto : raspored.getMesta()){
                for(Termin t : slobodniTermini){
                    if(t.getDatum().equals(dat)){
                        if(!t.getMesto().equals(mesto)){
                            //String mesto, String dan, String datum, String pocetakVreme, String krajVreme
                            Termin termin = new Termin(mesto, t.getDan(), dat, pocetakRadnogVremena, krajRadnogVremena);
                            if(!slobodniTermini.contains(termin)) {
                                if(!temp.contains(termin)) {
                                    temp.add(termin);
                                }
                            }
                        }
                    }
                }
            }
        }

        slobodniTermini.addAll(temp);

        slobodniTermini.sort(Termin.getComparator());
    }

    private void dodajDaneiMestoDatum() {
        for (Termin t : raspored.getSviTermini()) {
            if (!raspored.getDani().contains(t.getDan())) {
                raspored.getDani().add(t.getDan());
            }
            if (!raspored.getMesta().contains(t.getMesto())){
                raspored.getMesta().add(t.getMesto());
            }
            if (!raspored.getDatumi().contains(t.getDatum())) {
                raspored.getDatumi().add(t.getDatum());
            }
        }
    }
    private static List<Konfiguracija> citajKonfiguraciju(String configPath) throws FileNotFoundException {

        List<Konfiguracija> mapiranje = new ArrayList<>();

        File file = new File(configPath);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] split = line.split(" ", 3);

            mapiranje.add(new Konfiguracija(Integer.valueOf(split[0]), split[1], split[2]));
        }

        sc.close();


        return mapiranje;
    }

    @Override
    public void ucitajCSV(String path, String configPath) throws IOException {
        List<Konfiguracija> mapiranje = citajKonfiguraciju(configPath);
        Map<Integer, String> mapa = new HashMap<>();

        for (Konfiguracija konfiguracija : mapiranje) {
            mapa.put(konfiguracija.getIndex(), konfiguracija.getOriginal());
        }

        FileReader fileReader = new FileReader(path);
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

        for (CSVRecord record : csvParser) {
            Termin termin = new Termin();

            for (Konfiguracija konfiguracija : mapiranje) {
                int columnIndex = konfiguracija.getIndex();

                String columnName = konfiguracija.getCustom();

                switch (mapa.get(columnIndex)) {
                    case "mesto":
                        termin.setMesto(record.get(columnIndex));
                        break;
                    case "datum":
                        termin.setDatum(record.get(columnIndex));
                        termin.setKrajDatum(record.get(columnIndex));
                        break;
                    case "dan":
                        termin.setDan(record.get(columnIndex));
                        break;
                    case "pocetakVreme":
                        termin.setPocetakVreme(record.get(columnIndex));
                        break;
                    case "krajVreme":
                        termin.setKrajVreme(record.get(columnIndex));
                        break;
                    case "dodaci":
                        termin.getDodaci().put(columnName, record.get(columnIndex));
                        break;
                }

            }

            raspored.getSviTermini().add(termin);

        }

    }

    @Override
    public List<Termin> slobodniDatumiPocetakVremeKrajVremeDan(String pocetakDatum, String krajDatum, String pocetakVreme, String krajVreme, String dan) {
        raspored.getPretrazeno().clear();
        if(!(krajDatum.isEmpty())){
            LocalDate prviDatum = LocalDate.parse(pocetakDatum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate drugiDatum = LocalDate.parse(krajDatum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            for(Termin t : raspored.getSlobodniTermini()){
                LocalDate d1 = LocalDate.parse(t.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate d2 = LocalDate.parse(t.getKrajDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(d1.isAfter(prviDatum.minusDays(1)) && d2.isBefore(drugiDatum.plusDays(1))){
                    if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)) {
                        raspored.getPretrazeno().add(t);
                    }
                }
            }
        }else {
            for (Termin t : raspored.getSlobodniTermini()) {
                if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan) && t.getPocetakVreme().equals(pocetakVreme)) {
                    raspored.getPretrazeno().add(t);
                }
            }
        }

        for (Termin t : raspored.getPretrazeno()) {
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDatum(String datum) {

        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDatum().equals(datum)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return getRaspored().getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDatumiPocetak(String datum, String krajDatum, String pocetakVreme) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDatumiDan(String datum, String krajDatum, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDatum().equals(datum) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDatumiPocetakKraj(String datum, String krajDatum, String pocetakVreme, String krajVreme) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDatumiPocetakDan(String datum, String krajDatum, String pocetakVreme, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDatumiPocetakKrajDan(String datum, String krajDatum, String pocetakVreme, String krajVreme, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniPocetak(String pocetakVreme) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getPocetakVreme().equals(pocetakVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniPocetakKraj(String pocetakVreme, String krajVreme) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniPocetakKrajDan(String pocetakVreme, String krajVreme, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniKraj(String krajVreme) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDan(String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDatumi(String pDatum, String kDatum) {

        raspored.getPretrazeno().clear();

        LocalDate prviDatum = LocalDate.parse(pDatum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate drugiDatum = LocalDate.parse(kDatum, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for(Termin t : raspored.getSlobodniTermini()){
            LocalDate d1 = LocalDate.parse(t.getDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate d2 = LocalDate.parse(t.getKrajDatum(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if(d1.isAfter(prviDatum.minusDays(1)) && d2.isBefore(drugiDatum.plusDays(1))){
                raspored.getPretrazeno().add(t);
            }
        }

        // 09/10/2023 20/10/2023

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniDodatak(String dodatak) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            for(String v : t.getDodaci().values()){
                if(v.equals(dodatak)){
                    raspored.getPretrazeno().add(t);
                }
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMesto(String mesto) {
        raspored.getPretrazeno().clear();
        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMestoDatumi(String mesto, String datum, String krajDatum) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMestoDan(String mesto, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDan().equals(dan) && t.getMesto().equals(mesto)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMestoDatumiPocetak(String mesto, String datum, String krajDatum, String pocetakVreme) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMestoDatumiDan(String mesto, String datum, String krajDatum, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMestoPocetakDan(String mesto, String pocetakVreme, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getDan().equals(dan) && t.getMesto().equals(mesto) && t.getPocetakVreme().equals(pocetakVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMestoDatumiPocetakKraj(String mesto, String datum, String krajDatum, String pocetakVreme, String krajVreme) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> slobodniMestoDatumiPocetakKrajDan(String mesto, String datum, String krajDatum, String pocetakVreme, String krajVreme, String dan) {
        raspored.getPretrazeno().clear();

        for(Termin t : raspored.getSlobodniTermini()){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        for(Termin t : raspored.getPretrazeno()){
            System.out.println(t);
        }

        return raspored.getPretrazeno();
    }

    @Override
    public void obrisiTermin(Raspored raspored, String mesto, String dan, String datum, String pocetakVreme, String krajVreme, String dodaci) {
        DodatneFunkcionalnosti df = new DodatneFunkcionalnosti();
        Termin zaBrisanje = new Termin(mesto, dan, datum, pocetakVreme, krajVreme);

        df.obrisiTermin(raspored, zaBrisanje);
    }


    @Override
    public void dodajTermin(Raspored raspored, String mesto, String dan, String datum, String pocetakVreme, String krajVreme, String dodaci){
        DodatneFunkcionalnosti df = new DodatneFunkcionalnosti();

        df.dodajNoviTermin(raspored, mesto, dan, datum, "", pocetakVreme, krajVreme, dodaci);
    }

    @Override
    public void izmeniTermin(Raspored raspored, Termin kojiHocemoDaMenjamo, Termin saCimeMenjamo) {
        raspored.getSlobodniTermini().clear();

        saCimeMenjamo.setDodaci(kojiHocemoDaMenjamo.getDodaci());

        raspored.getSviTermini().remove(kojiHocemoDaMenjamo);
        raspored.getSviTermini().add(saCimeMenjamo);
        raspored.getSviTermini().sort(Termin.getComparator());

        generisiSlobodneTermine(raspored.pocetakRadnogVremena, raspored.krajRadnogVremena, raspored.pocetakRasporeda, raspored.krajRasporeda);
    }

    @Override
    public List<Termin> pretraziDodatak(String dodatak) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            for(String v : t.getDodaci().values()){
                if(v.equals(dodatak)){
                    raspored.getPretrazeno().add(t);
                }
            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMesto(String mesto) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto)){
                raspored.getPretrazeno().add(t);
            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMestoDatum(String mesto, String datum) {
        raspored.getPretrazeno().clear();
        for (Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum)){
                raspored.getPretrazeno().add(t);
            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMestoDan(String mesto, String dan) {
        raspored.getPretrazeno().clear();
        for (Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMestoDatumPocetak(String mesto, String datum, String pocetakVreme) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMestoDatumDan(String mesto, String datum, String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMestoPocetakDan(String mesto, String pocetakVreme, String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getPocetakVreme().equals(pocetakVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMestoDatumPocetakKraj(String mesto, String datum, String pocetakVreme, String krajVreme) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziMestoDatumPocetakKrajDan(String mesto, String datum, String pocetakVreme, String krajVreme, String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);
            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziDatum(String datum) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum)){
                raspored.getPretrazeno().add(t);

            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziDatumPocetak(String datum, String pocetakVreme) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                raspored.getPretrazeno().add(t);
            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziDatumDan(String datum, String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);

            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziDatumPocetakKraj(String datum, String pocetakVreme, String krajVreme) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);

            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziDatumPocetakDan(String datum, String pocetakVreme, String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);

            }
        }

        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziDatumPocetakKrajDan(String datum, String pocetakVreme, String krajVreme, String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);

            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziPocetak(String pocetakVreme) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme)){
                raspored.getPretrazeno().add(t);

            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziPocetakKraj(String pocetakVreme, String krajVreme) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);

            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziPocetakKrajDan(String pocetakVreme, String krajVreme, String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);

            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziKraj(String krajVreme) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getKrajVreme().equals(krajVreme)){
                raspored.getPretrazeno().add(t);

            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public List<Termin> pretraziDan(String dan) {
        raspored.getPretrazeno().clear();
        for(Termin t : getRaspored().sviTermini){
            if(t.getDan().equals(dan)){
                raspored.getPretrazeno().add(t);

            }
        }
        return raspored.getPretrazeno();
    }

    @Override
    public void exportPDF(String path) throws IOException{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.HELVETICA, 12);

        float y = 750;
        float yGranica = 100;

        contentStream.beginText();
        contentStream.newLineAtOffset(100, y);

        for (Termin t : this.raspored.getSviTermini()) {
            if (y < yGranica) {
                contentStream.endText();
                contentStream.close();

                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                y = 750;
                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
            }

            contentStream.moveTextPositionByAmount(0, -20);
            contentStream.showText("Dan: " + t.getDan());
            contentStream.showText(", Datum: " + t.getDatum());
            contentStream.showText(", Pocetak: " + t.getPocetakVreme());
            contentStream.showText(", Kraj: " + t.getKrajVreme());
            contentStream.showText(", Mesto: " + t.getMesto());

            y -= 20;
        }

        contentStream.endText();
        contentStream.close();

        System.out.println("Unesite destinaciju na kojoj zelite da bude fajl: ");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        document.save(linija + path);
        document.close();
    }



    @Override
    public void exportJSON(String path) throws IOException{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(raspored);
        System.out.println("Unesite destinaciju na kojoj zelite da bude fajl: ");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        FileWriter fw = new FileWriter(linija + path);
        fw.write(json);

        fw.close();
    }

    @Override
    public void exportCSV(String path) throws IOException {
        System.out.println("Unesite destinaciju na kojoj zelite da bude fajl: ");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        FileWriter fileWriter = new FileWriter(linija + path);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        for (Termin termin : super.raspored.getSviTermini()) {
            csvPrinter.printRecord(
                    termin.getDan() + ", "
                            + termin.getDatum() + " "
                            + termin.getPocetakVreme() + "-"
                            + termin.getKrajVreme() + "h, "
                            + termin.getMesto()
            );
        }

        csvPrinter.close();
        fileWriter.close();
    }

    @Override
    public void ucitajJSON(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        raspored = mapper.readValue(new File(path), Raspored.class);
//        Collections.sort(raspored.getSviTermini(), Comparator
//                .comparing(Termin::getDan)
//                .thenComparing(Termin::getDatum)
//                .thenComparing(Termin::getPocetakVreme));
//        dodajKrajDatum();
    }

    public void dodajKrajDatum(){
        for(Termin t : getRaspored().getSviTermini()){
            t.setKrajDatum(t.getDatum());
        }
    }

    public void ucitajNeradneDane(String s){
        int n = s.split(" ").length;
        String[] niz = s.split(" ");
        for(int i = 0; i < n; i++){
            raspored.getNeradniDani().add(niz[i]);
        }
    }

    public void ispisiNeradneDane() {
        for(String s : raspored.neradniDani){
            System.out.println(s);
        }
    }

    public void ucitajPocetakKraj(String s){
        String[] niz = s.split(" ");
        raspored.setPocetakRasporeda(niz[0]);
        raspored.setKrajRasporeda(niz[1]);
    }

    public void ucitajRadnoVreme(String s){
        String[] niz = s.split(" ");
        raspored.setPocetakRadnogVremena(niz[0]);
        raspored.setKrajRadnogVremena(niz[1]);
    }

    public Raspored getRaspored(){
        return this.raspored;
    }
}
