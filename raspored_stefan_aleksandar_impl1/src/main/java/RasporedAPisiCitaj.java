import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class RasporedAPisiCitaj extends RasporedA{

    public RasporedAPisiCitaj(){
        this.raspored = new Raspored();
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

    private void ispisiPDF(String path) {

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
}
