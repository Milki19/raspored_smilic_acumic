import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite putanju do fajla: ");
        String linija = scanner.nextLine();

        if(linija.contains(",")){
            RasporedPisiCitajApache rasporedPisiCitajApache = new RasporedPisiCitajApache();
            try{
                rasporedPisiCitajApache.ucitajPodatke(linija);
                System.out.println(rasporedPisiCitajApache.getSviTermini());
            }catch (IOException e){
                System.out.println("Greska pri citanju fajlova");
                return;
            }
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
        } else {
            RasporedPisiCitajJSON rasporedPisiCitajJSON = new RasporedPisiCitajJSON();
            try{
                rasporedPisiCitajJSON.ucitajPodatke(linija);
                System.out.println(rasporedPisiCitajJSON.getSviTermini());
            }catch (IOException e){
                System.out.println("Greska pri citanju fajlova");
            }
        }

    }
}
