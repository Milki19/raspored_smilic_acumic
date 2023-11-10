
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Test;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class RasporedPisiCitajJSON extends Raspored{

    public RasporedPisiCitajJSON() {
        neradniDani = new ArrayList<>();
        sviTermini = new ArrayList<>();
    }

    @Override
    public boolean ucitajPodatke(String path) throws IOException {
        ucitajJSON(path);
//        ucitajJackson(path);
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

    public void ucitajJackson(String path) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Termin termin = mapper.readValue(new File(path), Termin.class);
    }

    public void ucitajJSON(String path) throws IOException {
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

            String pocetakRasporeda = (String) jsonObject.get("pocetakRasporeda");
            System.out.println(pocetakRasporeda);
            setPocetakRasporeda(pocetakRasporeda);

            String krajRasporeda = (String) jsonObject.get("krajRasporeda");
            setKrajRasporeda(krajRasporeda);

            String pocetakRadnogVremena = (String) jsonObject.get("pocetakRadnogVremena");
            setPocetakRadnogVremena(pocetakRadnogVremena);

            String krajRadnogVremena = (String) jsonObject.get("krajRadnogVremena");
            setKrajRadnogVremena(krajRadnogVremena);

            String neradniDani = (String) jsonObject.get("neradniDani");
            String[] niz = neradniDani.split(" ");
            for(String s : niz){
                getNeradniDani().add(s);
            }

            JSONArray sviTermini = (JSONArray) jsonObject.get("sviTermini");

            Iterator<String> iterator = sviTermini.iterator();
            while(iterator.hasNext()) {
                System.out.println(iterator.next().toString());
            }



        }catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
