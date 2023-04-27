package com.example.spordiklubi2_0;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Personaaltreening extends Treening {

    private double tunniHind;
    private boolean toitumisnõustamineVõimalik;

    public Personaaltreening(String nimi, LocalDateTime algusaeg, LocalDateTime lõpuaeg, int vabuKohti, int kohtiKokku, String treeneriNimi, String klubi, String asukoht, double tunniHind, boolean toitumisnõustamineVõimalik) {
        super(nimi, algusaeg, lõpuaeg, vabuKohti, kohtiKokku, treeneriNimi, klubi, asukoht);
        this.tunniHind = tunniHind;
        this.toitumisnõustamineVõimalik = toitumisnõustamineVõimalik;
    }


    @Override
    public String toString() {
        return "Personaaltreening " + super.toString() +
                ", tunni hind: " + tunniHind + "€" +
                ", toitumisnõustamine võimalik: " + (toitumisnõustamineVõimalik ? "jah" : "ei");
    }

    public String failiRida() { // tagastab sõne klassi isendi kohta faili kirjutamiseks sobilikul kujul, st tagastatav sõne on faili rida
        String nõustamine;
        DateTimeFormatter kuupäevaFormaat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");
        if (toitumisnõustamineVõimalik) {
            nõustamine = "toitumisnõustamine võimalik";
        } else {
            nõustamine = "toitumisnõustamine pole võimalik";
        }
        return this.getNimi() + "; " + this.getAlgusaeg().format(kuupäevaFormaat) + "; " + this.getLõpuaeg().format(kuupäevaFormaat) + "; " + this.getVabuKohti() + "; " + this.getKohtiKokku() + "; " + this.getTreeneriNimi() + "; " + this.getKlubi() + "; " + this.getAsukoht() + "; " + this.tunniHind + "; " + nõustamine;
    }
}
