package com.example.spordiklubi2_0;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Treening {

    private String nimi;
    private LocalDateTime algusaeg;
    private LocalDateTime lõpuaeg;
    private int vabuKohti;
    private int kohtiKokku;
    private String treeneriNimi;
    private String klubi;
    private String asukoht;

    public Treening(String nimi, LocalDateTime algusaeg, LocalDateTime lõpuaeg, int vabuKohti, int kohtiKokku, String treeneriNimi, String klubi, String asukoht) {
        this.nimi = nimi;
        this.algusaeg = algusaeg;
        this.lõpuaeg = lõpuaeg;
        this.vabuKohti = vabuKohti;
        this.kohtiKokku = kohtiKokku;
        this.treeneriNimi = treeneriNimi;
        this.klubi = klubi;
        this.asukoht = asukoht;
    }

    public String getNimi() {
        return nimi;
    }

    public LocalDateTime getLõpuaeg() {
        return lõpuaeg;
    }

    public int getVabuKohti() {
        return vabuKohti;
    }

    public void setVabuKohti(int vabuKohti) {
        this.vabuKohti = vabuKohti;
    }

    public int getKohtiKokku() {
        return kohtiKokku;
    }

    public String getTreeneriNimi() {
        return treeneriNimi;
    }

    public String getKlubi() {
        return klubi;
    }

    public String getAsukoht() {
        return asukoht;
    }

    public LocalDateTime getAlgusaeg() {return algusaeg;}

    @Override
    public String toString() {
        return "kell: " + algusaeg.getHour() + "-" + lõpuaeg.getHour() +
                ", kuupäev: " + algusaeg.getDayOfMonth() + "." + algusaeg.getMonthValue() +
                ", vabu kohti: " + vabuKohti +
                ", kohti kokku: " + kohtiKokku +
                ", treener: " + treeneriNimi +
                ", klubi: " + klubi +
                ", asukoht: " + asukoht;
    }

    public static Treening leiaTreening(String treeninguNimi, List<Treening> trennid) {
        for (Treening trenn : trennid) {
            if (Objects.equals(treeninguNimi, trenn.getNimi()))
                return trenn;
        }
        return null;
    }

    public static void lisaTreening(List<Treening> trennid, Treening uusTreening) {
        trennid.add(uusTreening);

    }

    public static void eemaldaTreening(String treeninguNimi, List<Treening> trennid) {
        Treening treening = leiaTreening(treeninguNimi, trennid);
        trennid.remove(treening);
        System.out.println("Treening " + treening.getNimi() + " eemaldatud.");

    }

    public static String soovitaTreeningut(Isik isik, List<Broneering> bronnid, List<Treening> kõikTrennid) { // soovitab isikule personaalselt mingit trenni, kus ta pole käinud või ei oma broneeringut
        // Leiame kõik juba proovitud trennid
        List<Treening> proovitudTreeningud = new ArrayList<>();
        if (bronnid.size() > 0) {
            for (Broneering broneering : bronnid) {
                if (broneering.getLiige().getIsikukood().equals(isik.getIsikukood()) || broneering.getTreening().getTreeneriNimi().equals(isik.getEesnimi() + isik.getPerenimi()))
                    proovitudTreeningud.add(broneering.getTreening());
            }
        }

        List<Treening> proovimataTreeningud = new ArrayList<>();
        if (proovitudTreeningud.size() != 0)
            for (Treening treening : kõikTrennid) {
                for (Treening proovitud : proovitudTreeningud) {
                    if (!Objects.equals(proovitud.getNimi(), treening.getNimi()))
                        proovimataTreeningud.add(treening);
                }
            }
        else
            proovimataTreeningud = kõikTrennid;

        int võimalikeTrennideArv = proovimataTreeningud.size() > 0 ? proovimataTreeningud.size() : kõikTrennid.size();

        int suvaliseTrenniIndeks = (int) (Math.random() * võimalikeTrennideArv);
        return ("Äkki tahaksid proovida seda uut treeningut: "+proovimataTreeningud.get(suvaliseTrenniIndeks).getNimi());

    }

    public abstract String failiRida();

}
