package com.example.spordiklubi2_0;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Broneering {

    private int ID;
    private Treening treening;
    private Liige liige;

    public Broneering(Treening treening, Liige liige) {
        this.treening = treening;
        this.liige = liige;
        this.ID = (int) (Math.random() * 100);
    }

    public Broneering(Treening treening, Liige liige, int ID) {//üledefineerimine, kui on uus broneering, kasutatakse ülemist konstruktorit, olemasoleva broneeringu sisselugemiseks seda
        this.treening = treening;
        this.liige = liige;
        this.ID = ID;
    }

    public Treening getTreening() {
        return treening;
    }


    public Liige getLiige() {
        return liige;
    }


    @Override
    public String toString() {
        return "Treening: " + treening.getNimi() +
                ", treener: " + treening.getTreeneriNimi() +
                ", kell: " + treening.getAlgusaeg().getHour() + " - " + treening.getLõpuaeg().getHour() +
                ", kuupäev: " + treening.getAlgusaeg().getDayOfMonth() + "." + treening.getAlgusaeg().getMonthValue() +
                ", liige: " + liige.getEesnimi() + " " + liige.getPerenimi() + ", treeningu id: " + ID;
    }

    public int getID() {
        return ID;
    }


    public static Broneering leiaBroneering(int broneeringuID, List<Broneering> bronnid) {
        for (Broneering bronn : bronnid) {
            if (bronn.getID() == broneeringuID)
                return bronn;
        }
        return null;
    }

    public static void teeBroneering(String treeninguNimi, Liige liige, List<Treening> trennid, List<Broneering> bronnid) {
        Treening treening = Treening.leiaTreening(treeninguNimi, trennid);
        int vabuKohti = treening.getVabuKohti();
        if (vabuKohti > 0) {
            Broneering broneering = new Broneering(treening, liige);
            bronnid.add(broneering);
            vabuKohti--;
            treening.setVabuKohti(vabuKohti);
            System.out.println("Broneering treeningusse " + broneering.getTreening().getNimi() + " edukalt tehtud!");
        } else
            System.out.println("Pole vabu kohti.");
    }

    public static void tühistaBroneering(int broneeringuID, List<Broneering> bronnid) {
        Broneering broneering = leiaBroneering(broneeringuID, bronnid);
        Treening treening = broneering.getTreening();
        int vabuKohti = treening.getVabuKohti();
        vabuKohti++;
        treening.setVabuKohti(vabuKohti);
        bronnid.remove(broneering);
        System.out.println("Broneering tühistatud!");

    }

    public static void kuvaMöödunudBroneeringud(Isik isik, List<Broneering> bronnid) {
        for (Broneering broneering : bronnid) {
            if (broneering.getTreening().getAlgusaeg().isBefore(LocalDateTime.now())) {
                if (broneering.getLiige().getIsikukood().equals(isik.getIsikukood()) || broneering.getTreening().getTreeneriNimi().equals(isik.getEesnimi() + isik.getPerenimi())) {
                    System.out.println(broneering);
                }
            }
        }
        if (bronnid.size() == 0) {
            System.out.println("Eelnevad broneeringud puuduvad");
        }
    }

    public static void kuvaTulevasedBroneeringud(Isik isik, List<Broneering> bronnid) {
        for (Broneering broneering : bronnid) {
            if (broneering.getTreening().getAlgusaeg().isAfter(LocalDateTime.now())) {
                if (broneering.getLiige().getIsikukood().equals(isik.getIsikukood()) || broneering.getTreening().getTreeneriNimi().equals(isik.getEesnimi() + isik.getPerenimi())) {
                    System.out.println(broneering);
                }
            }
        }
        if (bronnid.size() == 0) {
            System.out.println("Tulevased broneeringud puuduvad");

        }
    }


    public String failiRida() {
        return ID + "; " + treening.getNimi() + "; " + liige.getIsikukood();
    }

}
