import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite putanju do fajla: ");
        String linija = scanner.nextLine();

        if(linija.contains(".csv")){
            String prviDeo = linija;
            System.out.println("Unesite putanju do konfiguracionog fajla: ");
            linija = scanner.nextLine();
            String drugiDeo = linija;
            String path = prviDeo + "," + drugiDeo;
            RasporedAPisiCitajApache rasporedPisiCitajApache = new RasporedAPisiCitajApache();
            try{
                rasporedPisiCitajApache.ucitajPodatke(path);
                System.out.println(rasporedPisiCitajApache.raspored.getSviTermini());
            }catch (IOException e){
                System.out.println("Greska pri citanju fajlova");
                return;
            }
            System.out.println("Unesite prvi i poslednji dan rasporeda u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
            linija = scanner.nextLine();
            rasporedPisiCitajApache.ucitajPocetakKraj(linija);
            System.out.println("Unesite pocetak i kraj radnog vremena u zadatom formatu, razdvajajuci ih razmakom: hh:mm");
            linija = scanner.nextLine();
            rasporedPisiCitajApache.ucitajRadnoVreme(linija);
            System.out.println("Unesite neradne dane u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
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
        } else {
            RasporedAPisiCitajJSON rasporedPisiCitajJSON = new RasporedAPisiCitajJSON();
            try{
                rasporedPisiCitajJSON.ucitajPodatke(linija);
                System.out.println(rasporedPisiCitajJSON.raspored.getSviTermini());
                rasporedPisiCitajJSON.exportujPodatke("raspored_stefan_aleksandar_test/resursi/noviTermini.txt");
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("Greska pri citanju fajlova");
            }
        }

    }
}
