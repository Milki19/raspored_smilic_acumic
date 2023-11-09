import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class RasporedPisiCitajApache extends Raspored{

    public RasporedPisiCitajApache() {
        this.sviTermini = new ArrayList<>();
        this.neradniDani = new ArrayList<>();
    }

    @Override
    public boolean ucitajPodatke(String path, String configPath) throws IOException {
        ucitajApache(path, configPath);
        return true;
    }

    public void ucitajNeradneDane(String s){
        int n = s.split(" ").length;
        String[] niz = s.split(" ");
        for(int i = 0; i < n; i++){
            getNeradniDani().add(niz[i]);
        }
    }

    private void ucitajApache(String path, String configPath) throws IOException {
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

            getSviTermini().add(termin);

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
    public boolean exportujPodatke(String path) throws IOException {
        writeData(path);
        return true;
    }

    private void writeData(String path) throws IOException{
        FileWriter fileWriter = new FileWriter(path);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        for (Termin termin : super.getSviTermini()) {
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

    public void ispisiNeradneDane() {
        for(String s : neradniDani){
            System.out.println(s);
        }
    }

    public void ucitajPocetakKraj(String s){
        String[] niz = s.split(" ");
        setPocetakRasporeda(niz[0]);
        setKrajRasporeda(niz[1]);
    }

    public void ispisiOba(){
        System.out.println(getPocetakRasporeda() + " " + getKrajRasporeda());
        System.out.println(getPocetakRadnogVremena() + " " + getKrajRadnogVremena());
    }

    public void ucitajRadnoVreme(String s){
        String[] niz = s.split(" ");
        setPocetakRadnogVremena(niz[0]);
        setKrajRadnogVremena(niz[1]);
    }
}

/*
raspored_stefan_aleksandar_test/resursi/termini.csv,raspored_stefan_aleksandar_test/resursi/konfig.txt
11/11/2023 12/11/2023 31/12/2023 01/01/2024
*/