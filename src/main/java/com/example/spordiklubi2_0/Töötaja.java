package com.example.spordiklubi2_0;

import java.time.LocalDateTime;

public class Töötaja extends Liige {
    private LocalDateTime töösuhteAlgusAeg;
    private LocalDateTime töösuhteLõppAeg;

    public Töötaja(String isikukood, String eesnimi, String perenimi, String email, int telNumber, boolean liikmesusKehtib, LocalDateTime liikmesuseAlgusKuupäev, LocalDateTime LiikmesuseLõppKuupäev, LocalDateTime töösuhteAlgusAeg, LocalDateTime töösuhteLõppAeg) {
        super(isikukood, eesnimi, perenimi, email, telNumber, liikmesusKehtib, liikmesuseAlgusKuupäev, LiikmesuseLõppKuupäev);
        this.töösuhteAlgusAeg = töösuhteAlgusAeg;
        this.töösuhteLõppAeg = töösuhteLõppAeg;
    }

    @Override
    public String toString() {
        return super.toString() + "Töötaja{" +
                "töösuhteAlgusAeg=" + töösuhteAlgusAeg +
                ", töösuhteLõppAeg=" + töösuhteLõppAeg +
                '}';
    }

}
