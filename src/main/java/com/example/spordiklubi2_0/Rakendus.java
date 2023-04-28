package com.example.spordiklubi2_0;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.spordiklubi2_0.Peaklass.*;
import static java.lang.Integer.parseInt;

public class Rakendus extends Application {
    @Override
    public void start(Stage pealava) throws IOException {

        //andmed
        List<Liige> isikud = loeSisseIsikud("isikud.txt");
        List<Treening> trennid = loeSissetreeningud("treeningud.txt");
        List<Broneering> broneeringud = loeSisseBroneeringud("broneeringud.txt", trennid, isikud);


        //nupusuurused
        int nupuLaius = 100;
        int nupuVahe = 5;
        int laius = 270;


        //avalehe stseen

        //avalehe nupud
        Button kuvaBronnid = new Button("Kuva broneeringud");
        Button kuvaTrennid = new Button("Kuva treeningud");
        Button soovitaTrenni = new Button("Soovita treeningut");
        Button kuvaTulevased = new Button("Kuva enda tulevased treeningud");



        //nuppudele ühtse välimuse andmine
        List<Button> nupud = List.of(kuvaBronnid, kuvaTrennid, soovitaTrenni, kuvaTulevased);
        for (Button nupp : nupud) {
            nupp.setMinWidth(nupuLaius);
            nupp.setTextFill(Color.BLUE);

        }
        GridPane ruudustik = new GridPane();

        ruudustik.add(kuvaBronnid, 0, 4);
        ruudustik.add(kuvaTrennid, 1, 4);
        ruudustik.add(soovitaTrenni, 2, 4);
        ruudustik.add(kuvaTulevased, 3, 4);

        ruudustik.setHgap(nupuVahe);
        ruudustik.setVgap(nupuVahe);
        ruudustik.setAlignment(Pos.CENTER);
        ruudustik.setMinWidth(laius);
        ruudustik.setMaxWidth(laius);

        Scene stseenAvaleht = new Scene(ruudustik, 500, 500);


        //logimise stseen https://docs.oracle.com/javafx/2/get_started/form.htm

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text logimisEkraaniPealkiri = new Text("Sisselogimine nõutav");
        logimisEkraaniPealkiri.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(logimisEkraaniPealkiri, 0, 0, 2, 1);

        Label isikukood = new Label("isikukood:");
        grid.add(isikukood, 0, 1);

        TextField sisestatudIsikukood = new TextField();
        grid.add(sisestatudIsikukood, 1, 1);

        Label parool = new Label("Parool");
        grid.add(parool, 0, 2);

        PasswordField parooliVäli = new PasswordField();
        grid.add(parooliVäli, 1, 2);

        Scene logimisStseen = new Scene(grid, 300, 275);


        Button logimisNupp = new Button("Logi sisse");
        HBox logimisHBox = new HBox(10);
        logimisHBox.setAlignment(Pos.BOTTOM_RIGHT);
        logimisHBox.getChildren().add(logimisNupp);
        grid.add(logimisHBox, 1, 4);


        // treeningute kuvamise steen https://jenkov.com/tutorials/javafx/tableview.html

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

        //hetkel kõiki veerge pole veel

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

        // set selection mode to only 1 row
        selectionModel.setSelectionMode(
                SelectionMode.SINGLE);
        ObservableList selectedItems =
                selectionModel.getSelectedItems();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        pealava.setScene(logimisStseen);
                        logimisNupp.setOnAction(e -> {
                            Treening valitudTrenn = (Treening) tableView.getSelectionModel().getSelectedItem();
                            for (Liige liige : isikud
                            ) {
                                if (liige.getIsikukood().equals(sisestatudIsikukood.getText())) {
                                    Label teade = new Label(Broneering.teeBroneering(valitudTrenn.getNimi(), liige, trennid, broneeringud));
                                    VBox vbox4 = new VBox(teade); //see koodijupp kordub palju, vaja kuidagi ühe korraga lahendada
                                    Scene TeateStseen = new Scene(vbox4, 500, 500);
                                    pealava.setScene(TeateStseen);
                                }


                            }
                        });
                    }
                }
        );


        VBox vbox = new VBox(tableView);
        Scene trennideStseen = new Scene(vbox, 500, 500);







        //Broneeringute kuvamise stseen
        //vaja muuta nii, et näitaks ainult isiku enda broneeringuid
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

        //hetkel kõiki veerge pole veel

        tableView2.getColumns().add(broneeringuVeerg1);
        tableView2.getColumns().add(broneeringuVeerg2);
        tableView2.getColumns().add(broneeringuVeerg3);

        for (int i = 0; i < broneeringud.size(); i++) {
            tableView2.getItems().add(
                    broneeringud.get(i).getTreening());
        }

        VBox vbox2 = new VBox(tableView2);
        Scene stseenBroneeringud = new Scene(vbox2, 500, 500);


        // soovituse ekraan


        logimisNupp.setOnAction(e ->
                {
                    for (Liige inimene : isikud
                    ) {
                        if (inimene.getIsikukood().equals(sisestatudIsikukood.getText())) {
                            List<Broneering> bronnid = new ArrayList<>();
                            for (Broneering bronn : broneeringud
                            ) {
                                if (bronn.getLiige() == inimene) {
                                    bronnid.add(bronn);
                                }
                            }
                            Label soovitus = new Label(Treening.soovitaTreeningut(inimene, bronnid, trennid));

                            VBox vbox3 = new VBox(soovitus);
                            Scene trenniSoovituseStseen = new Scene(vbox3, 500, 500);
                            pealava.setScene(trenniSoovituseStseen);


                        }
                    }


                }
        );


        //avaekraani nuppude tegevused
        kuvaTrennid.setOnMousePressed(sündmus -> pealava.setScene(trennideStseen));
        kuvaBronnid.setOnMousePressed(sündmus -> pealava.setScene(stseenBroneeringud));
        soovitaTrenni.setOnMousePressed(sündmus -> pealava.setScene(logimisStseen));


        pealava.setScene(stseenAvaleht);
        pealava.setTitle("Spordiklubi");
        pealava.setResizable(false);
        pealava.show();


        //kui ristist kinni panna, siis salvestab asjad faili ja väljub
        //KATKI, KUI SEDA KOODIJUPPI KASUTADA, KUSTUTAB .txt FAILIDE SISUD ÄRA

       /* pealava.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                //lõpus kirjutab trennide ja broneeringute järjendite lõppseisu peale programmi tööd failidesse
                kirjutaTreeningudFaili("treeningud.txt", trennid);
                kirjutaBroneeringudFaili("broneeringud.txt", broneeringud);
                Platform.exit();
                System.exit(0);
            }
        });

*/


    }

    public static void main(String[] args) {
        launch();
    }
}