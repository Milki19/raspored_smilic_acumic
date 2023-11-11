
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;

public class RasporedAPisiCitajJSON extends RasporedA {

    public RasporedAPisiCitajJSON() {
        this.raspored = new Raspored();
    }

    @Override
    public boolean ucitajPodatke(String path) throws IOException {
        ucitajJackson(path);
        return true;
    }

    @Override
    public boolean exportujPodatke(String path) throws IOException {
        ispisi(path);
        return false;
    }

    private void ispisi(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
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
}
