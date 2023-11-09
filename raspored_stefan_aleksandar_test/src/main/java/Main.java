import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        RasporedPisiCitajApache rasporedPisiCitajApache = new RasporedPisiCitajApache();

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

        if(linija.contains(".csv")) {
            System.out.println("Unesite prvi i poslednji dan rasporeda u zadatom formatu, razdvajajuci ih razmakom: mm/dd/yyyy");
            linija = scanner.nextLine();
            rasporedPisiCitajApache.ucitajPocetakKraj(linija);
            System.out.println("Unesite pocetak i kraj radnog vremena u zadatom formatu, razdvajajuci ih razmakom: hh:mm");
            linija = scanner.nextLine();
            rasporedPisiCitajApache.ucitajRadnoVreme(linija);
            rasporedPisiCitajApache.ispisiOba();
            System.out.println("Unesite neradne dane u zadatom formatu, razdvajajuci ih razmakom: mm/dd/yyyy");
            linija = scanner.nextLine();
            rasporedPisiCitajApache.ucitajNeradneDane(linija);
            rasporedPisiCitajApache.ispisiNeradneDane();

            System.out.println("Unesite naziv izlaznog fajla");
            linija = scanner.nextLine();
            try {
                rasporedPisiCitajApache.exportujPodatke(linija);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

        }
    }
}
