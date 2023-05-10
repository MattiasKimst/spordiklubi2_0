package com.example.spordiklubi2_0;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.spordiklubi2_0.Peaklass.*;

import static java.lang.Integer.parseInt;
import static javafx.scene.paint.Color.RED;

public class Rakendus extends Application {


    // meetod, mis teeb kindlaks, kas etteantud isikukoodiga isik on spordiklubi liige
    public boolean tuvastaInimene(String isikukood, List<Liige> isikud) {
        for (Liige inimene : isikud
        ) {
            if (inimene.getIsikukood().equals(isikukood)) {
                return true;
            }

        }
        return false;
    }


    //meetod, mis leiab listina etteantud isikukoodi järgi selle isiku broneeirngud
    public List<Broneering> leiaIsikuBronnid(String isikukood, List<Broneering> broneeringud, List<Liige> isikud) {
        List<Broneering> bronnid = new ArrayList<>();
        for (Liige inimene : isikud
        ) {
            if (inimene.getIsikukood().equals(isikukood)) {

                for (Broneering bronn : broneeringud
                ) {
                    if (bronn.getLiige() == inimene) {
                        bronnid.add(bronn);

                    }
                }
            }
        }
        return bronnid;
    }


    //meetod, mis leiab isikukoodi järgi Liige tüüpi objekti
    public Liige leiaisik(String isikukood, List<Liige> isikud) {
        for (Liige liige : isikud
        ) {
            if (liige.getIsikukood().equals(isikukood)) return liige;
        }
        return null;
    }


    @Override
    public void start(Stage pealava) throws IOException {

        //nendes listides hoiame andmeid
        List<Liige> isikud = loeSisseIsikud("isikud.txt");
        List<Treening> trennid = loeSissetreeningud("treeningud.txt");
        List<Broneering> broneeringud = loeSisseBroneeringud("broneeringud.txt", trennid, isikud);


        //akna suuruse algväärtused
        int aknaLaius = 600;
        int aknaKõrgus = 600;


        //nupuvahede suurused avalehel
        int nupuVahe = 20;
        int laius = 270;


        //logimise stseen idee: https://docs.oracle.com/javafx/2/get_started/form.htm

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 25, 25, 25));

        Text logimisEkraanPealkiri = new Text("Spordiklubi 2.0");
        logimisEkraanPealkiri.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        grid.add(logimisEkraanPealkiri, 0, 0, 2, 1);

        Text logimisEkraaniPealkiri = new Text("Logi sisse");
        logimisEkraaniPealkiri.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(logimisEkraaniPealkiri, 0, 6, 2, 1);

        Label isikukood = new Label("isikukood:");
        grid.add(isikukood, 0, 7);

        TextField isikukoodiVäli = new TextField();
        grid.add(isikukoodiVäli, 1, 7);

        Label parool = new Label("Parool");
        grid.add(parool, 0, 8);

        PasswordField parooliVäli = new PasswordField(); //parooliväli on olemas lihtsalt toreduse pärast, et asi meenutaks päris programmi; parooli kusagil ei kasutata
        grid.add(parooliVäli, 1, 8);

        Label vihje = new Label("Vihje: kasuta 39912126758, parooli pole vaja"); //info, kuidas programmi testida
        grid.add(vihje, 1, 12);


        Button logimisNupp = new Button("Logi sisse");

        HBox logimisHBox = new HBox(10);

        logimisHBox.setAlignment(Pos.BOTTOM_RIGHT);
        logimisHBox.getChildren().add(logimisNupp);

        grid.add(logimisHBox, 1, 10);

        Label hoiatus = new Label(""); //kasutame seda, kui isikukood on vale
        hoiatus.setTextFill(RED);
        grid.add(hoiatus, 1, 13);

        Scene logimisStseen = new Scene(grid, aknaLaius, aknaKõrgus);


        final String[] kasutajaIsikukood = new String[1];//java ei lubanud lambda avaldistes lihtsalt String tüüpi muutujat kasutada, seetõttu on String[] tüüpi
        final String[] kasutajaParool = new String[1];


        //logimise lõpp


        //avalehe stseen

        //avalehe nupud

        Button kuvaTrennid = new Button("Kuva treeningud ja broneeri");
        Button soovitaTrenni = new Button("Soovita treeningut");
        Button kuvaTulevased = new Button("Kuva enda tulevased treeningud");


        GridPane ruudustik = new GridPane();


        ruudustik.add(kuvaTrennid, 0, 4);
        ruudustik.add(soovitaTrenni, 0, 5);
        ruudustik.add(kuvaTulevased, 0, 6);

        Text menüüPealkiri = new Text("Spordiklubi 2.0");
        menüüPealkiri.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        ruudustik.add(menüüPealkiri, 0, 0, 2, 1);

        Text menüü = new Text("Tee oma valik: ");
        menüü.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        ruudustik.add(menüü, 0, 2, 2, 1);

        Text vahe = new Text(" ");
        ruudustik.add(vahe, 0, 1, 2, 1);


        ruudustik.setVgap(nupuVahe);
        ruudustik.setAlignment(Pos.CENTER);
        ruudustik.setMinWidth(laius);
        ruudustik.setMaxWidth(laius);

        Scene avaleheStseen = new Scene(ruudustik, pealava.getWidth(), pealava.getHeight());


        //avalehe lõpp


        // treeningute kuvamise steen, tabeli loomine lehelt https://jenkov.com/tutorials/javafx/tableview.html

        TableView tableView = new TableView();

        TableColumn<Treening, String> veerg1 =
                new TableColumn<>("Nimi");

        veerg1.setCellValueFactory(
                new PropertyValueFactory<>("nimi"));


        TableColumn<Treening, LocalDateTime> veerg2 =
                new TableColumn<>("Algusaeg");

        veerg2.setCellValueFactory(
                new PropertyValueFactory<>("algusaeg"));

        TableColumn<Treening, LocalDateTime> veerg3 =
                new TableColumn<>("Lõpuaeg");

        veerg3.setCellValueFactory(
                new PropertyValueFactory<>("lõpuaeg"));


        tableView.getColumns().add(veerg1);
        tableView.getColumns().add(veerg2);
        tableView.getColumns().add(veerg3);


        // trennide listist info ülekandmine tabelisse
        for (int i = 0; i < trennid.size(); i++) {
            tableView.getItems().add(
                    trennid.get(i));
        }

        TableView.TableViewSelectionModel<Treening> selectionModel =
                tableView.getSelectionModel();

        // et valida saaks ainult ühe rea
        selectionModel.setSelectionMode(
                SelectionMode.SINGLE);
        ObservableList valitud =
                selectionModel.getSelectedItems();


        tableView.getSelectionModel().selectedItemProperty().addListener((obs, vanaValik, uusValik) -> {
            if (uusValik != null) {

                Treening valitudTrenn = (Treening) tableView.getSelectionModel().getSelectedItem();
                Liige broneerija = leiaisik(kasutajaIsikukood[0], isikud);
                String teade = Broneering.teeBroneering(valitudTrenn.getNimi(), broneerija, trennid, broneeringud);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);//kuvab dialoogiakna salvestamise õnnestumise kohta
                alert.setTitle("Broneerimine");
                alert.setHeaderText(null);
                alert.setContentText(teade);

                alert.showAndWait();


            }


        });


        VBox vboxTrennideKuvamine = new VBox(tableView);
        Button tagasiNuppTrennid = new Button("Tagasi");

        Label kuidasBroneerida = new Label("Broneerimiseks vajuta sobiva trenni real");
        kuidasBroneerida.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        vboxTrennideKuvamine.getChildren().add(kuidasBroneerida);
        vboxTrennideKuvamine.getChildren().add(tagasiNuppTrennid);

        Scene trennideStseen = new Scene(vboxTrennideKuvamine, pealava.getWidth(), pealava.getHeight());


        //treeningute kuvamise lõpp


        // soovituse stseen


        List<Broneering> bronnid = leiaIsikuBronnid(kasutajaIsikukood[0], broneeringud, isikud);
        Liige inimene = leiaisik(kasutajaIsikukood[0], isikud);


        Label soovitus = new Label(Treening.soovitaTreeningut(inimene, bronnid, trennid));
        soovitus.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Label juhis = new Label("(Uue soovituse saamiseks vajuta klahvi j )");
        juhis.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));

        GridPane ruudustikSoovitus = new GridPane();
        ruudustikSoovitus.add(soovitus, 0, 1);
        ruudustikSoovitus.add(juhis, 0, 3);


        ruudustikSoovitus.setVgap(nupuVahe);
        ruudustikSoovitus.setAlignment(Pos.CENTER);

        Button tagasiNuppSoovitus = new Button("Tagasi");
        ruudustikSoovitus.add(tagasiNuppSoovitus, 0, 2);


        Scene trenniSoovituseStseen = new Scene(ruudustikSoovitus, pealava.getWidth(), pealava.getHeight());

        //soovituse stseeni lõpp


        //Enda tulevaste broneeringute vaatamise stseen

        kuvaTulevased.setOnMousePressed(sündmus ->

        {
            TableView tableView3 = new TableView();

            TableColumn<Treening, String> endaTrennideVeerg1 =
                    new TableColumn<>("Nimi");

            endaTrennideVeerg1.setCellValueFactory(
                    new PropertyValueFactory<>("nimi"));


            TableColumn<Treening, LocalDateTime> endaTrennideveerg2 =
                    new TableColumn<>("Algusaeg");

            endaTrennideveerg2.setCellValueFactory(
                    new PropertyValueFactory<>("algusaeg"));

            TableColumn<Treening, LocalDateTime> endaTrennideVeerg3 =
                    new TableColumn<>("Lõpuaeg");

            endaTrennideVeerg3.setCellValueFactory(
                    new PropertyValueFactory<>("lõpuaeg"));


            tableView3.getColumns().add(endaTrennideVeerg1);
            tableView3.getColumns().add(endaTrennideveerg2);
            tableView3.getColumns().add(endaTrennideVeerg3);

            List<Broneering> isikubronnid = leiaIsikuBronnid(kasutajaIsikukood[0], broneeringud, isikud);
            for (int i = 0; i < isikubronnid.size(); i++) {
                Broneering bronn = isikubronnid.get(i);
                tableView3.getItems().add(bronn.getTreening());
            }


            VBox vboxKuvaTulevased = new VBox(tableView3);
            Button tagasiNuppKuvaTulevased = new Button("Tagasi");
            vboxKuvaTulevased.getChildren().add(tagasiNuppKuvaTulevased);
            Scene stseenEndaTreeningud = new Scene(vboxKuvaTulevased, pealava.getWidth(), pealava.getHeight());

            final int lavaLaius = (int) pealava.getWidth();
            final int lavaKõrgus = (int) pealava.getHeight();

            pealava.setScene(stseenEndaTreeningud);
            pealava.setWidth(lavaLaius);
            pealava.setHeight(lavaKõrgus);


            tagasiNuppKuvaTulevased.setOnMousePressed(sündmus2 -> {

                pealava.setScene(avaleheStseen);
            });

        });
        //enda tulevaste vaatamise stseeni lõpp


        //logimisnupu vajutamise tegevused:

        logimisNupp.setOnMousePressed(sündmus -> {

            try {
                if (tuvastaInimene(isikukoodiVäli.getText(), isikud)) {//meetod tuvastainimene tagastab true, kui vastav isikukood kuulub mõnele liikmele
                    kasutajaIsikukood[0] = isikukoodiVäli.getText();//kasutajaisikukoodis hoiame läbivalt isikukoodi, mille saime logimisel, String[] tüüpi pikkusega üks, sest lambda avaldistes tavaline String ei saa olla
                    kasutajaParool[0] = parooliVäli.getText();//salvestame siia lihtsalt paroolivälja info, midagi sellega peale ei hakka


                    //jätame enne stseeni vahetamist meelde eelneva aknasuuruse ja peale stseeni vahetamist kanname selle uuele stseenile üle,
                    //muidu kui muuta mingi stseeni jookul akna suurust, siis stseenivahetusel taastub algväärtus, paremat võimalust selle lahendamiseks ei leidnud
                    final int lavaLaius = (int) pealava.getWidth();
                    final int lavaKõrgus = (int) pealava.getHeight();
                    pealava.setScene(avaleheStseen);
                    pealava.setWidth(lavaLaius);
                    pealava.setHeight(lavaKõrgus);


                } else {
                    throw new ValeVõiViganeIsikukood("Vigane isikukood või ei ole sellise isikukoodiga liiget"); //kui liikmete seas pole vastavat isikukoodi, viskame erindi

                }
            } catch (ValeVõiViganeIsikukood e) {
                hoiatus.setText(e.getMessage());
            }

        });


        //avaekraani nuppude tegevused


        kuvaTrennid.setOnMousePressed(sündmus -> {
            //jätame enne stseeni vahetamist meelde eelneva aknasuuruse ja peale stseeni vahetamist kanname selle uuele stseenile üle,
            //muidu kui muuta mingi stseeni jookul akna suurust, siis stseenivahetusel taastub algväärtus, paremat võimalust selle lahendamiseks ei leidnud
            final int lavaLaius = (int) pealava.getWidth();
            final int lavaKõrgus = (int) pealava.getHeight();
            pealava.setScene(trennideStseen);
            pealava.setWidth(lavaLaius);
            pealava.setHeight(lavaKõrgus);
        });


        soovitaTrenni.setOnMousePressed(sündmus -> {

            final int lavaLaius = (int) pealava.getWidth();
            final int lavaKõrgus = (int) pealava.getHeight();

            pealava.setScene(trenniSoovituseStseen);
            pealava.setWidth(lavaLaius);
            pealava.setHeight(lavaKõrgus);

        });


        tagasiNuppTrennid.setOnMousePressed(sündmus -> {

            final int lavaLaius = (int) pealava.getWidth();
            final int lavaKõrgus = (int) pealava.getHeight();

            pealava.setScene(avaleheStseen);
            pealava.setWidth(lavaLaius);
            pealava.setHeight(lavaKõrgus);
        });


        tagasiNuppSoovitus.setOnMousePressed(sündmus -> {
            final int lavaLaius = (int) pealava.getWidth();
            final int lavaKõrgus = (int) pealava.getHeight();

            pealava.setScene(avaleheStseen);
            pealava.setWidth(lavaLaius);
            pealava.setHeight(lavaKõrgus);
        });

        trenniSoovituseStseen.setOnKeyPressed(sündmus -> { //uue soovituse genereerimine klahvivajutusel
            if (sündmus.getCode() == KeyCode.J) {
                soovitus.setText(Treening.soovitaTreeningut(inimene, bronnid, trennid));
            }
        });


        pealava.setScene(logimisStseen);
        pealava.setTitle("Spordiklubi");

        pealava.show();


        pealava.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                //lõpus kirjutab  broneeringute järjendite lõppseisu peale programmi tööd failidesse

                try {
                    kirjutaBroneeringudFaili("broneeringud.txt", broneeringud);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);//kuvab dialoogiakna slavestamise õnnestumise kohta
                    alert.setTitle("Salvestamine");
                    alert.setHeaderText(null);
                    alert.setContentText("Broneeringud edukalt salvestatud ");

                    alert.showAndWait();
                    //throw new IOException(); //kui salvestamine ebaõnnestub, tuleb ette aken, mis ütleb seda; see rida on  tolle akna testimiseks
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Salvestamine ebaõnnestus");
                    alert.setHeaderText(null);
                    alert.setContentText("Broneeringute faili kirjutamine ebaõnnestus. ");

                    alert.showAndWait();


                }
                Platform.exit();
                System.exit(0);
            }
        });


    }

    public static void main(String[] args) {
        launch();
    }
}