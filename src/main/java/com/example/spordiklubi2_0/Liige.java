package com.example.spordiklubi2_0;

import java.time.LocalDateTime;
import java.util.Date;

public class Liige extends Isik {
    private boolean liikmesusKehtib;
    private LocalDateTime liikmesuseAlgusKuupäev;
    private LocalDateTime LiikmesuseLõppKuupäev;

    public Liige(String isikukood, String eesnimi, String perenimi, String email, int telNumber, boolean liikmesusKehtib, LocalDateTime liikmesuseAlgusKuupäev, LocalDateTime LiikmesuseLõppKuupäev) {
        super(isikukood, eesnimi, perenimi, email, telNumber);
        this.liikmesusKehtib = liikmesusKehtib;
        if (liikmesusKehtib == true) {
            this.liikmesuseAlgusKuupäev = liikmesuseAlgusKuupäev;
            this.LiikmesuseLõppKuupäev = LiikmesuseLõppKuupäev;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Liige{" +
                "liikmesusKehtib=" + liikmesusKehtib +
                ", liikmesuseAlgusKuupäev=" + liikmesuseAlgusKuupäev +
                ", LiikmesuseLõppKuupäev=" + LiikmesuseLõppKuupäev +
                '}';
    }

}

