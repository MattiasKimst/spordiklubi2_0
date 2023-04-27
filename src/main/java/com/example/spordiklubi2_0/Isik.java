package com.example.spordiklubi2_0;

public class Isik {
    private String isikukood;
    private String eesnimi;
    private String perenimi;
    private String email;
    private int telNumber;

    public Isik(String isikukood, String eesnimi, String perenimi, String email, int telNumber) {
        this.isikukood = isikukood;
        this.eesnimi = eesnimi;
        this.perenimi = perenimi;
        this.email = email;
        this.telNumber = telNumber;
    }

    @Override
    public String toString() {
        return "Isik{" +
                "isikukood=" + isikukood +
                ", eesnimi='" + eesnimi + '\'' +
                ", perenimi='" + perenimi + '\'' +
                ", email='" + email + '\'' +
                ", telNumber=" + telNumber +
                '}';
    }

    public String getIsikukood() {
        return isikukood;
    }


    public String getEesnimi() {
        return eesnimi;
    }


    public String getPerenimi() {
        return perenimi;
    }


}
