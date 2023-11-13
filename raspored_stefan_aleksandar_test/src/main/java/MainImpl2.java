import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
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
//        System.out.println(rasporedAPisiCitaj.getRaspored().getSviTermini().get(1).getDatum());
        System.out.println("Unesite prvi i poslednji dan rasporeda u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
        linija = scanner.nextLine();
        String[] splitDatum = linija.split(" ");
        rasporedAPisiCitaj.setPocetakDatum(splitDatum[0]);
        rasporedAPisiCitaj.setKrajDatum(splitDatum[1]);
        rasporedAPisiCitaj.setPocetakDatum(linija);
        System.out.println("Unesite pocetak i kraj radnog vremena u zadatom formatu, razdvajajuci ih razmakom: hh:mm");
        linija = scanner.nextLine();
        rasporedAPisiCitaj.ucitajRadnoVreme(linija);
        System.out.println("Unesite neradne dane u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
        linija = scanner.nextLine();
        rasporedAPisiCitaj.ucitajNeradneDane(linija);
        rasporedAPisiCitaj.ispisiNeradneDane();
        System.out.println("Unesite naziv izlaznog fajla");
        linija = scanner.nextLine();
        try{
            rasporedAPisiCitaj.ucitajPodatke("raspored_stefan_aleksandar_test/resursi/sortiran.csv,raspored_stefan_aleksandar_test/resursi/konfig.txt");
            System.out.println(rasporedAPisiCitaj.raspored.getSviTermini());
        }catch (IOException e){
            System.out.println("Greska pri citanju fajlova");
        }

        try {
            rasporedAPisiCitaj.exportujPodatke(linija);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Da li zelite da unesete jos termina");
        linija = scanner.nextLine();
        String odg = "DA";
        if (linija.toUpperCase().contains(odg)) {
            DodatneFunkcionalnosti df = new DodatneFunkcionalnosti();

            System.out.println("Unesite podatke u sledeceom formatu:" +
                    "Mesto,Dan,Datum,PocetakVreme,KrajVreme");
            //RAF1,PON,30/10/2023,09:15,11:15
            //Raf04,PON,02/10/2022,09:15,11:00
            linija = scanner.nextLine();
            String[] split = linija.split(",");
            df.dodajNoviTermin(rasporedAPisiCitaj.getRaspored(), split[0], split[1], split[2], "", split[3], split[4]);

            System.out.println("Unesite naziv izlaznog fajla");
            linija = scanner.nextLine();
            try {
                rasporedAPisiCitaj.exportujPodatke(linija);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
//        String str = new String("09:00");
//        String str1 = new String("10:00");
//        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//        Date d = new Date();
//        Date d2 = new Date();
//        try {
//            d = dateFormat.parse(str);
//            d2 = dateFormat.parse(str1);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//            System.out.println(d.getTime() < d2.getTime());
//
    }
}
