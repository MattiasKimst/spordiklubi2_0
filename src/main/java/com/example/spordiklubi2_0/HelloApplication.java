package com.example.spordiklubi2_0;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.spordiklubi2_0.Peaklass.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class HelloApplication extends Application {
    @Override
    public void start(Stage pealava) throws IOException {




        Button kuvaBronnid = new Button("Kuva broneeringud");
        kuvaBronnid.setOnMousePressed(sündmus-> System.exit(0));
        Button kuvaTrennid = new Button("Kuva treeningud");
        kuvaTrennid.setOnMousePressed(sündmus->System.exit(0));
        Button soovitaTrenni = new Button("Soovita treeningut");
        soovitaTrenni.setOnMousePressed(sündmus->System.exit(0));
        Button kuvaTulevased = new Button("Kuva enda tulevased treeningud");
        kuvaTulevased.setOnMousePressed(sündmus->System.exit(0));


        int nupuLaius = 100;
        int nupuVahe = 5;
        int laius=270;


        List<Button> nupud = List.of(kuvaBronnid,kuvaTrennid,soovitaTrenni,kuvaTulevased);
        for (Button nupp : nupud) {
            nupp.setMinWidth(nupuLaius);
            nupp.setTextFill(Color.BLUE);

        }
        GridPane ruudustik = new GridPane();

        ruudustik.add(kuvaBronnid, 0, 0);
        ruudustik.add(kuvaTrennid, 0, 2);
        ruudustik.add(soovitaTrenni, 0, 3);
        ruudustik.add(kuvaTulevased, 0, 1);

        ruudustik.setHgap(nupuVahe);
        ruudustik.setVgap(nupuVahe);
        ruudustik.setAlignment(Pos.CENTER);
        ruudustik.setMinWidth(laius);
        ruudustik.setMaxWidth(laius);

        Scene stseen = new Scene(ruudustik, 300, 300);
        pealava.setScene(stseen);
        pealava.setTitle("Spordiklubi");
        pealava.setResizable(false);
        pealava.show();



/*
        pealava.setOnCloseRequest(new EventHandler<WindowEvent>() {
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