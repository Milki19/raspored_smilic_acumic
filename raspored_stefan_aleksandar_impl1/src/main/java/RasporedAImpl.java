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
import java.time.LocalTime;
import java.util.*;

public class RasporedAImpl extends RasporedA{

    private String pocetakDatum;
    private String krajDatum;

    private List<String> dani;
    private List<String> datumi;
    private List<String> mesta;
    private List<Termin> pretrazeno;

    public List<String> getDani() {
        return dani;
    }

    public void setDani(List<String> dani) {
        this.dani = dani;
    }

    public List<String> getMesta() {
        return mesta;
    }

    public void setMesta(List<String> mesta) {
        this.mesta = mesta;
    }

    public RasporedAImpl(){
        this.raspored = new Raspored();
        this.dani = new ArrayList<>();
        this.mesta = new ArrayList<>();
        this.pretrazeno = new ArrayList<>();
        this.datumi = new ArrayList<>();
    }

    @Override
    public void generisiSlobodneTermine(String pocetakRadnogVremena, String krajRadnogVremena, String pocetakDatum, String krajDatum) {
        LocalTime pocetak = LocalTime.parse(pocetakRadnogVremena);
        LocalTime kraj = LocalTime.parse(krajRadnogVremena);
        List<Termin> slobodniTermini = raspored.getSlobodniTermini();
        dodajDaneiMestoDatum();


        for (String d : dani) {
            for (String dat : datumi) {
                for (String m : mesta) {
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

        for(String dat : datumi){
            for(String mesto : mesta){
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

    public boolean imaPodudaranje(Termin noviTermin) {
        for (Termin t : raspored.getSlobodniTermini()) {
            if (t.podudara(noviTermin, t)) {
                return true;
            }
        }
        return false;
    }


    private void dodajDaneiMestoDatum() {
        for (Termin t : raspored.getSviTermini()) {
            if (!dani.contains(t.getDan())) {
                dani.add(t.getDan());
            }
            if (!mesta.contains(t.getMesto())){
                mesta.add(t.getMesto());
            }
            if (!datumi.contains(t.getDatum())) {
                datumi.add(t.getDatum());
            }
        }
    }

    @Override
    public boolean ucitajPodatke(String path) throws IOException {

        if(path.contains(",")){
            String[] niz = path.split(",");
            ucitajCSV(niz[0], niz[1]);
        }else{
            ucitajJackson(path);
        }

        return true;
    }

    @Override
    public boolean exportujPodatke(String path) throws IOException {
        if(path.contains(".csv")){
            ispisiCSV(path);
        }else if(path.contains(".json")){
            ispisiJSON(path);
        }else if(path.contains(".pdf")){
            ispisiPDF(path);
        }else{
            //Sta ako nista ne unese?
            ispisiJSON(path);
        }
        return false;
    }

    @Override
    public List<Termin> slobodniPocetakDatumKrajDatumPocetakVremeKrajVremeDan(String pocetakDatum, String krajDatum, String pocetakVreme, String krajVrene, String dan) {
        for(Termin t : getRaspored().sviTermini){
            if(t.getDan().equals(dan)){

            }
        }
        return null;
    }


    @Override
    public void dodajTermin(){
        DodatneFunkcionalnosti df = new DodatneFunkcionalnosti();
        Scanner sc = new Scanner(System.in);

        System.out.println("Unesite obavezne podatke za (mesto, dan, pocetni datum, pocetno vreme, krajnje vreme) u sledeceom formatu: mesto DAN dd/mm/yyyy hh:mm hh:mm");
        //Raf04,PON,02/10/2022,09:15,11:00
        //Raf04 PON 22/10/2022 09:15 11:00 Poslovne aplikacije Igro Mijatovic DA
        //"Raf04","PON","02/10/2022","09:15","11:00","Poslovne aplikacije","Mijatovic Igor","DA"
        String linija = sc.nextLine();
        String[] split = linija.split(" ", 6);
        for (String s : split) {
            System.out.println(s);
        }

        df.dodajNoviTermin(getRaspored(), split[0], split[1], split[2], "", split[3], split[4], "");
    }

    @Override
    public List<Termin> pretraziDodatak(String dodatak) {
        for(Termin t : getRaspored().sviTermini){
            for(String v : t.getDodaci().values()){
                if(v.equals(dodatak)){
                    pretrazeno.add(t);
                }
            }
        }
                 
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMesto(String mesto) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto)){
                pretrazeno.add(t);
            }
        }
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMestoDatum(String mesto, String datum) {
        
        for (Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum)){
                pretrazeno.add(t);
            }
        }
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMestoDan(String mesto, String dan) {
        
        for (Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDan().equals(dan)){
                pretrazeno.add(t);
            }
        }
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMestoDatumPocetak(String mesto, String datum, String pocetakVreme) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                pretrazeno.add(t);
            }
        }
        
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMestoDatumDan(String mesto, String datum, String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getDan().equals(dan)){
                pretrazeno.add(t);
            }
        }
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMestoPocetakDan(String mesto, String pocetakVreme, String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getPocetakVreme().equals(pocetakVreme) && t.getDan().equals(dan)){
                pretrazeno.add(t);
            }
        }
           
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMestoDatumPocetakKraj(String mesto, String datum, String pocetakVreme, String krajVreme) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                pretrazeno.add(t);
            }
        }
           
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziMestoDatumPocetakKrajDan(String mesto, String datum, String pocetakVreme, String krajVreme, String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                pretrazeno.add(t);
            }
        }
              
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziDatum(String datum) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum)){
                pretrazeno.add(t);
                
            }
        }
        
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziDatumPocetak(String datum, String pocetakVreme) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                pretrazeno.add(t);
                
            }
        }
              
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziDatumDan(String datum, String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getDan().equals(dan)){
                pretrazeno.add(t);
                
            }
        }
             
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziDatumPocetakKraj(String datum, String pocetakVreme, String krajVreme) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                pretrazeno.add(t);
                
            }
        }
        
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziDatumPocetakDan(String datum, String pocetakVreme, String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getDan().equals(dan)){
                pretrazeno.add(t);
                
            }
        }
             
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziDatumPocetakKrajDan(String datum, String pocetakVreme, String krajVreme, String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                pretrazeno.add(t);
                
            }
        }   
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziPocetak(String pocetakVreme) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme)){
                pretrazeno.add(t);
                
            }
        }
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziPocetakKraj(String pocetakVreme, String krajVreme) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                pretrazeno.add(t);
                
            }
        }    
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziPocetakKrajDan(String pocetakVreme, String krajVreme, String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                pretrazeno.add(t);
                
            }
        }  
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziKraj(String krajVreme) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getKrajVreme().equals(krajVreme)){
                pretrazeno.add(t);
                
            }
        } 
        return pretrazeno;
    }

    @Override
    public List<Termin> pretraziDan(String dan) {
        
        for(Termin t : getRaspored().sviTermini){
            if(t.getDan().equals(dan)){
                pretrazeno.add(t);
                
            }
        }
        return pretrazeno;
    }

    private void ispisiPDF(String path) throws IOException{

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



    private void ispisiJSON(String path) throws IOException{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(raspored);
        System.out.println("Unesite destinaciju na kojoj zelite da bude fajl: ");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        FileWriter fw = new FileWriter(linija + path);
        fw.write(json);

        fw.close();
    }

    private void ispisiCSV(String path) throws IOException {
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

    public void ucitajJackson(String path) throws IOException{
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

    private void ucitajCSV(String path, String configPath) throws IOException {
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

    public Raspored getRaspored(){
        return this.raspored;
    }
}
