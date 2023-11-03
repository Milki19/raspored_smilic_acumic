import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RasporedPisiCitaj extends Raspored{

    private String porekloFajla;

    public RasporedPisiCitaj(String porekloFajla) {
        this.porekloFajla = porekloFajla;
    }


    @Override
    public boolean ucitajPodatke(String path, String configPath) throws IOException {
        ucitajApache(path, configPath);
        return true;
    }

    private void ucitajApache(String path, String configPath) throws IOException {
        List<Konfiguracija> mapiranje = citajKonfiguraciju(configPath);
        Map<Integer, String> mapa = new HashMap<>();

        for (Konfiguracija konfiguracija : mapiranje) {
            mapa.put(konfiguracija.getIndex(), konfiguracija.getOriginal());
        }

        FileReader fileReader = new FileReader(path);
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mapa.get(-1));

        for (CSVRecord record : csvParser) {
            Termin termin = new Termin();

            for (Konfiguracija konfiguracija : mapiranje) {
                int columnIndex = konfiguracija.getIndex();

                if(columnIndex == -1) continue;

                String columnName = konfiguracija.getCustom();

                if (mapiranje.get(columnIndex).equals("mesto")) {
                    termin.setMesto(record.get(columnIndex));
                } else if (mapiranje.get(columnIndex).equals("pocetak")) {
                    LocalDateTime startDateTime = LocalDateTime.parse(record.get(columnIndex), formatter);
                    termin.setPocetak(startDateTime);
                } else if (mapiranje.get(columnIndex).equals("kraj")) {
                    LocalDateTime endDateTime = LocalDateTime.parse(record.get(columnIndex), formatter);
                    termin.setKraj(endDateTime);
                } else if (mapiranje.get(columnIndex).equals("dodatak")) {
                    termin.getDodaci().put(columnName, record.get(columnIndex));
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
                    termin.getPocetak(),
                    termin.getKraj(),
                    termin.getMesto()
            );
        }

        csvPrinter.close();
        fileWriter.close();
    }
}
