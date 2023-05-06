package com.example.spordiklubi2_0;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

import static com.example.spordiklubi2_0.Peaklass.*;
import static java.lang.Integer.parseInt;

public class Rakendus extends Application {

    public boolean tuvastaInimene(String isikukood, List<Liige> isikud) {
        for (Liige inimene : isikud
        ) {
            if (inimene.getIsikukood().equals(isikukood)) {
                return true;
            }

        }
        return false;
    }

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

    public Liige leiaisik(String isikukood, List<Liige> isikud) {
        for (Liige liige : isikud
        ) {
            if (liige.getIsikukood().equals(isikukood)) return liige;
        }
        return null;
    }



    @Override
    public void start(Stage pealava) throws IOException {

        //andmed
        List<Liige> isikud = loeSisseIsikud("isikud.txt");
        List<Treening> trennid = loeSissetreeningud("treeningud.txt");
        List<Broneering> broneeringud = loeSisseBroneeringud("broneeringud.txt", trennid, isikud);


        //akna suurused
        int aknaLaius = 600;
        int aknaKõrgus = 600;


        //nupusuurused

        int nupuVahe = 20;
        int laius = 270;



        //avalehe stseen

        //avalehe nupud
        Button kuvaBronnid = new Button("Kuva broneeringud");
        Button kuvaTrennid = new Button("Kuva treeningud");
        Button soovitaTrenni = new Button("Soovita treeningut");
        Button kuvaTulevased = new Button("Kuva enda tulevased treeningud");


        GridPane ruudustik = new GridPane();

        ruudustik.add(kuvaBronnid, 0, 3);
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

        Scene avaleheStseen = new Scene(ruudustik, aknaLaius, aknaKõrgus);

        //avalehe lõpp




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

        PasswordField parooliVäli = new PasswordField();
        grid.add(parooliVäli, 1, 8);

        Label vihje = new Label("Vihje: kasuta 39912126758, parooli pole vaja");
        grid.add(vihje, 1, 12);


        Button logimisNupp = new Button("Logi sisse");
        Button tagasiNuppLogimine = new Button("Tagasi");

        HBox logimisHBox = new HBox(10);

        logimisHBox.setAlignment(Pos.BOTTOM_RIGHT);
        logimisHBox.getChildren().add(logimisNupp);
        logimisHBox.getChildren().add(tagasiNuppLogimine);
        grid.add(logimisHBox, 1, 10);

        Scene logimisStseen = new Scene(grid, aknaLaius, aknaKõrgus);

        final String[] kasutajaIsikukood = new String[1];
        final String[] kasutajaParool = new String[1];


        logimisNupp.setOnMousePressed(sündmus -> {
            kasutajaIsikukood[0] = isikukoodiVäli.getText();//kasutajaisikukoodis hoiame läbivalt isikukoodi, mille saime logimisel, String[] tüüpi pikkusega üks, sest lambda avaldistes tavaline String ei saa olla
            kasutajaParool[0] = parooliVäli.getText();//salvestame siia lihtsalt paroolivälja info, midagi sellega peale ei hakka
            pealava.setScene(avaleheStseen);

        }); //logimise lõpp


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
                Label teade = new Label(Broneering.teeBroneering(valitudTrenn.getNimi(), broneerija, trennid, broneeringud));


                VBox vbox4 = new VBox(teade); //see koodijupp kordub palju, vaja kuidagi ühe korraga lahendada
                Button tagasinuppTeateStseen = new Button("Tagasi");
                vbox4.getChildren().add(tagasinuppTeateStseen);
                Scene TeateStseen = new Scene(vbox4, 500, 500);
                pealava.setScene(TeateStseen);
                tagasinuppTeateStseen.setOnMousePressed(sündmus -> pealava.setScene(avaleheStseen));
            }


        });


        VBox vboxTrennideKuvamine = new VBox(tableView);
        Button tagasiNuppTrennid = new Button("Tagasi");

        Label kuidasBroneerida = new Label("Broneerimiseks vajuta sobiva trenni real");
        kuidasBroneerida.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        vboxTrennideKuvamine.getChildren().add(kuidasBroneerida);
        vboxTrennideKuvamine.getChildren().add(tagasiNuppTrennid);
        Scene trennideStseen = new Scene(vboxTrennideKuvamine, aknaLaius, aknaKõrgus);

        //treeningute kuvamise lõpp


        //Broneeringute kuvamise stseen
        TableView tableView2 = new TableView();

        TableColumn<Treening, String> broneeringuVeerg1 =
                new TableColumn<>("Nimi");

        broneeringuVeerg1.setCellValueFactory(
                new PropertyValueFactory<>("nimi"));


        TableColumn<Treening, LocalDateTime> broneeringuVeerg2 =
                new TableColumn<>("Algusaeg");

        broneeringuVeerg2.setCellValueFactory(
                new PropertyValueFactory<>("algusaeg"));

        TableColumn<Treening, LocalDateTime> broneeringuVeerg3 =
                new TableColumn<>("Lõpuaeg");

        broneeringuVeerg3.setCellValueFactory(
                new PropertyValueFactory<>("lõpuaeg"));


        tableView2.getColumns().add(broneeringuVeerg1);
        tableView2.getColumns().add(broneeringuVeerg2);
        tableView2.getColumns().add(broneeringuVeerg3);

        for (int i = 0; i < broneeringud.size(); i++) {
            tableView2.getItems().add(
                    broneeringud.get(i).getTreening());
        }

        VBox vboxBroneeringuteKuvamine = new VBox(tableView2);
        Button tagasiNuppBronnid = new Button("Tagasi");
        vboxBroneeringuteKuvamine.getChildren().add(tagasiNuppBronnid);

        Scene stseenBroneeringud = new Scene(vboxBroneeringuteKuvamine, aknaLaius, aknaKõrgus);

        //broneeringute kuvamise lõpp


        // soovituse ekraan


        List<Broneering> bronnid = leiaIsikuBronnid(kasutajaIsikukood[0], broneeringud, isikud);
        Liige inimene = leiaisik(kasutajaIsikukood[0], isikud);


        Label soovitus = new Label(Treening.soovitaTreeningut(inimene, bronnid, trennid));
        soovitus.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        VBox vboxSoovitaTrenni = new VBox(soovitus);
        Button tagasiNuppSoovitus = new Button("Tagasi");
        vboxSoovitaTrenni.getChildren().add(tagasiNuppSoovitus);
        Scene trenniSoovituseStseen = new Scene(vboxSoovitaTrenni, aknaLaius, aknaKõrgus);


        //soovituse kuvamise lõpp


        //Enda tulevaste broneeringute vaatamine

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

            List<Broneering> isikubronnid=leiaIsikuBronnid(kasutajaIsikukood[0],broneeringud,isikud);
            for (int i = 0; i < isikubronnid.size(); i++) {
                Broneering bronn = isikubronnid.get(i);
                tableView3.getItems().add(bronn.getTreening());
            }



        VBox vboxKuvaTulevased = new VBox(tableView3);
            Button tagasiNuppKuvaTulevased = new Button("Tagasi");
            vboxKuvaTulevased.getChildren().add(tagasiNuppKuvaTulevased);
            Scene stseenEndaTreeningud = new Scene(vboxKuvaTulevased, aknaLaius, aknaKõrgus);
            pealava.setScene(stseenEndaTreeningud);
            tagasiNuppKuvaTulevased.setOnMousePressed(sündmus2 -> pealava.setScene(avaleheStseen));

        });
        //enda tulevaste vaatamise lõpp


        //avaekraani nuppude tegevused


        kuvaTrennid.setOnMousePressed(sündmus -> pealava.setScene(trennideStseen));
        kuvaBronnid.setOnMousePressed(sündmus -> pealava.setScene(stseenBroneeringud));
        soovitaTrenni.setOnMousePressed(sündmus -> pealava.setScene(trenniSoovituseStseen));


        tagasiNuppLogimine.setOnMousePressed(sündmus -> pealava.setScene(avaleheStseen));
        tagasiNuppTrennid.setOnMousePressed(sündmus -> pealava.setScene(avaleheStseen));
        tagasiNuppBronnid.setOnMousePressed(sündmus -> pealava.setScene(avaleheStseen));
        tagasiNuppSoovitus.setOnMousePressed(sündmus -> pealava.setScene(avaleheStseen));


        pealava.setScene(logimisStseen);
        pealava.setTitle("Spordiklubi");

        pealava.show();


       pealava.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                //lõpus kirjutab  broneeringute järjendite lõppseisu peale programmi tööd failidesse

                try {
                    kirjutaBroneeringudFaili("broneeringud.txt", broneeringud);
                } catch (IOException e) {
                    System.out.println("Broneeringute faili kirjutamine ebaõnnestus. Põhjus: " +e.getMessage());
                    throw new RuntimeException(e);
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