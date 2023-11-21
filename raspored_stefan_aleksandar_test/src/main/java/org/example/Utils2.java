package org.example;

import org.example.RasporedA;
import org.example.Termin;

import java.io.IOException;
import java.util.*;

public class Utils2 {

    private List<Termin> pretrazeneStvari;

    public Utils2 () {
        pretrazeneStvari = new ArrayList<>();
    }

    public void interakcijaSaKorisnikom(RasporedA rasporedA) throws IOException {
        System.out.println("\nIzabite sta sledece zelite da uradite, tako sto napisete broj koji se nalazi ispred:");
        System.out.println("1. Dodati termin");
        System.out.println("2. Izbrisati termin");
        System.out.println("3. Izmeniti termin");
        System.out.println("4. Pretraziti termine");
        System.out.println("5. Pogledati slobodne termine");
        System.out.println("6. Izadji");
        Scanner sc = new Scanner(System.in);
        Map<String, String> dodaci = new HashMap<>();
        String linija = sc.nextLine();

        if(linija.equals("1")){
            System.out.println("Unesite obavezne podatke za (mesto, dan, pocetni datum, pocetno vreme, krajnje vreme) u sledeceom formatu: mesto DAN dd/mm/yyyy hh:mm hh:mm");
            //Raf04,PON,02/10/2022,09:15,11:00
            //Raf04 PON 22/10/2022 09:15 11:00 Poslovne aplikacije Igro Mijatovic DA
            //"Raf04","PON","02/10/2022","09:15","11:00","Poslovne aplikacije","Mijatovic Igor","DA"
            linija = sc.nextLine();
            String[] split = linija.split(" ", 7);
            for (String s : split) {
                System.out.println(s);
            }
            if (split.length == 5) {
                rasporedA.dodajTermin(rasporedA.getRaspored(), new Termin(split[0], split[1], split[2], split[3], split[4]));
            }else if (split.length == 6) {
                rasporedA.dodajTermin(rasporedA.getRaspored(), new Termin(split[0], split[1], split[2], split[3], split[4], split[5]));
            } else if (split.length > 6) {
                String[] dodatak = split[5].split(",");
                //Raf04 PON 22/10/2023 09:15 11:00 Poslovne aplikacije Igor Mijatovic DA
//              Raf04 PON 22/10/2022 09:15 11:00
                dodaci.put(split[0], "Predmet");
                dodaci.put(split[1], "Profesor");
                dodaci.put(split[2], "Racunar");

                rasporedA.dodajTermin(rasporedA.getRaspored(), new Termin(split[0], split[1], split[2], split[3], split[4], dodaci));
            }


        }else if(linija.equals("2")){
            System.out.println("Podatke za termin koji zelite da obrisete za (mesto, dan, pocetni datum, pocetno vreme, krajnje vreme) u sledeceom formatu: mesto DAN dd/mm/yyyy hh:mm hh:mm");
            //Raf04,PON,02/10/2022,09:15,11:00
            //Raf04 PON 22/10/2022 09:15 11:00 Poslovne aplikacije Igro Mijatovic DA
            //"Raf04","PON","02/10/2022","09:15","11:00","Poslovne aplikacije","Mijatovic Igor","DA"
            linija = sc.nextLine();
            String[] split = linija.split(" ", 7);
            for (String s : split) {
                System.out.println(s);
            }

            rasporedA.obrisiTermin(rasporedA.getRaspored(), new Termin (split[0], split[1], split[2], split[3], split[4], split[5]));
            System.err.println("Slobodni termini size: " + rasporedA.getRaspored().getSlobodniTermini().size());
            for (Termin t : rasporedA.getRaspored().getSlobodniTermini()) {
                System.out.println(t);
            }

            System.err.println("Termini size: " + rasporedA.getRaspored().getSviTermini().size());

        }else if(linija.equals("3")){

            linija = sc.nextLine();
            String[] split = linija.split(",");

            String stariTermin = split[0];
            String noviTermin = split[1];

            String[] strTer = stariTermin.split(" ");
            String[] novTer = noviTermin.split(" ");

            // RAF20 02/10/2023 13:15 16:00
            // RAF20 PON 02/10/2023 02/10/2023 14:15 16:00

            for(Termin t : rasporedA.getRaspored().getSviTermini()){
                if(t.getMesto().equals(strTer[0]) && t.getDatum().equals(strTer[2]) && t.getPocetakVreme().equals(strTer[4]) && t.getKrajVreme().equals(strTer[5]) && t.getDan().equals(strTer[1])){
                    rasporedA.izmeniTermin(rasporedA.getRaspored(), t, new Termin(novTer[0], novTer[1], novTer[2], novTer[3], novTer[4], novTer[5]));
                    return;
                }else{
                    System.out.println("Ne postoji trenutni termin.");
                }
            }


        }else if(linija.equals("4")){
            pretrazeneStvari = pretraga(rasporedA);
            System.out.println(pretrazeneStvari);

            System.out.println("Da li zelite da exportujete pretrazene odgovore? Da/Ne");
            linija = sc.nextLine();
            if(linija.equals("Da")){
                System.out.println("Unesite ime fajla i destinaciju razdvojene razmakom.");
                linija = sc.nextLine();
                String[] niz = linija.split(" ");
                if(niz[0].contains(".csv")){
                    rasporedA.exportCSV(niz[0], pretrazeneStvari, niz[1]);
                }else if(niz[0].contains(".json")){
                    rasporedA.exportJSON(niz[0], pretrazeneStvari, niz[1]);
                }else{
                    rasporedA.exportPDF(niz[0], pretrazeneStvari, niz[1]);
                    return;
                }
            }
            interakcijaSaKorisnikom(rasporedA);
        }else if(linija.equals("5")){
            pretrazeneStvari = slobodniSearch(rasporedA);

            System.out.println("Da li zelite da exportujete pretrazene odgovore? Da/Ne");
            linija = sc.nextLine();
            if(linija.equals("Da")){
                System.out.println("Unesite ime fajla i destinaciju razdvojene razmakom.");
                linija = sc.nextLine();
                String[] niz = linija.split(" ");
                if(niz[0].contains(".csv")){
                    rasporedA.exportCSV(niz[0], pretrazeneStvari, niz[1]);
                }else if(niz[0].contains(".json")){
                    rasporedA.exportJSON(niz[0], pretrazeneStvari, niz[1]);
                }else{
                    rasporedA.exportPDF(niz[0], pretrazeneStvari, niz[1]);
                }
                return;
            }

            interakcijaSaKorisnikom(rasporedA);
        }else if(linija.equals("6")){

        }else{
            System.out.println("Niste izabrali validnu opciju:\n");
            interakcijaSaKorisnikom(rasporedA);
        }


    }

    public List<Termin> pretraga (RasporedA rasporedA) {
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

    public List<Termin> slobodniSearch (RasporedA rasporedA) {
        System.out.println("\nIzaberite po cemu zelite da pretrazite slobodne termine razvodejene razmakom:");
        System.out.println("1. Mesto");
        System.out.println("2. Pocetni datum");
        System.out.println("3. Krajnji datum");
        System.out.println("4. Pocetno vreme");
        System.out.println("5. Krajnje vreme");
        System.out.println("6. Dan");
        System.out.println("7. Dodatno\n");

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
        int flag7 = 0;

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
            if (niz[i].equals("7")) {
                flag7 = 1;
            }
        }

        if ((flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 0) ||
                (flag1 == 0 && flag2 == 0 && flag3 == 1 && flag4 == 0 && flag5 == 0 && flag6 == 0)) {
            System.out.println("Unesite datum koji zelite u formatu: dd/mm/yyyy");
            linija = sc.nextLine();
            return rasporedA.slobodniDatum(linija);
        } else if ((flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1 && flag6 == 1 && flag7 == 0) || (flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 1 && flag5 == 1 && flag6 == 1 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite pocetni datum, krajnji datum, pocetno vreme, krajnje vreme i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy dd/mm/yyyy hh:mm hh:mm DAN");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniDatumiPocetakVremeKrajVremeDan(split[0], split[1], split[2], split[3], split[4]);
            }
            System.out.println("Unesite pocetni datum, pocetno vreme, krajnje vreme i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniDatumiPocetakVremeKrajVremeDan(split[0], "", split[1], split[2], split[3]);
        } else if (flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 0) {
            System.out.println("Unesite pocenti datum i krajnji datum koji zelite u formatu: dd/mm/yyyy dd/mm/yyyy");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniDatumi(split[0], split[1]);
        } else if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 1) {
            System.out.println("Unesite dodatak za koji zelite da proverite slobodne termine: ");
            linija = sc.nextLine();
            return rasporedA.slobodniDodatak(linija);
        } else if (flag1 == 1 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 0) {
            System.out.println("Unesite mesto za koji zelite da proverite slobodne termine: ");
            linija = sc.nextLine();
            return rasporedA.slobodniMesto(linija);
        } else if ((flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 0) || (flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 0 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite mesto, pocetni datum, krajnji datum za koji zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy dd/mm/yyy");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniMestoDatumi(split[0], split[1], split[2]);
            }
            System.out.println("Unesite mesto i datum za koji zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniMestoDatumi(split[0], split[1], "");
        } else if (flag1 == 1 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 1 && flag7 == 0) {
            System.out.println("Unesite mesto i dan za koji zelite da proverite slobodne termine u formatu: mesto DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniMestoDan(split[0], split[1]);
        } else if ((flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 0 && flag6 == 0 && flag7 == 0) || (flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 1 && flag5 == 0 && flag6 == 0 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite mesto, pocetni datum, krajnji datum i pocetno vreme za koje zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy dd/mm/yyy hh:mm");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniMestoDatumiPocetak(split[0], split[1], split[2], split[3]);
            }
            System.out.println("Unesite mesto, datum i pocetno vreme za koje zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniMestoDatumiPocetak(split[0], split[1], "", split[2]);
        } else if ((flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0 && flag6 == 1 && flag7 == 0) || (flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 1 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite mesto, pocetni datum, krajnji datum i dan za koji zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy dd/mm/yyyy DAN");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniMestoDatumiDan(split[0], split[1], split[2], split[3]);
            }
            System.out.println("Unesite mesto, datum i dan za koji zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniMestoDatumiDan(split[0], split[1], "", split[2]);
        } else if (flag1 == 1 && flag2 == 0 && flag3 == 0 && flag4 == 1 && flag5 == 0 && flag6 == 1 && flag7 == 0) {
            System.out.println("Unesite mesto, pocetno vreme i dan za koji zelite da proverite slobodne termine u formatu: mesto hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniMestoPocetakDan(split[0], split[1], split[2]);
        } else if ((flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1 && flag6 == 0 && flag7 == 0) || (flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 1 && flag5 == 1 && flag6 == 0 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite mesto, pocetni datum, krajnji datum, pocetno vreme i krajnje vreme za koje zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy dd/mm/yyyy hh:mm hh:mm");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniMestoDatumiPocetakKraj(split[0], split[1], split[2], split[3], split[4]);
            }
            System.out.println("Unesite mesto, datum, pocetno vreme i krajnje vreme za koje zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniMestoDatumiPocetakKraj(split[0], split[1], "", split[2], split[3]);
        } else if ((flag1 == 1 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1 && flag6 == 1 && flag7 == 0) || (flag1 == 1 && flag2 == 1 && flag3 == 0 && flag4 == 1 && flag5 == 1 && flag6 == 1 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite mesto, pocetni datum, krajnji datum, pocetno vreme, krajnje vreme i dan za koji zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy dd/mm/yyyy hh:mm hh:mm DAN");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniMestoDatumiPocetakKrajDan(split[0], split[1], split[2], split[3], split[4], split[5]);
            }
            System.out.println("Unesite mesto, datum, pocetno vreme, krajnje vreme i dan za koje zelite da proverite slobodne termine u formatu: mesto dd/mm/yyyy hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniMestoDatumiPocetakKrajDan(split[0], split[1], "", split[2], split[3], split[4]);
        } else if ((flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 0 && flag6 == 0 && flag7 == 0) || (flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 1 && flag5 == 0 && flag6 == 0 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite pocetni datum, krajnji datum i pocetno vreme za koje zelite da proverite slobodne termine u formatu: dd/mm/yyyy dd/mm/yyyy hh:mm");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniDatumiPocetak(split[0], split[1], split[2]);
            }
            System.out.println("Unesite datum i pocetno vreme za koje zelite da proverite slobodne termine u formatu: dd/mm/yyyy hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniDatumiPocetak(split[0], "", split[1]);
        } else if ((flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 0 && flag5 == 0 && flag6 == 1 && flag7 == 0) || (flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 1 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite pocetni datum, krajnji datum i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy dd/mm/yyyy DAN");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniDatumiDan(split[0], split[1], split[2]);
            }
            System.out.println("Unesite datum i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniDatumiDan(split[0], "", split[1]);
        } else if ((flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 1 && flag6 == 0 && flag7 == 0) || (flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 1 && flag5 == 1 && flag6 == 0 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite pocetni datum, krajnji datum, pocetno vreme i krajnje vreme za koje zelite da proverite slobodne termine u formatu: dd/mm/yyyy dd/mm/yyyy hh:mm hh:mm");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniDatumiPocetakKraj(split[0], split[1], split[2], split[3]);
            }
            System.out.println("Unesite datum, pocetno vreme i krajnje vreme za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniDatumiPocetakKraj(split[0], "", split[1], split[2]);
        } else if ((flag1 == 0 && flag2 == 1 && flag3 == 1 && flag4 == 1 && flag5 == 0 && flag6 == 1 && flag7 == 0) || (flag1 == 0 && flag2 == 1 && flag3 == 0 && flag4 == 1 && flag5 == 0 && flag6 == 1 && flag7 == 0)) {
            if (flag3 == 1) {
                System.out.println("Unesite pocetni datum, krajnji datum, pocetno vreme i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy dd/mm/yyyy hh:mm DAN");
                linija = sc.nextLine();
                String[] split = linija.split(" ");
                return rasporedA.slobodniDatumiPocetakDan(split[0], split[1], split[2], split[3]);
            }
            System.out.println("Unesite datum, pocetno vreme i dan za koji zelite da proverite slobodne termine u formatu: dd/mm/yyyy hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniDatumiPocetakDan(split[0], "", split[1], split[2]);
        } else if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 1 && flag5 == 1 && flag6 == 0 && flag7 == 0) {
            System.out.println("Unesite pocetno vreme i krajnje vreme za koje zelite da proverite slobodne termine u formatu: hh:mm hh:mm");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniPocetakKraj(split[0], split[1]);
        } else if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 1 && flag5 == 1 && flag6 == 1 && flag7 == 0) {
            System.out.println("Unesite pocetno vreme, krajnje vreme i dan za koji zelite da proverite slobodne termine u formatu: hh:mm hh:mm DAN");
            linija = sc.nextLine();
            String[] split = linija.split(" ");
            return rasporedA.slobodniPocetakKrajDan(split[0], split[1], split[2]);
        } else if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 1 && flag6 == 0 && flag7 == 0) {
            System.out.println("Unesite krajnje vreme za koje zelite da proverite slobodne termine u formatu: hh:mm");
            linija = sc.nextLine();
            return rasporedA.slobodniKraj(linija);
        } else if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0 && flag6 == 1 && flag7 == 0) {
            System.out.println("Unesite dan ili raspon dana za koji zelite da proverite slobodne termine u formatu: DAN ili DAN DAN");
            linija = sc.nextLine();
            if(linija.contains(" ")){
                String[] split = linija.split(" ");
                return rasporedA.slobodniDani(split[0], split[1]);
            }
            return rasporedA.slobodniDan(linija);

        } else if (flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 1 && flag5 == 0 && flag6 == 0 && flag7 == 0) {
            System.out.println("Unesite pocetno vreme za koje zelite da proverite slobodne termine u formatu: hh:mm");
            linija = sc.nextLine();
            return rasporedA.slobodniPocetak(linija);
        }

        return null;
    }

}
