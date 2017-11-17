/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.Mapa;
import GUI.MenuLista;
import GUI.Vychody;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.*;
import uiText.TextoveRozhrani;

/**
 *
 * @author xzenj02
 */
public class Main extends Application {
    
    private TextArea centralText;
    private IHra hra;
    private TextField zadejPrikazTextField;
    
    private Mapa mapa;
    private MenuLista menuLista;
    private Vychody vychody;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Vychody getVychody() {
        return vychody;
    }

    @Override
    public void start(Stage primaryStage) {
        hra = new Hra();
        mapa = new Mapa(hra);
        menuLista = new MenuLista(hra, this);
        vychody = new Vychody(hra);

        this.setStage(primaryStage);
        
        BorderPane borderPane = new BorderPane();
        
        
        centralText = new TextArea();
        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);
  
  
        borderPane.setCenter(centralText);
        
        Label zadejPrikazLabel = new Label("Zadej příkaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        zadejPrikazTextField = new TextField();
        zadejPrikazTextField.setPrefWidth(200);
        zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                String vstupniPrikaz = zadejPrikazTextField.getText();
                String odpovedHry = hra.zpracujPrikaz(vstupniPrikaz);
                
                centralText.appendText("\n" + vstupniPrikaz + "\n");
                centralText.appendText("\n" + odpovedHry + "\n");
                zadejPrikazTextField.setText("");

                
                if(hra.konecHry()){
                    zadejPrikazTextField.setEditable(false);
                    centralText.appendText(hra.vratEpilog());
                }
                
            }
        });

        Label vychodyLabel = new Label("Východy: ");
        vychodyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        FlowPane dolniLista = new FlowPane();
        dolniLista.setAlignment(Pos.CENTER_LEFT);
        dolniLista.setPadding(new Insets(10));
        dolniLista.setHgap(20);
        dolniLista.getChildren().addAll(zadejPrikazLabel, zadejPrikazTextField, vychodyLabel, vychody);
        borderPane.setBottom(dolniLista);
        borderPane.setLeft(mapa);
        borderPane.setTop(menuLista);
        //borderPane.setRight(vychody);

        Scene scene = new Scene(borderPane, 1200, 470);

        primaryStage.setTitle("Adventura");

        primaryStage.setScene(scene);
        primaryStage.show();
        zadejPrikazTextField.requestFocus();
    }

    public TextArea getCentralText() {
        return centralText;
    }

    public Mapa getMapa() {
        return mapa;
    }

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
    
    public void setHra(IHra hra) {
        this.hra = hra;
    }

}
