import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        RasporedPisiCitajApache rasporedPisiCitajApache = new RasporedPisiCitajApache();
        RasporedPisiCitajCSV rasporedPisiCitajCSV = new RasporedPisiCitajCSV();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite putanju do fajla i konfiguracionog fajla u obliku: putanjaDoFajla,putanjaDoKonfiguracije");
        String linija = scanner.nextLine();
        try{
            rasporedPisiCitajApache.ucitajPodatke(linija.split(",")[0], linija.split(",")[1]);
            System.out.println(rasporedPisiCitajApache.getSviTermini());
        }catch (IOException e){
            System.out.println("Greska pri citanju fajlova");
            return;
        }

        System.out.println("Unesite naziv izlaznog fajla");
        linija = scanner.nextLine();
        try{
            rasporedPisiCitajApache.exportujPodatke(linija);
        }catch (IOException e){
            e.printStackTrace();
        }
        return;
    }
}
