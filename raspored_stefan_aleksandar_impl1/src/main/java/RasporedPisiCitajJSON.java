
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RasporedPisiCitajJSON extends Raspored{


    @Override
    public boolean ucitajPodatke(String path, String configPath) throws IOException {
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean exportujPodatke(String path) throws IOException {
        return false;
    }
}
