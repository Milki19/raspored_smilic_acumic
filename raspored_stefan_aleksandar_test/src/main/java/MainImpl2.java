import java.io.IOException;
import java.util.Scanner;

public class MainImpl2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite putanju do fajla: ");
        String linija = scanner.nextLine();
        String prviDeo = linija;
        System.out.println("Unesite putanju do konfiguracionog fajla: ");
        linija = scanner.nextLine();
        String drugiDeo = linija;
        String path = prviDeo + "," + drugiDeo;
        RasporedAPisiCitaj rasporedAPisiCitaj = new RasporedAPisiCitaj();
        try{
            rasporedAPisiCitaj.ucitajPodatke(path);
            System.out.println(rasporedAPisiCitaj.raspored.getSviTermini());
        }catch (IOException e){
            System.out.println("Greska pri citanju fajlova");
        }
        System.out.println("Unesite prvi i poslednji dan rasporeda u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
        linija = scanner.nextLine();
        rasporedAPisiCitaj.ucitajPocetakKraj(linija);
        System.out.println("Unesite pocetak i kraj radnog vremena u zadatom formatu, razdvajajuci ih razmakom: hh:mm");
        linija = scanner.nextLine();
        rasporedAPisiCitaj.ucitajRadnoVreme(linija);
        System.out.println("Unesite neradne dane u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
        linija = scanner.nextLine();
        rasporedAPisiCitaj.ucitajNeradneDane(linija);
        rasporedAPisiCitaj.ispisiNeradneDane();
        System.out.println("Unesite naziv izlaznog fajla");
        linija = scanner.nextLine();
        try {
            rasporedAPisiCitaj.exportujPodatke(linija);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
