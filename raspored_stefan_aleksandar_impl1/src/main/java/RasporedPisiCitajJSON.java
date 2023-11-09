
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public void ucitajJSON(String path) throws IOException {
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

            String pocetakRasporeda = (String) jsonObject.get("pocetakRasporeda");
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
            for(Object o : sviTermini){
                Termin t = new Termin();
//                Object obj1 = parser.parse();
                JSONArray jsonArray = (JSONArray) obj;

                String mesto = (String) jsonArray.get(0);
                t.setMesto(mesto);

                String dan = (String) jsonArray.get(1);
                t.setDan(dan);

                String datum = (String) jsonArray.get(2);
                t.setDatum(datum);

                String pocetakVreme = (String) jsonArray.get(3);
                t.setPocetakVreme(pocetakVreme);

                String krajVreme = (String) jsonArray.get(4);
                t.setKrajVreme(krajVreme);

                JSONArray dodaci = (JSONArray) jsonArray.get(5);
                for(Object u : dodaci){
                    JSONArray jsonArray1 = (JSONArray) obj;

                    String predmet = (String) jsonArray1.get(0);
                    t.getDodaci().put("predmet", predmet);
                    String predavac = (String) jsonArray1.get(1);
                    t.getDodaci().put("predavac", predavac);
                    String racunar = (String) jsonArray.get(2);
                    t.getDodaci().put("racunar", racunar);

                }
            }

        }catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
