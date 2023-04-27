package com.example.spordiklubi2_0;

import java.time.LocalDateTime;
import java.util.Date;

public class Treener extends Töötaja {
    private boolean onRühmatreener;
    private boolean onIndividuaalTreener;

    public Treener(String isikukood, String eesnimi, String perenimi, String email, int telNumber, boolean liikmesusKehtib, LocalDateTime liikmesuseAlgusKuupäev, LocalDateTime LiikmesuseLõppKuupäev, LocalDateTime töösuhteAlgusAeg, LocalDateTime töösuhteLõppAeg, boolean onRühmatreener, boolean onIndividuaalTreener) {
        super(isikukood, eesnimi, perenimi, email, telNumber, liikmesusKehtib, liikmesuseAlgusKuupäev, LiikmesuseLõppKuupäev, töösuhteAlgusAeg, töösuhteLõppAeg);
        this.onRühmatreener = onRühmatreener;
        this.onIndividuaalTreener = onIndividuaalTreener;
    }

    public void setOnIndividuaalTreener(boolean onIndividuaalTreener) {
        this.onIndividuaalTreener = onIndividuaalTreener;
    }

    @Override
    public String toString() {
        return super.toString() + "Treener{" +
                "onRühmatreener=" + onRühmatreener +
                ", onIndividuaalTreener=" + onIndividuaalTreener +
                '}';
    }

}
