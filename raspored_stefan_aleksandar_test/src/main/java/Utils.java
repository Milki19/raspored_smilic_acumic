import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private List<Termin> pretrazeneStvari;

    public Utils () {
        pretrazeneStvari = new ArrayList<>();
    }


    public void interakcijaSaKorisnikom(RasporedAImpl rasporedA) {
        System.out.println("\nIzabite sta sledece zelite da uradite, tako sto napisete broj koji se nalazi ispred:");
        System.out.println("1. Dodati termin");
        System.out.println("2. Izbrisati termin");
        System.out.println("3. Izmeniti termin");
        System.out.println("4. Pretraziti termine");
        System.out.println("5. Pogledati slobodne termine");
        System.out.println("6. Izadji");
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        if(linija.equals("1")){
            System.out.println("Unesite obavezne podatke za (mesto, dan, pocetni datum, pocetno vreme, krajnje vreme) u sledeceom formatu: mesto DAN dd/mm/yyyy hh:mm hh:mm");
            //Raf04,PON,02/10/2022,09:15,11:00
            //Raf04 PON 22/10/2022 09:15 11:00 Poslovne aplikacije Igro Mijatovic DA
            //"Raf04","PON","02/10/2022","09:15","11:00","Poslovne aplikacije","Mijatovic Igor","DA"
            linija = sc.nextLine();
            String[] split = linija.split(" ", 6);
            for (String s : split) {
                System.out.println(s);
            }
            if (split.length == 5)
                rasporedA.dodajTermin(rasporedA.getRaspored(), split[0], split[1], split[2], split[3], split[4], "");
            else if (split.length >= 5)
                rasporedA.dodajTermin(rasporedA.getRaspored(), split[0], split[1], split[2], split[3], split[4], split[5]);
        }else if(linija.equals("2")){
            System.out.println("Podatke za termin koji zelite da obrisete za (mesto, dan, pocetni datum, pocetno vreme, krajnje vreme) u sledeceom formatu: mesto DAN dd/mm/yyyy hh:mm hh:mm");
            //Raf04,PON,02/10/2022,09:15,11:00
            //Raf04 PON 22/10/2022 09:15 11:00 Poslovne aplikacije Igro Mijatovic DA
            //"Raf04","PON","02/10/2022","09:15","11:00","Poslovne aplikacije","Mijatovic Igor","DA"
            linija = sc.nextLine();
            String[] split = linija.split(" ", 6);
            for (String s : split) {
                System.out.println(s);
            }

            rasporedA.obrisiTermin(rasporedA.getRaspored(), split[0], split[1], split[2], split[3], split[4], "");
            System.err.println("Slobodni termini size: " + rasporedA.getRaspored().getSlobodniTermini().size());
            for (Termin t : rasporedA.getRaspored().getSlobodniTermini()) {
                System.out.println(t);
            }

            System.err.println("Termini size: " + rasporedA.getRaspored().getSviTermini().size());

        }else if(linija.equals("3")){

        }else if(linija.equals("4")){
            pretrazeneStvari = pretraga(rasporedA);
            System.out.println(pretrazeneStvari);
            interakcijaSaKorisnikom(rasporedA);
        }else if(linija.equals("5")){
            slobodniSearch(rasporedA);
        }else if(linija.equals("6")){

        }else{
            System.out.println("Niste izabrali validnu opciju:\n");
            interakcijaSaKorisnikom(rasporedA);
        }
        
        
    }
    
    public List<Termin> pretraga (RasporedAImpl rasporedA) {
        System.out.println("\nIzaberite opcije kako zelite da pretrazite razvodejene razmakom:");
        System.out.println("1. Mesto");
        System.out.println("2. Datum");
        System.out.println("3. Pocetno vreme");
        System.out.println("4. Krajnje vreme");
        System.out.println("5. Dan");
        System.out.println("6. Dodatno");
        
        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();
        String[] niz = linija.split(" ");


        int flag1 = 0;
        int flag2 = 0;
        int flag3 = 0;
        int flag4 = 0;
        int flag5 = 0;
        int flag6 = 0;
        
        
        for (int i = 0; i < niz.length; i++) {
            if (niz[i].equals("1")) {
                flag1 = 1;
            }
            if (niz[i].equals("2")) {
                flag2 = 1;
            }
            if (niz[i].equals("3")) {
                flag3 = 1;
            }
            if (niz[i].equals("4")) {
                flag4 = 1;
            }
            if (niz[i].equals("5")) {
                flag5 = 1;
            }
            if (niz[i].equals("6")) {
                flag6 = 1;
            }
        }

        if(flag1 == 1 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite mesto koje zelite da pretrazite: ");
            linija = sc.nextLine();
            return rasporedA.pretraziMesto(linija);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite mesto i datum koje zelite da pretrazite u formatu: mesto dd/mm/yyyy");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziMestoDatum(split[0], split[1]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite mesto, datum i pocetno vreme koje zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziMestoDatumPocetak(split[0], split[1], split[2]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite mesto, datum i dan koji zelite da pretrazite u formatu: mesto dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziMestoDatumDan(split[0], split[1], split[2]);
        } else if(flag1 == 1 && flag2 == 0 && flag3 == 1 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite mesto, pocetno vreme i dan koji zelite da pretrazite u formatu: mesto hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziMestoPocetakDan(split[0], split[1], split[2]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite mesto, datum, pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziMestoDatumPocetakKraj(split[0], split[1], split[2], split[3]);
        } else if(flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1){
            System.out.println("Unesite mesto, datum, pocetno vreme, krajnje vreme i dan koji zelite da pretrazite u formatu: mesto dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziMestoDatumPocetakKrajDan(split[0], split[1], split[2], split[3], split[4]);
        } else if(flag1 == 1 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite mesto i dan koji zelite da pretrazite u formatu: mesto DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziMestoDan(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite datum koji zelite da pretrazite u formatu: dd/mm/yyyy");
            linija = sc.nextLine();
            return rasporedA.pretraziDatum(linija);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite datum i pocetno vreme koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziDatumPocetak(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite datum i dan koji zelite da pretrazite u formatu: dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziDatumDan(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite datum, pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziDatumPocetakKraj(split[0], split[1], split[2]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite datum, pocetno vreme i dan koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziDatumPocetakDan(split[0], split[1], split[2]);
        } else if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1){
            System.out.println("Unesite datum, pocetno vreme, krajnje vreme i dan koji zelite da pretrazite u formatu: dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziDatumPocetakKrajDan(split[0], split[1], split[2], split[3]);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 0 && flag5 == 0){
            System.out.println("Unesite pocetno vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            return rasporedA.pretraziPocetak(linija);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite pocetno vreme i krajnje vreme koje zelite da pretrazite u formatu: hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziPocetakKraj(split[0], split[1]);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 1 && flag5 == 1){
            System.out.println("Unesite pocetno vreme, krajnje vreme i dan koje zelite da pretrazite u formatu: hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.pretraziPocetakKrajDan(split[0], split[1], split[2]);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 1 && flag5 == 0){
            System.out.println("Unesite krajnje vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            return rasporedA.pretraziKraj(linija);
        } else if(flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 1){
            System.out.println("Unesite krajnje vreme koje zelite da pretrazite u formatu: hh:mm");
            linija = sc.nextLine();
            return rasporedA.pretraziDan(linija);
        } else if(flag6 == 1){
            System.out.println("Unesite po cemu zelite da vrsite pretragu:");
            linija = sc.nextLine();
            return rasporedA.pretraziDodatak(linija);
        }

        return null;
    }

    public List<Termin> slobodniSearch (RasporedAImpl rasporedA) {
        System.out.println("\nIzaberite po cemu zelite da pretrazite slobodne termine razvodejene razmakom:");
        System.out.println("1. Mesto");
        System.out.println("2. Pocetni datum");
        System.out.println("3. Krajnji datum");
        System.out.println("4. Pocetno vreme");
        System.out.println("5. Krajnje vreme");
        System.out.println("6. Dan\n");

        Scanner sc = new Scanner(System.in);
        String linija = sc.nextLine();

        // 1 3 5

        String[] niz = linija.split(" ");

        int flag1 = 0;
        int flag2 = 0;
        int flag3 = 0;
        int flag4 = 0;
        int flag5 = 0;
        int flag6 = 0;

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
            if(niz[i].equals("6")){
                flag6 = 1;
            }
        }

        if(flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1 && flag6 == 1){
            System.out.println("Unesite pocetni datum, krajnji datum, pocetno vreme, krajnje vreme i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniPocetakDatumKrajDatumPocetakVremeKrajVremeDan(split[0], split[1], split[2], split[3], split[4]);
        }


        return null;
    }
    
}
