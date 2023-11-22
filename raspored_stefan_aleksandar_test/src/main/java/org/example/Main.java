package org.example;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Class<?> impl = Class.forName("org.example.RasporedAImpl");
        RasporedA rasporedA = (RasporedA) impl.getDeclaredConstructor().newInstance();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Unesite putanju do fajla: ");
        String linija = scanner.nextLine();
        Utils utils = new Utils();


        if(linija.contains(".csv")){
            String prviDeo = linija;
            System.out.println("Unesite putanju do konfiguracionog fajla: ");
            linija = scanner.nextLine();
            String drugiDeo = linija;

            try{
                rasporedA.ucitajCSV(prviDeo, drugiDeo);
            }catch (IOException e){
                System.out.println("Greska pri citanju fajlova");
                return;
            }
            System.out.println("Unesite prvi i poslednji dan rasporeda u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
            linija = scanner.nextLine();
            rasporedA.ucitajPocetakKraj(linija);
            System.out.println("Unesite pocetak i kraj radnog vremena u zadatom formatu, razdvajajuci ih razmakom: hh:mm");
            linija = scanner.nextLine();
            rasporedA.ucitajRadnoVreme(linija);
            System.out.println("Unesite neradne dane u zadatom formatu, razdvajajuci ih razmakom: dd/mm/yyyy");
            linija = scanner.nextLine();
            rasporedA.ucitajNeradneDane(linija);

            rasporedA.getRaspored().getSviTermini().sort(Termin.getComparator());
            rasporedA.generisiSlobodneTermine(rasporedA.getRaspored().getPocetakRadnogVremena(),
                    rasporedA.getRaspored().getKrajRadnogVremena(), rasporedA.getRaspored().getPocetakRasporeda(),
                    rasporedA.getRaspored().getKrajRasporeda());

            utils.interakcijaSaKorisnikom(rasporedA);

            if(utils.getIspisano() == 0){
                System.out.println("Unesite naziv izlaznog fajla:");
                linija = scanner.nextLine();
                try {
                    if(linija.contains(".csv")){
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportCSV(linija, rasporedA.getRaspored().getSviTermini(), linija);
                    } else if(linija.contains(".json")){
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportJSON(linija, rasporedA.getRaspored().getSviTermini(), linija);
                    } else if(linija.contains(".pdf")){
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportPDF(linija, rasporedA.getRaspored().getSviTermini(), linija);
                    } else {
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportPDF(linija, rasporedA.getRaspored().getSviTermini(), linija);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            try{
                rasporedA.ucitajJSON(linija);
                rasporedA.getRaspored().getSviTermini().sort(Termin.getComparator());

                rasporedA.generisiSlobodneTermine(rasporedA.getRaspored().getPocetakRadnogVremena(),
                        rasporedA.getRaspored().getKrajRadnogVremena(), rasporedA.getRaspored().getPocetakRasporeda(),
                        rasporedA.getRaspored().getKrajRasporeda());

                utils.interakcijaSaKorisnikom(rasporedA);

                if(utils.getIspisano() == 0) {
                    System.out.println("Unesite naziv izlaznog fajla:");
                    linija = scanner.nextLine();
                    String fajl = linija;
                    if (linija.contains(".csv")) {
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportCSV(fajl, rasporedA.getRaspored().getSviTermini(), linija);
                    } else if (linija.contains(".json")) {
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportJSON(fajl, rasporedA.getRaspored().getSviTermini(), linija);
                    } else if (linija.contains(".pdf")) {
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportPDF(fajl, rasporedA.getRaspored().getSviTermini(), linija);
                    } else {
                        System.out.println("Unesite destinaciju fajla: ");
                        linija = scanner.nextLine();
                        rasporedA.exportPDF(fajl, rasporedA.getRaspored().getSviTermini(), linija);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
