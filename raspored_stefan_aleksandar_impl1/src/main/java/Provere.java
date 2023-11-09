public class Provere extends RasporedPisiCitajApache{

    public boolean proveraNeradniDan(String dan){

        for(String s : getNeradniDani()) {
            if (dan.equals(s)) {
                return false;
            }
        }
        return true;
    }



}
