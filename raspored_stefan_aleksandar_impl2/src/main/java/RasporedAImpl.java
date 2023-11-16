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
    private List<String> mesta;
    private List<Termin> slobodniTermini;
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

    public List<Termin> getSlobodniTermini() {
        return slobodniTermini;
    }

    public void setSlobodniTermini(List<Termin> slobodniTermini) {
        this.slobodniTermini = slobodniTermini;
    }

    public RasporedAImpl(){
        this.raspored = new Raspored();
        this.dani = new ArrayList<>();
        this.mesta = new ArrayList<>();
        this.slobodniTermini = new ArrayList<>();
        this.pretrazeno = new ArrayList<>();
        //generisiSlobodneTermine("09:00", "21:00");
    }

    public void generisiSlobodneTermine(String pocetakRadnogVremena, String krajRadnogVremena, String pocetakDatum, String krajDatum) {
        LocalTime pocetak = LocalTime.parse(pocetakRadnogVremena);
        LocalTime kraj = LocalTime.parse(krajRadnogVremena);
        Duration trajanjeIntervala = Duration.ofHours(1); // Podešavanje trajanja intervala (u ovom slučaju 1 sat)
        dodajDaneiMesto();

        for (String d : dani) {
            for (String m : mesta) {
                for (Termin t : raspored.getSviTermini()) {
                    LocalTime pocetniSledeci = pocetak;
                    LocalTime tPV = LocalTime.parse(t.getPocetakVreme());
                    LocalTime tKV = LocalTime.parse(t.getKrajVreme());
                    if (d.equals(t.getDan()) && m.equals(t.getMesto())) {
                        if (pocetniSledeci.isBefore(tPV)) {
                            //String mesto, String dan, String datum, String pocetakVreme, String krajVreme
                            Termin slobodanTermin = new Termin(m, d, t.getDatum(), pocetniSledeci.toString(), tPV.toString());
                            if(!slobodniTermini.contains(slobodanTermin)){
                                if (!imaPodudaranje(slobodanTermin)) {
                                    slobodniTermini.add(slobodanTermin);
                                }
                            }
                            pocetniSledeci = tKV;
                        }
                        if (!(pocetniSledeci.isBefore(tKV) && pocetniSledeci.isAfter(tPV))) {
                            Termin slobodanTermin = new Termin(m, d, t.getDatum(), pocetniSledeci.toString(), krajRadnogVremena);
                            if(!slobodniTermini.contains(slobodanTermin)){
                                if (!imaPodudaranje(slobodanTermin)) {
                                    slobodniTermini.add(slobodanTermin);
                                }
                            }
                        }
                    }
                    pocetniSledeci = tKV;

                }
            }
        }

        slobodniTermini.sort(Termin.getComparator());
    }

//    public void generisiSlobodneTermine(String pocetakRadnogVremena, String krajRadnogVremena, String pocetakDatum, String krajDatum) {
//        LocalTime pocetak = LocalTime.parse(pocetakRadnogVremena);
//        LocalTime kraj = LocalTime.parse(krajRadnogVremena);
//        Duration trajanjeIntervala = Duration.ofHours(1);
//        dodajDaneiMesto();
//
//        for (String d : dani) {
//            for (String m : mesta) {
//                boolean slobodan = true;
//                for (Termin t : raspored.getSviTermini()) {
//                    if (d.equals(t.getDan()) && m.equals(t.getMesto())) {
//                        LocalTime tPV = LocalTime.parse(t.getPocetakVreme());
//                        LocalTime tKV = LocalTime.parse(t.getKrajVreme());
//
//                        Termin slobodanTermin;
//                        if (pocetak.isBefore(tPV)) {
//                            slobodanTermin = new Termin(m, d, t.getDatum(), pocetak.toString(), tPV.toString());
//                            if (!imaPodudaranje(slobodanTermin)) {
//                                if (slobodniTermini.contains(slobodanTermin)) {
//                                    slobodniTermini.add(slobodanTermin);
//                                }
//                            }
//                            pocetak = tKV;
//                        }
//                        if (!pocetak.isBefore(tKV) || tPV.equals(tKV)) {
//                            slobodanTermin = new Termin(m, d, t.getDatum(), pocetak.toString(), krajRadnogVremena);
//                            if (!imaPodudaranje(slobodanTermin)) {
//                                if (slobodniTermini.contains(slobodanTermin)) {
//                                    slobodniTermini.add(slobodanTermin);
//                                }
//                            }
//                            pocetak = tKV;
//                        }
//                        if (imaPodudaranje(t)) {
//                            slobodan = false;
//                        }
//                    }
//                }
//                if (slobodan) {
//                    Termin slobodanTermin = new Termin(m, d, pocetakDatum, pocetakRadnogVremena, krajRadnogVremena);
//                    if (!imaPodudaranje(slobodanTermin)) {
//                        if (slobodniTermini.contains(slobodanTermin)) {
//                            slobodniTermini.add(slobodanTermin);
//                        }
//
//                    }
//                }
//            }
//        }
//
//        slobodniTermini.sort(Termin.getComparator());
//    }

    public boolean imaPodudaranje(Termin noviTermin) {
        for (Termin t : slobodniTermini) {
            if (t.podudara(noviTermin, t)) {
                return true;
            }
        }
        return false;
    }


    private void dodajDaneiMesto() {
        for (Termin t : raspored.getSviTermini()) {
            if (!dani.contains(t.getDan())) {
                dani.add(t.getDan());
            }
            if (!mesta.contains(t.getMesto())){
                mesta.add(t.getMesto());
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
            System.out.println("Nije moguce exportovati u zadatom formatu.");
        }
        return false;
    }


    @Override
    public void proveri() {
        System.out.println("\nIzaberite po cemu zelite da pretrazite slobodne termine razvodejene razmakom:");
        System.out.println("1. Mesto");
        System.out.println("2. Pocetni datum");
        System.out.println("3. Krajnji datum");
        System.out.println("4. Pocetno vreme");
        System.out.println("5. Krajnje vreme");
        System.out.println("6. Dan\n");

        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        // 1 3 5

        String[] niz = linija.split(" ");

        int flag1 = 0;
        int flag2 = 0;
        int flag3 = 0;
        int flag4 = 0;
        int flag5 = 0;
        int flag6 = 0;

        for(int i = 0; i < niz.length; i++){
            if(niz[i].equals("1")){
                flag1 = 1;
            }
            if(niz[i].equals("2")){
                flag2 = 1;
            }
            if(niz[i].equals("3")){
                flag3 = 1;
            }
            if(niz[i].equals("4")){
                flag4 = 1;
            }
            if(niz[i].equals("5")){
                flag5 = 1;
            }
            if(niz[i].equals("6")){
                flag6 = 1;
            }
        }

        if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1 && flag6 == 1){
            System.out.println("Unesite pocetni datum, krajnji datum, pocetno vreme, krajnje vreme i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            proveri23456(split[0], split[1], split[2], split[3], split[4]);
        }

    }

    @Override
    public void proveri23456(String pocetakDatum, String krajDatum, String pocetakVreme, String krajVrene, String dan) {
        for(Termin t : getRaspored().sviTermini){
            if(t.getDan().equals(dan)){

            }
        }
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

//        df.dodajNoviTermin(getRaspored(), split[0], split[1], split[2], "", split[3], split[4], split[5]);
    }

    @Override               // POSEBNOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
    public List<Termin> pretraziDodatak(String dodatak) {
        for(Termin t : getRaspored().sviTermini){
            for(String v : t.getDodaci().values()){
                if(v.equals(dodatak)){
                    System.out.println(t + " Dodatak: " + dodatak);
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
