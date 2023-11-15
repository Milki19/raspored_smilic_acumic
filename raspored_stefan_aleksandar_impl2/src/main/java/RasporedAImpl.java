import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class RasporedAImpl extends RasporedA  {

    private String pocetakDatum;
    private String krajDatum;

    public RasporedAImpl() {
        this.raspored = new Raspored();
    }

    @Override
    public boolean ucitajPodatke(String path) throws IOException {
        String[] niz = path.split(",");
        ucitajApache(niz[0], niz[1]);
        return true;
    }

    public void ucitajNeradneDane(String s){
        int n = s.split(" ").length;
        String[] niz = s.split(" ");
        for(int i = 0; i < n; i++){
            raspored.getNeradniDani().add(niz[i]);
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

    private void ucitajApache(String path, String configPath) throws IOException{
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
                        termin.setDatum(pocetakDatum);
                        termin.setKrajDatum(krajDatum);
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
    public boolean exportujPodatke(String path) throws IOException {
        ispisi(path);
        return true;
    }

    private void ispisi(String path) throws IOException{
        FileWriter fileWriter = new FileWriter(path);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        for (Termin termin : super.raspored.getSviTermini()) {
            csvPrinter.printRecord(
                    termin.getDan() + ", "
                            + termin.getKrajDatum() + ","
                            + termin.getPocetakVreme() + "-"
                            + termin.getKrajVreme() + "h, "
                            + termin.getMesto() + ", "
                            + termin.getDatum()
            );
        }

        csvPrinter.close();
        fileWriter.close();
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

    @Override
    public void interakcija(){
        System.out.println("Izabite sta sledece zelite da uradite, tako sto napisete broj koji se nalazi ispred: \n");
        System.out.println("1. Dodati termin\n");
        System.out.println("2. Izbrisati termin\n");
        System.out.println("3. Izmeniti termin\n");
        System.out.println("4. Pretraziti termine\n");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        if(linija.equals("1")){
            dodajTermin();
        }
    }

    @Override
    public void dodajTermin(){
        DodatneFunkcionalnosti df = new DodatneFunkcionalnosti();
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        System.out.println("Unesite podatke u sledeceom formatu: Mesto,Dan,Datum,PocetakVreme,KrajVreme");
        //Raf04,PON,02/10/2022,09:15,11:00
        //"Raf04","PON","02/10/2022","09:15","11:00","Poslovne aplikacije","Mijatovic Igor","DA"
        linija = sc.nextLine();
        String[] split = linija.split(",", 6);
        for (String s : split) {
            System.out.println(s);
        }

        //df.napraviDodtne(split[5]);
        df.dodajNoviTermin(getRaspored(), split[0], split[1], split[2], "", split[3], split[4]);
    }

    @Override
    public void pretrazi() {

    }

    @Override
    public void pretrazi1(String mesto) {

    }

    @Override
    public void pretrazi12(String mesto, String datum) {

    }

    @Override
    public void pretrazi15(String mesto, String dan) {

    }

    @Override
    public void pretrazi123(String mesto, String datum, String pocetakVreme) {

    }

    @Override
    public void pretrazi125(String mesto, String datum, String dan) {

    }

    @Override
    public void pretrazi135(String mesto, String pocetakVreme, String dan) {

    }

    @Override
    public void pretrazi1234(String mesto, String datum, String pocetakVreme, String krajVreme) {

    }

    @Override
    public void pretrazi12345(String mesto, String datum, String pocetakVreme, String krajVreme, String dan) {

    }

    @Override
    public void pretrazi2(String datum) {

    }

    @Override
    public void pretrazi23(String datum, String pocetakVreme) {

    }

    @Override
    public void pretrazi25(String datum, String dan) {

    }

    @Override
    public void pretrazi234(String datum, String pocetakVreme, String krajVreme) {

    }

    @Override
    public void pretrazi235(String datum, String pocetakVreme, String dan) {

    }

    @Override
    public void pretrazi2345(String datum, String pocetakVreme, String krajVreme, String dan) {

    }

    @Override
    public void pretrazi3(String pocetakVreme) {

    }

    @Override
    public void pretrazi34(String pocetakVreme, String krajVreme) {

    }

    @Override
    public void pretrazi345(String pocetakVreme, String krajVreme, String dan) {

    }

    @Override
    public void pretrazi4(String krajVreme) {

    }

    @Override
    public void pretrazi5(String dan) {

    }

    @Override
    public void proveri() {

    }

    @Override
    public void proveri23456(String pocetakDatum, String krajDatum, String pocetakVreme, String krajVrene, String dan) {

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

    public Raspored getRaspored () {
        return this.raspored;
    }
}


