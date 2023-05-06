package com.example.spordiklubi2_0;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Peaklass {

    //kõikide failide puhul on oluline, et info oleks nendes täpselt õigel kujul kirjutatud, muidu asi läheb katki
    public static List<Liige> loeSisseIsikud(String failinimi) {
        List<Liige> isikud = new ArrayList<>();
        File fail = new File(failinimi);
        try (Scanner scanner = new Scanner(fail, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String rida = scanner.nextLine();
                String[] tükid = rida.split("; ");
                DateTimeFormatter kuupäevaFormaat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");
                if (tükid.length == 8) {//8 välja korral failis on tegu lihtliikmega
                    boolean onLiige = false;
                    if (tükid[5].equals("on liige")) {
                        onLiige = true;
                    } else {//kui pole liige, siis väärtustab liikmesuse algus ja lõppaja ilmselgelt vale ajaga
                        tükid[6] = "01.01.1900 00.00.00";
                        tükid[7] = "01.01.1900 00.00.00";
                    }
                    isikud.add(new Liige(tükid[0], tükid[1], tükid[2], tükid[3], parseInt(tükid[4]), onLiige, LocalDateTime.parse(tükid[6], kuupäevaFormaat), LocalDateTime.parse(tükid[7], kuupäevaFormaat)));
                }
                if (tükid.length == 10) {//10 välja korral failis on tegu töötajaga
                    boolean onLiige = false;
                    if (tükid[5].equals("on liige")) {
                        onLiige = true;
                    } else {//kui pole liige, siis väärtustab liikmesuse algus ja lõppaja ilmselgelt vale ajaga
                        tükid[6] = "01.01.1900 00.00.00";
                        tükid[7] = "01.01.1900 00.00.00";
                    }
                    isikud.add(new Töötaja(tükid[0], tükid[1], tükid[2], tükid[3], parseInt(tükid[4]), onLiige, LocalDateTime.parse(tükid[6], kuupäevaFormaat), LocalDateTime.parse(tükid[7], kuupäevaFormaat), LocalDateTime.parse(tükid[8], kuupäevaFormaat), LocalDateTime.parse(tükid[9], kuupäevaFormaat)));
                }
                if (tükid.length == 12) {//12 välja korral failis on tegu treeneriga
                    boolean onLiige = false;
                    if (tükid[5].equals("on liige")) {
                        onLiige = true;
                    } else {//kui pole liige, siis väärtustab liikmesuse algus ja lõppaja ilmselgelt vale ajaga
                        tükid[6] = "01.01.1900 00.00.00";
                        tükid[7] = "01.01.1900 00.00.00";
                    }
                    boolean onRühmaTreener = false;
                    boolean onPersonaalTreener = false;
                    if (tükid[10] == "on rühmatreener") {
                        onRühmaTreener = true;
                    }
                    if (tükid[10] == "on personaaltreener") {
                        onPersonaalTreener = true;
                    }
                    isikud.add(new Treener(tükid[0], tükid[1], tükid[2], tükid[3], parseInt(tükid[4]), onLiige, LocalDateTime.parse(tükid[6], kuupäevaFormaat), LocalDateTime.parse(tükid[7], kuupäevaFormaat), LocalDateTime.parse(tükid[8], kuupäevaFormaat), LocalDateTime.parse(tükid[9], kuupäevaFormaat), onRühmaTreener, onPersonaalTreener));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return isikud;
    }


    public static List<Treening> loeSissetreeningud(String failinimi) {
        List<Treening> treeningud = new ArrayList<>();
        File fail = new File(failinimi);
        try (Scanner scanner = new Scanner(fail, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String rida = scanner.nextLine();
                String[] tükid = rida.split("; ");
                DateTimeFormatter kuupäevaFormaat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");

                if (tükid.length == 10) {//10 välja korral failis on tegu personaaltreeninguga
                    boolean toitumisNõustamineonVõimalik = false;
                    if (tükid[9].equals("toitumisnõustamine võimalik")) {
                        toitumisNõustamineonVõimalik = true;
                    }
                    treeningud.add(new Personaaltreening(tükid[0], LocalDateTime.parse(tükid[1], kuupäevaFormaat), LocalDateTime.parse(tükid[2], kuupäevaFormaat), parseInt(tükid[3]), parseInt(tükid[4]), tükid[5], tükid[6], tükid[7], parseDouble(tükid[8]), toitumisNõustamineonVõimalik));
                }
                if (tükid.length == 11) {//11 välja korral failis on tegu rühmatreeninguga
                    treeningud.add(new Rühmatreening(tükid[0], LocalDateTime.parse(tükid[1], kuupäevaFormaat), LocalDateTime.parse(tükid[2], kuupäevaFormaat), parseInt(tükid[3]), parseInt(tükid[4]), tükid[5], tükid[6], tükid[7], tükid[8], tükid[9], parseInt(tükid[10])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return treeningud;
    }

    public static void kirjutaTreeningudFaili(String failinimi, List<Treening> trennid) {

        try {
            FileWriter kirjutaja = new FileWriter(failinimi);
            for (Treening trenn : trennid
            ) {
                kirjutaja.write(trenn.failiRida() + "\n");
            }
            kirjutaja.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Broneering> loeSisseBroneeringud(String failinimi, List<Treening> trennid, List<Liige> isikud) {
        List<Broneering> broneeringud = new ArrayList<>();
        File fail = new File(failinimi);
        try (Scanner scanner = new Scanner(fail, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String rida = scanner.nextLine();
                String[] tükid = rida.split("; ");
                int id = parseInt(tükid[0]);//id pannakse uus iga uue broneenringu isendi loomisel, vaja teha nii, et saaks ka juba failis olemasoleva id-ga uut isendit luua
                String trenniNimi = tükid[1];
                String isikuKood = tükid[2];
                Treening broneeritudTreening = null;//muidu ei kompileeru, sest muutujal ei pruugi väärtustust tekkida
                Liige liige = null;

                for (Treening trenn : trennid
                ) {
                    if (trenn.getNimi().equals(trenniNimi)) {
                        broneeritudTreening = trenn;
                    }
                }
                for (Liige isik : isikud
                ) {
                    if (isik.getIsikukood().equals(isikuKood)) {
                        liige = isik;
                    }
                }
                broneeringud.add(new Broneering(broneeritudTreening, liige, id));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return broneeringud;

    }

    public static void kirjutaBroneeringudFaili(String failinimi, List<Broneering> broneeringud) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(failinimi), "UTF-8"))) {

            for (Broneering bronn : broneeringud
            ) {
                bw.write(bronn.failiRida());
                bw.newLine();
            }
        }



    }

    /*public static void main(String[] args) throws IOException {
        List<Liige> isikud = loeSisseIsikud("isikud.txt");
        List<Treening> trennid = loeSissetreeningud("treeningud.txt");
        List<Broneering> broneeringud = loeSisseBroneeringud("broneeringud.txt", trennid, isikud);


        //Siit hakkab kasutajaliides
        Scanner skänner = new Scanner(System.in);
        while (true) {

            //avaleht
            System.out.println("\nMida soovid teha? \n\nKuva broneeringud - b\nKuva trennid - t\nSoovita treeningut - s\nKuva enda tulevased treeningud - k\nVälju - v\n");
            String sisestus = skänner.nextLine();


            //väljumine
            if (sisestus.equals("v")) {
                break;
            }


            //trenni soovitamine
            if (sisestus.equals("s")) {
                System.out.println("Sisesta oma isikukood:");
                String ikood = skänner.nextLine();
                for (Liige inimene : isikud
                ) {
                    if (inimene.getIsikukood().equals(ikood)) {
                        List<Broneering> bronnid = new ArrayList<>();
                        for (Broneering bronn : broneeringud
                        ) {
                            if (bronn.getLiige() == inimene) {
                                bronnid.add(bronn);
                            }
                        }
                        System.out.println("\n");
                        Treening.soovitaTreeningut(inimene, bronnid, trennid);
                    }
                }
                System.out.println("\nTagasi - t");
                String sisend = skänner.nextLine();
            }


            //enda trennide kuvamine
            if (sisestus.equals("k")) {
                System.out.println("Sisesta oma isikukood:");
                String isikukood = skänner.nextLine();
                for (Liige inimene : isikud) {
                    if (inimene.getIsikukood().equals(isikukood)) {//otsib isikukoodi põhjal selle isiku broneeringud välja
                        List<Broneering> bronnid = new ArrayList<>();
                        for (Broneering bronn : broneeringud
                        ) {
                            if (bronn.getLiige() == inimene) {
                                bronnid.add(bronn);
                            }
                        }
                        System.out.println("\n");
                        Broneering.kuvaMöödunudBroneeringud(inimene, bronnid);
                        Broneering.kuvaTulevasedBroneeringud(inimene, bronnid);
                    }
                }
                System.out.println("\nTagasi - t");
                String sisend = skänner.nextLine();
            }


            // kuvab kõikide isikute kõik broneeringud
            if (sisestus.equals("b")) {//kui kasutaja sisestab "b", siis väljastatakse kõik järjendi broneeringud elemendid
                for (Broneering broneering : broneeringud
                ) {
                    System.out.println(broneering);
                }
                System.out.println("\n\nTühista broneering - c\nTagasi - t\n");
                sisestus = skänner.nextLine();
                if (sisestus.equals("c")) {
                    System.out.println("Sisesta broneeringu id: ");
                    String id = skänner.nextLine();
                    Broneering.tühistaBroneering(parseInt(id), broneeringud);
                } else {
                    continue;
                }
            }


            //kuvab kõik trennid
            if (sisestus.equals("t")) {//kui kasutaja sisestab "t", siis kuvatakse kõik trennid ja pakutakse kasutajale võimalust minna tagasi või lisada broneering
                int trenniNumber = 0;
                for (Treening trenn : trennid
                ) {
                    System.out.println((trenniNumber++) + " " + trenn);
                    //trennid kuvatakse koos järjekorranumbriga, et kasutaja saaks valida nende vahel -
                    // kui kasutaja valiks nime või mõne muu tunnuse abil, oleks sisestusvea oht suurem
                }
                System.out.println("\nLisa broneering - lb\nTagasi - t\nLisa trenn - lt\nEemalda trenn - et");
                sisestus = skänner.nextLine();
                //broneeringu lisamine
                if (sisestus.equals("lb")) {//broneerimiseks peab sisestama täpselt sama isikukoodi nagu on liikmete järjendis kellelgi
                    System.out.println("Mis numbriga trenni soovid broneerida?");
                    int trenniNr = parseInt(skänner.nextLine());
                    System.out.println("Sisesta oma isikukood:");
                    String ik = skänner.nextLine();
                    for (Liige liige : isikud
                    ) {
                        if (liige.getIsikukood().equals(ik)) {
                            Broneering.teeBroneering(trennid.get(trenniNr).getNimi(), liige, trennid, broneeringud);
                        }
                    }
                }
                //trenni lisamine
                if (sisestus.equals("lt")) {//trenni lisamiseks küsitakse trenni üksikasju
                    System.out.println("Personaaltrenni lisamiseks sisesta - p , Rühmatreeningu lisamiseks - r");
                    String trennitüüp = skänner.nextLine();

                    if (trennitüüp.equals("p")) {
                        System.out.println("Sisesta trenni nimi");
                        String trenninimi = skänner.nextLine();
                        DateTimeFormatter kuupäevaFormaat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");
                        System.out.println("Sisesta algusaeg kujul dd.mm.yyyy HH.mm.ss");
                        LocalDateTime alguaeg = LocalDateTime.parse(skänner.nextLine(), kuupäevaFormaat);
                        System.out.println("Sisesta lõppaeg kujul dd.mm.yyyy HH.mm.ss");
                        LocalDateTime lõppaeg = LocalDateTime.parse(skänner.nextLine(), kuupäevaFormaat);
                        System.out.println("Sisesta vabade kohtade arv");
                        int vabadeKohtadeArv = parseInt(skänner.nextLine());
                        System.out.println("Sisesta kõikide kohtade arv");
                        int kõikideKohtadeArv = parseInt(skänner.nextLine());
                        System.out.println("Sisesta treeneri nimi");
                        String treeneriNimi = skänner.nextLine();
                        System.out.println("Sisesta klubi nimi");
                        String klubi = skänner.nextLine();
                        System.out.println("Sisesta asukoht");
                        String asukoht = skänner.nextLine();
                        System.out.println("Sisesta tunnihind");
                        double tunnihind = parseDouble(skänner.nextLine());
                        System.out.println("Kas toitumisnõustamine võimalik? Sisesta   jah    ei  ");
                        String nõustamine = skänner.nextLine();
                        boolean toitumisnõustamine = false;
                        if (nõustamine.equals("jah")) {
                            toitumisnõustamine = true;
                        }
                        Treening.lisaTreening(trennid, new Personaaltreening(trenninimi, alguaeg, lõppaeg, vabadeKohtadeArv, kõikideKohtadeArv, treeneriNimi, klubi, asukoht, tunnihind, toitumisnõustamine));
                    }
                    if (trennitüüp.equals("r")) {
                        System.out.println("Sisesta trenni nimi");
                        String trenninimi = skänner.nextLine();
                        DateTimeFormatter kuupäevaFormaat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");
                        System.out.println("Sisesta algusaeg kujul dd.mm.yyyy HH.mm.ss");
                        LocalDateTime alguaeg = LocalDateTime.parse(skänner.nextLine(), kuupäevaFormaat);
                        System.out.println("Sisesta lõppaeg kujul dd.mm.yyyy HH.mm.ss");
                        LocalDateTime lõppaeg = LocalDateTime.parse(skänner.nextLine(), kuupäevaFormaat);
                        System.out.println("Sisesta vabade kohtade arv");
                        int vabadeKohtadeArv = parseInt(skänner.nextLine());
                        System.out.println("Sisesta kõikide kohtade arv");
                        int kõikideKohtadeArv = parseInt(skänner.nextLine());
                        System.out.println("Sisesta treeneri nimi");
                        String treeneriNimi = skänner.nextLine();
                        System.out.println("Sisesta klubi nimi");
                        String klubi = skänner.nextLine();
                        System.out.println("Sisesta asukoht");
                        String asukoht = skänner.nextLine();
                        System.out.println("Sisesta treeningu stiil");
                        String stiil = skänner.nextLine();
                        System.out.println("Sisesta raskusaste");
                        String raskusaste = skänner.nextLine();
                        System.out.println("Sisesta keskmine kulutatud kalorite arv täisarvuna");
                        int kalorid = parseInt(skänner.nextLine());

                        Treening.lisaTreening(trennid, new Rühmatreening(trenninimi, alguaeg, lõppaeg, vabadeKohtadeArv, kõikideKohtadeArv, treeneriNimi, klubi, asukoht, stiil, raskusaste, kalorid));
                    }
                }
                //trenni eemaldamine
                if (sisestus.equals("et")) {
                    System.out.println("Mis numbriga trenni soovid eemaldada?");
                    int trenniNr = parseInt(skänner.nextLine());
                    Treening.eemaldaTreening(trennid.get(trenniNr).getNimi(), trennid);
                }
            }
        }

        //lõpus kirjutab trennide ja broneeringute järjendite lõppseisu peale programmi tööd failidesse
        kirjutaTreeningudFaili("treeningud.txt", trennid);
        kirjutaBroneeringudFaili("broneeringud.txt", broneeringud);
    }*/
}


