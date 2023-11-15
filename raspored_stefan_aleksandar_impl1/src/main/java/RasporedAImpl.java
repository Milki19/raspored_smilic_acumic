import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.util.*;

public class RasporedAImpl extends RasporedA{

    private String pocetakDatum;
    private String krajDatum;

    public RasporedAImpl(){
        this.raspored = new Raspored();
    }
    @Override
    public boolean ucitajPodatke(String path) throws IOException {

        if(path.contains(",")){
            String[] niz = path.split(",");
            ucitajCSV(niz[0], niz[1]);
        }else{
            ucitajJackson(path);
        }

        return true;
    }

    @Override
    public boolean exportujPodatke(String path) throws IOException {
        if(path.contains(".csv")){
            ispisiCSV(path);
        }else if(path.contains(".json")){
            ispisiJSON(path);
        }else if(path.contains(".pdf")){
            ispisiPDF(path);
        }else{
            System.out.println("Nije moguce exportovati u zadatom formatu.");
        }
        return false;
    }

    @Override
    public void dodajTermin(){
        DodatneFunkcionalnosti df = new DodatneFunkcionalnosti();
        Scanner sc = new Scanner(System.in);

        System.out.println("Unesite podatke u sledeceom formatu: Mesto,Dan,Datum,PocetakVreme,KrajVreme");
        //Raf04,PON,02/10/2022,09:15,11:00
        //Raf04,PON,22/10/2022,09:15,11:00
        //"Raf04","PON","02/10/2022","09:15","11:00","Poslovne aplikacije","Mijatovic Igor","DA"
        String linija = sc.nextLine();
        String[] split = linija.split(",", 6);
        for (String s : split) {
            System.out.println(s);
        }

        //df.napraviDodtne(split[5]);
        df.dodajNoviTermin(getRaspored(), split[0], split[1], split[2], "", split[3], split[4]);
    }



    @Override
    public void pretrazi(){
        System.out.println("\nIzaberite opcije kako zelite da pretrazite razvodejene razmakom:");
        System.out.println("1. Mesto");
        System.out.println("2. Datum");
        System.out.println("3. Pocetno vreme");
        System.out.println("4. Krajnje vreme");
        System.out.println("5. Dan\n");

        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        // 1 3 5

        String[] niz = linija.split(" ");

        int flag1 = 0;
        int flag2 = 0;
        int flag3 = 0;
        int flag4 = 0;
        int flag5 = 0;

        /*
        mesto;
        datum;
        pocetakVreme;
        krajVreme;
        dan;
         */

        for(int i = 0; i < niz.length; i++){
            if(niz[i].equals("1")){
                flag1 = 1;
            }
            if(niz[i].equals("2")){
                flag2 = 1;
            }
            if(niz[i].equals("3")){
                flag3 = 1;
            }
            if(niz[i].equals("4")){
                flag4 = 1;
            }
            if(niz[i].equals("5")){
                flag5 = 1;
            }
        }

        if(flag1 == 1 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite mesto koje zelite da pretrazite: ");
            linija = sc.nextLine();
            pretrazi1(linija);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite mesto i datum koje zelite da pretrazite u formatu: mesto dd/mm/yyyy");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi12(split[0], split[1]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite mesto, datum i pocetno vreme koje zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi123(split[0], split[1], split[2]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite mesto, datum i dan koji zelite da pretrazite u formatu: mesto dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi125(split[0], split[1], split[2]);
        } else if(flag1 == 1 && flag2 == 0 && flag3 == 1 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite mesto, pocetno vreme i dan koji zelite da pretrazite u formatu: mesto hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi135(split[0], split[1], split[2]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite mesto, datum, pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi1234(split[0], split[1], split[2], split[3]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1){
            System.out.println("Unesite mesto, datum, pocetno vreme, krajnje vreme i dan koji zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi12345(split[0], split[1], split[2], split[3], split[4]);
        } else if(flag1 == 1 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite mesto i dan koji zelite da pretrazite u formatu: mesto DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi15(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite datum koji zelite da pretrazite u formatu: dd/mm/yyyy");
            linija = sc.nextLine();
            pretrazi2(linija);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite datum i pocetno vreme koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi23(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite datum i dan koji zelite da pretrazite u formatu: dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi25(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite datum, pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi234(split[0], split[1], split[2]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite datum, pocetno vreme i dan koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi235(split[0], split[1], split[2]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1){
            System.out.println("Unesite datum, pocetno vreme, krajnje vreme i dan koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi2345(split[0], split[1], split[2], split[3]);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite pocetno vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            pretrazi3(linija);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi34(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 1 && flag5 == 1){
            System.out.println("Unesite pocetno vreme, krajnje vreme i dan koje zelite da pretrazite u formatu: hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi345(split[0], split[1], split[2]);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite krajnje vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            pretrazi4(linija);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite krajnje vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            pretrazi5(linija);
        } else{
            System.out.println("Izabrana kombinacija za pretrazivanje nije trenutno dostupna");
            pretrazi();
        }


    }

    @Override
    public void pretrazi1(String mesto) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto koje zelite da pretrazite: ");
            linija = sc.nextLine();
            pretrazi1(linija);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi12(String mesto, String datum) {
        boolean ima = false;
        for (Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum)){
                System.out.println(t);
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");

        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto i datum koje zelite da pretrazite u formatu: mesto dd/mm/yyyy");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi12(split[0], split[1]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi15(String mesto, String dan) {
        boolean ima = false;
        for (Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDan().equals(dan)){
                System.out.println(t);
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");

        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto i datum koje zelite da pretrazite u formatu: mesto DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi15(split[0], split[1]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi123(String mesto, String datum, String pocetakVreme) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                System.out.println(t);
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");

        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto, datum i pocetno vreme koje zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi123(split[0], split[1], split[2]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi125(String mesto, String datum, String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getDan().equals(dan)){
                System.out.println(t);
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");

        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto, datum i dan koje zelite da pretrazite u formatu: mesto dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi125(split[0], split[1], split[2]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi135(String mesto, String pocetakVreme, String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getPocetakVreme().equals(pocetakVreme) && t.getDan().equals(dan)){
                System.out.println(t);
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");

        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto, pocetno vreme i dan koji zelite da pretrazite u formatu: mesto hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi135(split[0], split[1], split[2]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi1234(String mesto, String datum, String pocetakVreme, String krajVreme) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                System.out.println(t);
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");

        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto, datum, pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi1234(split[0], split[1], split[2], split[3]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi12345(String mesto, String datum, String pocetakVreme, String krajVreme, String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getMesto().equals(mesto) && t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                System.out.println(t);
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");

        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite mesto, datum, pocetno vreme, krajnje vreme i dan koji zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi12345(split[0], split[1], split[2], split[3], split[4]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi2(String datum) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite datum koji zelite da pretrazite u formatu: dd/mm/yyyy");
            linija = sc.nextLine();
            pretrazi2(linija);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi23(String datum, String pocetakVreme) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite datum i pocetno vreme koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi23(split[0], split[1]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi25(String datum, String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getDan().equals(dan)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite datum i dan koji zelite da pretrazite u formatu: dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi25(split[0], split[1]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi234(String datum, String pocetakVreme, String krajVreme) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite datum, pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi234(split[0], split[1], split[2]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi235(String datum, String pocetakVreme, String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getDan().equals(dan)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite datum, pocetno vreme i dan koje zelite da pretrazite u formatu: dd/mm/yyyy hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi235(split[0], split[1], split[2]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi2345(String datum, String pocetakVreme, String krajVreme, String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getDatum().equals(datum) && t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite datum, pocetno vreme, krajnje vreme i dan koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi2345(split[0], split[1], split[2], split[3]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi3(String pocetakVreme) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite pocetno vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            pretrazi3(linija);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi34(String pocetakVreme, String krajVreme) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi34(split[0], split[1]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi345(String pocetakVreme, String krajVreme, String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getPocetakVreme().equals(pocetakVreme) && t.getKrajVreme().equals(krajVreme) && t.getDan().equals(dan)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite pocetno vreme, krajnje vreme i dan koji zelite da pretrazite u formatu: hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            pretrazi345(split[0], split[1], split[2]);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi4(String krajVreme) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getKrajVreme().equals(krajVreme)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite krajnje vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            pretrazi4(linija);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    @Override
    public void pretrazi5(String dan) {
        boolean ima = false;
        for(Termin t : getRaspored().sviTermini){
            if(t.getDan().equals(dan)){
                System.out.println(t);
                ima = true;
            }
        }
        if(!ima) System.out.println("Trazeni termin ne postoji u zadatom rasporedu.");


        System.out.println("\nDa li zelite da: ");
        System.out.println("1. Nastavite ovu pretragu");
        System.out.println("2. Pokrenete drugu pretragu");
        System.out.println("3. Vratite se na pocetak");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        if(linija.equals("1")){
            System.out.println("Unesite dan koje zelite da pretrazite u formatu: DAN");
            linija = sc.nextLine();
            pretrazi5(linija);
        }else if(linija.equals("2")){
            pretrazi();
        }else interakcija();
    }

    private void ispisiPDF(String path) throws IOException{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.HELVETICA, 12);

        float y = 750;
        float yGranica = 100;

        contentStream.beginText();
        contentStream.newLineAtOffset(100, y);

        for (Termin t : this.raspored.getSviTermini()) {
            if (y < yGranica) {
                contentStream.endText();
                contentStream.close();

                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                y = 750;
                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
            }

            contentStream.moveTextPositionByAmount(0, -20);
            contentStream.showText("Dan: " + t.getDan());
            contentStream.showText(", Datum: " + t.getDatum());
            contentStream.showText(", Pocetak: " + t.getPocetakVreme());
            contentStream.showText(", Kraj: " + t.getKrajVreme());
            contentStream.showText(", Mesto: " + t.getMesto());

            y -= 20;
        }

        contentStream.endText();
        contentStream.close();

        System.out.println("Unesite destinaciju na kojoj zelite da bude fajl: ");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        document.save(linija + path);
        document.close();
    }



    private void ispisiJSON(String path) throws IOException{
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(raspored);
        System.out.println("Unesite destinaciju na kojoj zelite da bude fajl: ");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        FileWriter fw = new FileWriter(linija + path);
        fw.write(json);

        fw.close();
    }

    private void ispisiCSV(String path) throws IOException {
        System.out.println("Unesite destinaciju na kojoj zelite da bude fajl: ");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        FileWriter fileWriter = new FileWriter(linija + path);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);

        for (Termin termin : super.raspored.getSviTermini()) {
            csvPrinter.printRecord(
                    termin.getDan() + ", "
                            + termin.getDatum() + " "
                            + termin.getPocetakVreme() + "-"
                            + termin.getKrajVreme() + "h, "
                            + termin.getMesto()
            );
        }

        csvPrinter.close();
        fileWriter.close();
    }

    public void ucitajJackson(String path) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        raspored = mapper.readValue(new File(path), Raspored.class);
        dodajKrajDatum();
    }

    public void dodajKrajDatum(){
        for(Termin t : getRaspored().getSviTermini()){
            t.setKrajDatum(t.getDatum());
        }
    }

    private static List<Konfiguracija> citajKonfiguraciju(String configPath) throws FileNotFoundException {

        List<Konfiguracija> mapiranje = new ArrayList<>();

        File file = new File(configPath);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] split = line.split(" ", 3);

            mapiranje.add(new Konfiguracija(Integer.valueOf(split[0]), split[1], split[2]));
        }

        sc.close();


        return mapiranje;
    }

    private void ucitajCSV(String path, String configPath) throws IOException {
        List<Konfiguracija> mapiranje = citajKonfiguraciju(configPath);
        Map<Integer, String> mapa = new HashMap<>();

        for (Konfiguracija konfiguracija : mapiranje) {
            mapa.put(konfiguracija.getIndex(), konfiguracija.getOriginal());
        }

        FileReader fileReader = new FileReader(path);
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

        for (CSVRecord record : csvParser) {
            Termin termin = new Termin();

            for (Konfiguracija konfiguracija : mapiranje) {
                int columnIndex = konfiguracija.getIndex();

                String columnName = konfiguracija.getCustom();

                switch (mapa.get(columnIndex)) {
                    case "mesto":
                        termin.setMesto(record.get(columnIndex));
                        break;
                    case "datum":
                        termin.setDatum(record.get(columnIndex));
                        termin.setKrajDatum(record.get(columnIndex));
                        break;
                    case "dan":
                        termin.setDan(record.get(columnIndex));
                        break;
                    case "pocetakVreme":
                        termin.setPocetakVreme(record.get(columnIndex));
                        break;
                    case "krajVreme":
                        termin.setKrajVreme(record.get(columnIndex));
                        break;
                    case "dodaci":
                        termin.getDodaci().put(columnName, record.get(columnIndex));
                        break;
                }

            }

            raspored.getSviTermini().add(termin);

        }

    }

    public void ucitajNeradneDane(String s){
        int n = s.split(" ").length;
        String[] niz = s.split(" ");
        for(int i = 0; i < n; i++){
            raspored.getNeradniDani().add(niz[i]);
        }
    }

    public void ispisiNeradneDane() {
        for(String s : raspored.neradniDani){
            System.out.println(s);
        }
    }

    public void ucitajPocetakKraj(String s){
        String[] niz = s.split(" ");
        raspored.setPocetakRasporeda(niz[0]);
        raspored.setKrajRasporeda(niz[1]);
    }

    public void ucitajRadnoVreme(String s){
        String[] niz = s.split(" ");
        raspored.setPocetakRadnogVremena(niz[0]);
        raspored.setKrajRadnogVremena(niz[1]);
    }

    public void interakcija(){
        System.out.println("\nIzabite sta sledece zelite da uradite, tako sto napisete broj koji se nalazi ispred:");
        System.out.println("1. Dodati termin");
        System.out.println("2. Izbrisati termin");
        System.out.println("3. Izmeniti termin");
        System.out.println("4. Pretraziti termine");
        System.out.println("5. Izadji");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        if(linija.equals("1")){
            dodajTermin();
        }else if(linija.equals("2")){

        }else if(linija.equals("3")){

        }else if(linija.equals("4")){
            pretrazi();
        }else if(linija.equals("5")){

        }else{
            System.out.println("Niste izabrali validnu opciju:\n");
            interakcija();
        }
    }

    public String getPocetakDatum() {
        return pocetakDatum;
    }

    public void setPocetakDatum(String pocetakDatum) {
        this.pocetakDatum = pocetakDatum;
    }

    public String getKrajDatum() {
        return krajDatum;
    }

    public void setKrajDatum(String krajDatum) {
        this.krajDatum = krajDatum;
    }

    public Raspored getRaspored(){
        return this.raspored;
    }
}
