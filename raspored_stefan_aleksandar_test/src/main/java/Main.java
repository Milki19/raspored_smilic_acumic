import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite putanju do fajla: ");
        String linija = scanner.nextLine();
        RasporedAImpl rasporedAImpl = new RasporedAImpl();
        Utils utils = new Utils();


        if(linija.contains(".csv")){
            String prviDeo = linija;
            System.out.println("Unesite putanju do konfiguracionog fajla: ");
            linija = scanner.nextLine();
            String drugiDeo = linija;
            String path = prviDeo + "," + drugiDeo;

            try{
                rasporedAImpl.ucitajCSV(prviDeo, drugiDeo);
                System.out.println(rasporedAImpl.raspored.getSviTermini());
            }catch (IOException e){
                System.out.println("Greska pri citanju fajlova");
                return;
            }
            System.out.println("Unesite prvi i poslednji dan rasporeda u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
            linija = scanner.nextLine();
            rasporedAImpl.ucitajPocetakKraj(linija);
            System.out.println("Unesite pocetak i kraj radnog vremena u zadatom formatu, razdvajajuci ih razmakom: hh:mm");
            linija = scanner.nextLine();
            rasporedAImpl.ucitajRadnoVreme(linija);
            System.out.println("Unesite neradne dane u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
            linija = scanner.nextLine();
            rasporedAImpl.ucitajNeradneDane(linija);
            rasporedAImpl.ispisiNeradneDane();

            rasporedAImpl.raspored.getSviTermini().sort(Termin.getComparator()); //Sortiranje

            rasporedAImpl.generisiSlobodneTermine(rasporedAImpl.raspored.getPocetakRadnogVremena(),
                    rasporedAImpl.raspored.getKrajRadnogVremena(), rasporedAImpl.raspored.pocetakRasporeda,
                    rasporedAImpl.raspored.getKrajRasporeda()); // Generisanje slobodnih termina
            System.err.println(rasporedAImpl.getRaspored().getSlobodniTermini().size());

            for (Termin t : rasporedAImpl.getRaspored().getSlobodniTermini()) {
                System.out.println(t);
            }


            utils.interakcijaSaKorisnikom(rasporedAImpl); // Ulazimo u meni za interakciju

            System.out.println("Unesite naziv izlaznog fajla:");
            linija = scanner.nextLine();
            try {
                if(linija.contains(".csv")){
                    rasporedAImpl.exportCSV(linija);
                }else if(linija.contains(".json")){
                    rasporedAImpl.exportJSON(linija);
                }else if(linija.contains(".pdf")){
                    rasporedAImpl.exportPDF(linija);
                }else{
                    rasporedAImpl.exportPDF(linija);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try{
                rasporedAImpl.ucitajJSON(linija); // Ucitavamo podatke
                rasporedAImpl.raspored.getSviTermini().sort(Termin.getComparator()); //Sortiranje

                rasporedAImpl.generisiSlobodneTermine(rasporedAImpl.raspored.getPocetakRadnogVremena(),
                        rasporedAImpl.raspored.getKrajRadnogVremena(), rasporedAImpl.raspored.pocetakRasporeda,
                        rasporedAImpl.raspored.getKrajRasporeda()); // Generisanje slobodnih termina
                System.err.println(rasporedAImpl.getRaspored().getSlobodniTermini().size());

                for (Termin t : rasporedAImpl.getRaspored().getSlobodniTermini()) {
                    System.out.println(t);
                }


                utils.interakcijaSaKorisnikom(rasporedAImpl); // Ulazimo u meni za interakciju

                System.out.println("Unesite naziv izlaznog fajla:");
                linija = scanner.nextLine();
                if(linija.contains(".csv")){
                    rasporedAImpl.exportCSV(linija);
                }else if(linija.contains(".json")){
                    rasporedAImpl.exportJSON(linija);
                }else if(linija.contains(".pdf")){
                    rasporedAImpl.exportPDF(linija);
                }else{
                    rasporedAImpl.exportPDF(linija);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }

}
