public class Provere extends RasporedAPisiCitajApache {

    public boolean proveraNeradniDan(String dan){

        for(String s : raspored.getNeradniDani()) {
            if (dan.equals(s)) {
                return false;
            }
        }
        return true;
    }



}
