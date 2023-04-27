package com.example.spordiklubi2_0;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rühmatreening extends Treening {

    private String treeninguStiil;
    private String raskusAste;
    private int keskmineKulutatudKaloriteArv;

    public Rühmatreening(String nimi, LocalDateTime algusaeg, LocalDateTime lõpuaeg, int vabuKohti, int kohtiKokku, String treeneriNimi, String klubi, String asukoht, String treeninguStiil, String raskusAste, int keskmineKulutatudKaloriteArv) {
        super(nimi, algusaeg, lõpuaeg, vabuKohti, kohtiKokku, treeneriNimi, klubi, asukoht);
        this.treeninguStiil = treeninguStiil;
        this.raskusAste = raskusAste;
        this.keskmineKulutatudKaloriteArv = keskmineKulutatudKaloriteArv;
    }

    public String getTreeninguStiil() {
        return treeninguStiil;
    }


    public String getRaskusAste() {
        return raskusAste;
    }


    @Override
    public String toString() {
        return "Rühmatreening, " + super.toString() +
                ", treeningustiil: " + treeninguStiil +
                ", raskusaste: " + raskusAste +
                ", keskmine kulutatav kalorite arv: " + keskmineKulutatudKaloriteArv + "kcal";
    }

    public String failiRida() { // tagastab sõne klassi isendi kohta faili kirjutamiseks sobilikul kujul, st tagastatav sõne on faili rida
        String nõustamine;
        DateTimeFormatter kuupäevaFormaat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");
        return this.getNimi() + "; " + this.getAlgusaeg().format(kuupäevaFormaat) + "; " + this.getLõpuaeg().format(kuupäevaFormaat) + "; " + this.getVabuKohti() + "; " + this.getKohtiKokku() + "; " + this.getTreeneriNimi() + "; " + this.getKlubi() + "; " + this.getAsukoht() + "; " + this.getTreeninguStiil() + "; " + this.getRaskusAste() + "; " + this.keskmineKulutatudKaloriteArv;
    }
}
