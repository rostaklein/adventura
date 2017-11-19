/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.*;
import GUI.Batoh;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.*;
import uiText.TextoveRozhrani;

/**
 *
 * @author xzenj02, kler00
 */
public class Main extends Application {
    
    private TextArea centralText;
    private IHra hra;
    private TextField zadejPrikazTextField;
    
    private Mapa mapa;
    private MenuLista menuLista;
    private Vychody vychody;
    private Predmety predmety;
    private Postavy postavy;
    private Batoh batoh;

    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void start(Stage primaryStage) {
        hra = new Hra();
        mapa = new Mapa(hra);
        menuLista = new MenuLista(hra, this);
        vychody = new Vychody(hra, this);
        predmety = new Predmety(hra, this);
        postavy = new Postavy(hra, this);
        batoh = new Batoh(hra, this);

        this.setStage(primaryStage);
        
        BorderPane borderPane = new BorderPane();
        
        
        centralText = new TextArea();
        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);
        centralText.setWrapText(true);
  
        borderPane.setCenter(centralText);
        
        Label zadejPrikazLabel = new Label("Zadej příkaz: ");
        Font formatTextu = Font.font("Arial", FontWeight.BOLD, 14);
        zadejPrikazLabel.setFont(formatTextu);

        zadejPrikazTextField = new TextField();
        zadejPrikazTextField.setPrefWidth(150);
        zadejPrikazTextField.setOnAction(event -> {
            String vstupniPrikaz = zadejPrikazTextField.getText();
            zpracujPrikaz(vstupniPrikaz);
            zadejPrikazTextField.setText("");
        });

        Label vychodyLabel = new Label("Kam jít: ");
        vychodyLabel.setFont(formatTextu);
        Label predmetyLabel = new Label("Předměty zde: ");
        predmetyLabel.setFont(formatTextu);
        Label postavyLabel = new Label("Promluv s: ");
        postavyLabel.setFont(formatTextu);


        HBox prikazy = new HBox();
        prikazy.setAlignment(Pos.CENTER);
        prikazy.setPadding(new Insets(10));
        prikazy.setSpacing(10);
        prikazy.getChildren().addAll(vychodyLabel, vychody, predmetyLabel, predmety, postavyLabel, postavy);

        HBox textFieldLine = new HBox();
        textFieldLine.getChildren().addAll(zadejPrikazLabel, zadejPrikazTextField);
        textFieldLine.setAlignment(Pos.CENTER);
        textFieldLine.setSpacing(10);
        VBox dolniLista = new VBox();
        dolniLista.setAlignment(Pos.CENTER);
        dolniLista.getChildren().addAll(textFieldLine, prikazy);

        borderPane.setBottom(dolniLista);
        borderPane.setLeft(mapa);
        borderPane.setTop(menuLista);
        borderPane.setRight(batoh);

        Scene scene = new Scene(borderPane, 1200, 550);

        primaryStage.setTitle("Adventura");

        primaryStage.setScene(scene);
        primaryStage.show();
        zadejPrikazTextField.requestFocus();
    }

    public void zpracujPrikaz(String prikaz){
        String odpovedHry = hra.zpracujPrikaz(prikaz);
        centralText.appendText("\n" + prikaz + "\n");
        centralText.appendText("\n" + odpovedHry + "\n");

        if(hra.konecHry()){
            zadejPrikazTextField.setEditable(false);
            vychody.setDisable(true);
            predmety.setDisable(true);
            postavy.setDisable(true);
            batoh.setDisable(true);
            centralText.appendText(hra.vratEpilog());
        }
    };


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        }
        else{
            if (args[0].equals("-txt")) {
                IHra hra = new Hra();
                TextoveRozhrani textHra = new TextoveRozhrani(hra);
                textHra.hraj();
            }
            else{
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
    }

    /**
     * Přiřazuje novou hru k jednotlivým částem UI.
     * @param hra
     */
    public void newGame(IHra hra){
        this.hra = hra;
        this.mapa.newGame(hra);
        this.vychody.newGame(hra);
        this.predmety.newGame(hra);
        this.postavy.newGame(hra);

        this.vychody.setDisable(false);
        this.predmety.setDisable(false);
        this.postavy.setDisable(false);
        this.batoh.setDisable(false);

        this.centralText.setText(hra.vratUvitani());
        this.zadejPrikazTextField.setEditable(true);
    }

}
