import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite putanju do fajla: ");
        String linija = scanner.nextLine();
        RasporedAImpl rasporedPisiCitaj = new RasporedAImpl();


        if(linija.contains(".csv")){
            String prviDeo = linija;
            System.out.println("Unesite putanju do konfiguracionog fajla: ");
            linija = scanner.nextLine();
            String drugiDeo = linija;
            String path = prviDeo + "," + drugiDeo;

            try{
                rasporedPisiCitaj.ucitajPodatke(path);
                System.out.println(rasporedPisiCitaj.raspored.getSviTermini());
            }catch (IOException e){
                System.out.println("Greska pri citanju fajlova");
                return;
            }
            System.out.println("Unesite prvi i poslednji dan rasporeda u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
            linija = scanner.nextLine();
            rasporedPisiCitaj.ucitajPocetakKraj(linija);
            System.out.println("Unesite pocetak i kraj radnog vremena u zadatom formatu, razdvajajuci ih razmakom: hh:mm");
            linija = scanner.nextLine();
            rasporedPisiCitaj.ucitajRadnoVreme(linija);
            System.out.println("Unesite neradne dane u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
            linija = scanner.nextLine();
            rasporedPisiCitaj.ucitajNeradneDane(linija);
            rasporedPisiCitaj.ispisiNeradneDane();



            System.out.println("Unesite naziv izlaznog fajla:");
            linija = scanner.nextLine();
            try {
                rasporedPisiCitaj.exportujPodatke(linija);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try{
                rasporedPisiCitaj.ucitajPodatke(linija);
                System.out.println(rasporedPisiCitaj.raspored.getSviTermini());
                rasporedPisiCitaj.generisiSlobodneTermine("09:00", "21:00", "02/10/2023", "20/01/2024");
                rasporedPisiCitaj.interakcija();
                System.out.println("Unesite naziv izlaznog fajla:");
                linija = scanner.nextLine();
                rasporedPisiCitaj.exportujPodatke(linija);
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("Greska pri citanju fajlova.");
            }


        }

    }
}
