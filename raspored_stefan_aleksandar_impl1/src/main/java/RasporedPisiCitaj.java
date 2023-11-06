import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDate;
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

                if (mapiranje.get(columnIndex).equals("Mesto")) {
                    termin.setMesto(record.get(columnIndex));
                } else if (mapiranje.get(columnIndex).equals("PocetakDan")) {
                    LocalDate startDateTime = LocalDate.parse(record.get(columnIndex), formatter);
                    termin.setPocetakDan(startDateTime);
                } else if (mapiranje.get(columnIndex).equals("PocetakVreme")) {
                    LocalDate startDateTime = LocalDate.parse(record.get(columnIndex), formatter);
                    termin.setPocetakDan(startDateTime);
                } else if (mapiranje.get(columnIndex).equals("KrajDan")) {
                    LocalDate endDateTime = LocalDate.parse(record.get(columnIndex), formatter);
                    termin.setKrajDan(endDateTime);
                } else if (mapiranje.get(columnIndex).equals("KrajVreme")) {
                    LocalDate startDateTime = LocalDate.parse(record.get(columnIndex), formatter);
                    termin.setPocetakDan(startDateTime);
                } else if (mapiranje.get(columnIndex).equals("dodatak")) {
                    termin.getDodaci().put(columnName, record.get(columnIndex));
                }

                //SREDITI OVO
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
                    termin.getPocetakDan(),
                    termin.getKrajDan(),
                    termin.getMesto()
            );
        }

        csvPrinter.close();
        fileWriter.close();
    }
}
